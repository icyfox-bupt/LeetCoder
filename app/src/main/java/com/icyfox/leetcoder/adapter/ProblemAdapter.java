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

    public List<Problem> getmProblems() {
        return mProblemList;
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
        ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_problem, null);
            vh.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            vh.tvDifficulty = (TextView) convertView.findViewById(R.id.tv_difficulty);
            vh.tvAccept = (TextView) convertView.findViewById(R.id.tv_accept);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        Problem problem = mProblemList.get(position);
        vh.tvId.setText(problem.getPid() + "");
        vh.tvTitle.setText(problem.getTitle());
        vh.tvDifficulty.setText(problem.getDifficulty().toString());
        vh.tvAccept.setText( ((double)problem.getAcceptance() / 10 ) +"%");

        switch (problem.getDifficulty()){
            case HARD:
                vh.tvDifficulty.setTextColor(getColor(R.color.hard));
                break;
            case EASY:
                vh.tvDifficulty.setTextColor(getColor(R.color.easy));
                break;
            case MEDIUM:
                vh.tvDifficulty.setTextColor(getColor(R.color.medium));
                break;
        }

        return convertView;
    }

    class ViewHolder{
        TextView tvId, tvTitle, tvDifficulty, tvAccept;
    }

    private int getColor(int res){
        return mContext.getResources().getColor(res);
    }
}
