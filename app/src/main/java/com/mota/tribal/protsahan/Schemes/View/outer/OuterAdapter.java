package com.mota.tribal.protsahan.Schemes.View.outer;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Schemes.Model.Data.InnerData;
import com.ramotion.garlandview.TailAdapter;

import java.util.List;

public class OuterAdapter extends TailAdapter<OuterItem> {

    private final int POOL_SIZE = 16;

    private final List<List<InnerData>> mData;
    private final List<String> schemename;
    private final RecyclerView.RecycledViewPool mPool;
    private Context context;

    public OuterAdapter(List<List<InnerData>> data, List<String> schemename, Context context) {
        this.mData = data;
        this.schemename = schemename;
        this.context = context;

        mPool = new RecyclerView.RecycledViewPool();
        mPool.setMaxRecycledViews(0, POOL_SIZE);
    }

    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OuterItem(view, schemename, mPool, context);
    }

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {
        holder.setContent(mData.get(position), position);
    }

    @Override
    public void onViewRecycled(OuterItem holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.outer_item;
    }

}
