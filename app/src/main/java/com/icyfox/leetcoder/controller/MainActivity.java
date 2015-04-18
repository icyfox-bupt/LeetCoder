package com.icyfox.leetcoder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.icyfox.leetcoder.R;
import com.icyfox.leetcoder.adapter.ProblemAdapter;
import com.icyfox.leetcoder.bean.Problem;
import com.icyfox.leetcoder.model.ProblemModel;
import com.icyfox.leetcoder.utils.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView mProblemListView;
    private ProblemAdapter mProblemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        mProblemListView = (ListView) findViewById(R.id.problemlist);
        mProblemAdapter = new ProblemAdapter(this.getApplicationContext());
        mProblemListView.setAdapter(mProblemAdapter);
        mProblemListView.setEmptyView(findViewById(R.id.problem_loading_view));
        mProblemListView.setOnItemClickListener(itemClick);

        ProblemModel.getInstance().retrieveProblemList(new ProblemModel.RetrieveCallback() {
            @Override
            public boolean onDataRetrieved(final boolean success, final List<Problem> retrievedProblems) {
                mProblemAdapter.addProblems(retrievedProblems);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            mProblemAdapter.notifyDataSetChanged();
                        }
                    }
                });
                return success;
            }
        });


    }

    /**
     * 点击事件使用数据库里的id (Long) 来进行跳转传值
     */
    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Problem problem = (Problem) mProblemAdapter.getItem(position);
            Intent it = new Intent(getApplicationContext(), ProblemActivity.class);
            it.putExtra("pid", problem.getId());
            startActivity(it);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
