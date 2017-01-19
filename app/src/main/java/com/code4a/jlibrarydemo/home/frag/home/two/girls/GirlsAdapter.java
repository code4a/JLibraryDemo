package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.code4a.jlibrarydemo.data.GirlsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * adapter
 */
public class GirlsAdapter extends RecyclerArrayAdapter<GirlsBean.ResultsEntity> {

    public OnMyItemClickListener mOnItemClickListener;

    public GirlsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlsViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, holder);
                }
            }
        });
    }

    public interface OnMyItemClickListener {
        void onItemClick(int position, BaseViewHolder holder);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
