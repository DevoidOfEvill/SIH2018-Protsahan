package com.mota.tribal.protsahan.Schemes.View.inner;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Schemes.Model.Data.InnerData;
import com.mota.tribal.protsahan.Schemes.View.SchemeDescriptionActivity;
import com.mota.tribal.protsahan.databinding.InnerItemBinding;

import java.util.ArrayList;
import java.util.List;


public class InnerAdapter extends com.ramotion.garlandview.inner.InnerAdapter<InnerItem> {

    private final List<InnerData> mData = new ArrayList<>();
    private Context context;

    public InnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public InnerItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final InnerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new InnerItem(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InnerItem holder, int position) {
        holder.setContent(mData.get(position));
        Intent intent = new Intent(context, SchemeDescriptionActivity.class);
        intent.putExtra("QueryData", mData.get(position).toString());
    }

    @Override
    public void onViewRecycled(InnerItem holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inner_item;
    }

    public void addData(@NonNull List<InnerData> schemeInfoList) {
        final int size = mData.size();
        mData.addAll(schemeInfoList);
        notifyItemRangeInserted(size, schemeInfoList.size());
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

}
