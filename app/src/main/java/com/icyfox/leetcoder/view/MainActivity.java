package com.icyfox.leetcoder.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.icyfox.leetcoder.R;
import com.icyfox.leetcoder.adapter.ProblemAdapter;
import com.icyfox.leetcoder.bean.Diffculty;
import com.icyfox.leetcoder.bean.Problem;
import com.icyfox.leetcoder.bean.Status;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView list;
    private ProblemAdapter adapter;
    private List<Problem> problems;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        list = (ListView) findViewById(R.id.problemlist);
        problems = new ArrayList<>();
        adapter = new ProblemAdapter(this, problems);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.ll_empty));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://leetcode.com/problemset/algorithms/", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("失败", statusCode + " " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resolveProblem(responseString);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Resolve html to problem datas.
     * @param html
     */
    private void resolveProblem(String html){
        Document doc = Jsoup.parse(html);
        Elements elems = doc.getElementById("problemList").select("tr");
        for (int i=0;i<elems.size();i++){
            Element elem = elems.get(i);
            Elements tds = elem.select("td");

            Problem problem = new Problem();
            if (tds.size() > 0) {
                problem.setStatus(Status.fromString(tds.get(0).getElementsByTag("span").get(0).className()));
                problem.setId( Integer.parseInt(tds.get(1).text()));
                problem.setTitle(tds.get(2).getElementsByTag("a").get(0).text());
                problem.setUrl(tds.get(2).getElementsByTag("a").get(0).attr("href"));
                problem.setAcceptance( (int)(Double.parseDouble(tds.get(3).text().replace("%", "")) * 10 ));
                problem.setDiffculty(Diffculty.fromInteger( Integer.parseInt(tds.get(4).attr("value"))));
            }
            if (!problem.empty())
                problems.add(problem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
