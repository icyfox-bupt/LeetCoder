package com.icyfox.leetcoder.view;

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

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView list;
    private ProblemAdapter adapter;
    private List<Problem> problems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        list = (ListView) findViewById(R.id.problemlist);
        problems = new LinkedList<>();
        adapter = new ProblemAdapter(this, problems);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.problem_loading_view));
        list.setOnItemClickListener(itemClick);

        ProblemModel.getInstance().retrieveProblemList(new ProblemModel.RetrieveCallback() {
            @Override
            public boolean onDataRetrieved(final boolean success, final List<Problem> retrievedProblems) {
                problems.addAll(retrievedProblems);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                return success;
            }
        });


    }


    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Problem problem = problems.get(position);
            Intent it = new Intent(activity, ProblemActivity.class);
            it.putExtra("problem", problem);
            startActivity(it);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
