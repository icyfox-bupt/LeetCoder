package com.icyfox.leetcoder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icyfox.leetcoder.R;
import com.icyfox.leetcoder.bean.Problem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by icyfox on 2015/3/27.
 */
public class ProblemAdapter extends BaseAdapter{

    private Context mContext;
    private List<Problem> mProblemList;
    private LayoutInflater mInflater;

    public ProblemAdapter(Context context) {
        this.mContext = context;
        this.mProblemList = new LinkedList<>();
        this.mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addProblems(List<Problem> problems){
        this.mProblemList.addAll(problems);
    }

    public void addProblem(Problem problem){
        this.mProblemList.add(problem);
    }

    @Override
    public int getCount() {
        return mProblemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProblemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_problem, null);
        TextView tvId, tvTitle;
        tvId = (TextView) convertView.findViewById(R.id.tv_id);
        tvTitle = (TextView) convertView.findViewById(R.id.tv_title);

        Problem problem = mProblemList.get(position);
        tvId.setText(problem.getPid() + "");
        tvTitle.setText(problem.getTitle());
        return convertView;
    }
}
