package com.icyfox.leetcoder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.icyfox.leetcoder.R;
import com.icyfox.leetcoder.bean.Problem;
import com.icyfox.leetcoder.utils.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Activity show Problem.
 */
public class ProblemActivity extends BaseActivity {

    private final String BASE = "https://leetcode.com";
    private Problem mProblem;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        init();
    }

    private void init() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        Intent it = getIntent();
        mProblem = (Problem)it.getSerializableExtra("problem");
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        //防止页面超出边界
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE + mProblem.getUrl(), new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("失败", statusCode + " " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resolveProblem(responseString);
            } 
        });
    }

    private void resolveProblem(String html) {
        Document document = Jsoup.parse(html);
        Element frame = document.body().getElementsByClass("container").get(1)
                .getElementsByClass("row").get(0);
        String question = frame.html();

        /** 给problem添加详情信息 **/
        int ac = Integer.parseInt(frame.getElementsByClass("total-ac").get(0).getElementsByTag("strong").text());
        int sub = Integer.parseInt(frame.getElementsByClass("total-submit").get(0).getElementsByTag("strong").text());
        String content = frame.getElementsByClass("question-content").html();
        Elements tags = frame.getElementsByClass("btn-primary");
        for (int i = 0; i < tags.size(); i++)
            problem.addTags(tags.get(i).html());
        problem.setAccepted(ac);
        problem.setSubmissions(sub);
        problem.setContent(content);
        /** 给problem添加详情信息结束 **/

        webview.loadDataWithBaseURL(BASE, question, "text/html", "utf-8", "");
        String question = document.body().getElementsByClass("container").get(1)
                .getElementsByClass("row").get(0).html();
        mWebView.loadDataWithBaseURL(BASE, question, "text/html", "utf-8", "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_problem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
