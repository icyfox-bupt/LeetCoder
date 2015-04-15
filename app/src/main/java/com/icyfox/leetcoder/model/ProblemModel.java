package com.icyfox.leetcoder.model;

import android.util.Log;

import com.icyfox.leetcoder.bean.Difficulty;
import com.icyfox.leetcoder.bean.Problem;
import com.icyfox.leetcoder.bean.Status;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orm.SugarRecord;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bixiaopeng on 15/4/15.
 */

public class ProblemModel {

    public interface RetrieveCallback {
        public boolean onDataRetrieved(boolean success, List<Problem> problems);
    }

    static private ProblemModel sProblemModel;
    static public ProblemModel getInstance(){
        if (sProblemModel==null){
            synchronized (ProblemModel.class){
                if (sProblemModel == null) {
                    sProblemModel = new ProblemModel();
                }
            }
        }
        return sProblemModel;
    }
    private ProblemModel(){

    }

    public boolean retrieveProblemList(final RetrieveCallback callback){
        List<Problem> localCachedProblems = SugarRecord.listAll(Problem.class);

        AsyncHttpClient client = new AsyncHttpClient();
        if (localCachedProblems.size() == 0)
            client.get("https://leetcode.com/problemset/algorithms/", new TextHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.i("network", statusCode + " " + responseString);
                    runCallbackInNewThread(false, null,callback);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, final String responseString) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            resolveProblem(responseString, callback);
                        }
                    }).start();

                }
            });
        else
            runCallbackInNewThread(true,localCachedProblems,callback);
        return true;
    }

    private void runCallbackInNewThread(final boolean success, final List<Problem> problems, final RetrieveCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onDataRetrieved(success,problems);
            }
        }).start();
    }

    /**
     * Resolve html to problem datas.
     * @param html
     */
    private void resolveProblem(String html,RetrieveCallback callback){
        List<Problem> problems = new LinkedList<>();
        Document doc = Jsoup.parse(html);
        Elements problemNodes = doc.getElementById("problemList").select("tr");
        for (Element problemNode : problemNodes){

            Elements tds = problemNode.select("td");
            Problem problem = new Problem();
            if (tds.size() > 0) {
                problem.setStatus(Status.fromString(tds.get(0).getElementsByTag("span").get(0).className()));
                problem.setPid(Integer.parseInt(tds.get(1).text()));
                problem.setTitle(tds.get(2).getElementsByTag("a").get(0).text());
                problem.setUrl(tds.get(2).getElementsByTag("a").get(0).attr("href"));
                problem.setAcceptance( (int)(Double.parseDouble(tds.get(3).text().replace("%", "")) * 10 ));
                problem.setDifficulty(Difficulty.fromInteger(Integer.parseInt(tds.get(4).attr("value"))));
            }
            if (!problem.empty()) {
                problems.add(problem);
            }
        }
        Problem.saveInTx(problems);
        callback.onDataRetrieved(true,problems);
    }

}
