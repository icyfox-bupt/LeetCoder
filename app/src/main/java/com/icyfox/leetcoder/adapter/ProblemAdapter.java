package com.icyfox.leetcoder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icyfox.leetcoder.R;
import com.icyfox.leetcoder.bean.Problem;

import java.util.List;

/**
 * Created by icyfox on 2015/3/27.
 */
public class ProblemAdapter extends BaseAdapter{

    Activity activity;
    List<Problem> problemList;
    LayoutInflater inflater;

    public ProblemAdapter(Activity activity, List<Problem> problemList) {
        this.activity = activity;
        this.problemList = problemList;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return problemList.size();
    }

    @Override
    public Object getItem(int position) {
        return problemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_problem, null);
        TextView tvId, tvTitle;
        tvId = (TextView) convertView.findViewById(R.id.tv_id);
        tvTitle = (TextView) convertView.findViewById(R.id.tv_title);

        Problem problem = problemList.get(position);
        tvId.setText(problem.getPid() + "");
        tvTitle.setText(problem.getTitle());
        return convertView;
    }
}
