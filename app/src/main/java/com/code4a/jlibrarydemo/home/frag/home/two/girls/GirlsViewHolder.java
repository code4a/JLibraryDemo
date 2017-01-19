package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;


/**
 * viewholder
 */
public class GirlsViewHolder extends BaseViewHolder<GirlsBean.ResultsEntity> {

    private ImageView image;

    public GirlsViewHolder(ViewGroup parent) {
        super(parent, R.layout.home_two_item_girl);
        image = $(R.id.girl_img);
    }

    @Override
    public void setData(GirlsBean.ResultsEntity data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }
}
