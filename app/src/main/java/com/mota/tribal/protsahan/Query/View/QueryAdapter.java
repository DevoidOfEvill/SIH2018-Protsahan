package com.mota.tribal.protsahan.Query.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mota.tribal.protsahan.Query.Model.Data.Query;
import com.mota.tribal.protsahan.R;

import java.util.ArrayList;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.QueryViewHolder> {

    private Context context;
    private ArrayList<Query> queries;

    public QueryAdapter(Context context, ArrayList<Query> queries) {
        this.context = context;
        this.queries = queries;
    }


    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.query_item, parent, false);
        return new QueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        holder.question.setText("Question " + (position + 1) + ": " + queries.get(position).getQuestion());
        holder.answer.setText(queries.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }

    public class QueryViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer;

        public QueryViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}