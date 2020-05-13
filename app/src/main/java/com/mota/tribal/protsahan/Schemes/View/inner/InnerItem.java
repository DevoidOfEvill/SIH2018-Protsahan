package com.mota.tribal.protsahan.Schemes.View.inner;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Schemes.Model.Data.InnerData;

import org.greenrobot.eventbus.EventBus;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class InnerItem extends com.ramotion.garlandview.inner.InnerItem {

    public final TextView mHeader;
    public final TextView mName;
    public final TextView mAddress;
    public final ImageView mAvatar;
    public final View mAvatarBorder;
    public final View mLine;
    private final View mInnerLayout;
    private InnerData mSchemeInfo;

    public InnerItem(View itemView) {
        super(itemView);
        mInnerLayout = ((ViewGroup) itemView).getChildAt(0);

        mHeader = itemView.findViewById(R.id.tv_header);
        mName = itemView.findViewById(R.id.tv_name);
        mAddress = itemView.findViewById(R.id.tv_address);
        mAvatar = itemView.findViewById(R.id.avatar);
        mAvatarBorder = itemView.findViewById(R.id.avatar_border);
        mLine = itemView.findViewById(R.id.line);

        mInnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(InnerItem.this);
            }
        });
    }

    @Override
    protected View getInnerLayout() {
        return mInnerLayout;
    }

    public InnerData getItemData() {
        return mSchemeInfo;
    }

    public void clearContent() {
        Glide.clear(mAvatar);
        mSchemeInfo = null;
    }

    void setContent(InnerData data) {
        mSchemeInfo = data;

        mHeader.setText(data.title);
        mName.setText(String.format("%s ", data.name));
        mAddress.setText(String.format("%s · %s",
                data.year, data.description));

        Glide.with(itemView.getContext())
                .load(data.avatarUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .bitmapTransform(new CropCircleTransformation(itemView.getContext()))
                .into(mAvatar);
    }

}
