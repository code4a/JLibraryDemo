package com.code4a.jlibrarydemo.home.frag.home.one;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrary.utils.ToastUtil;
import com.code4a.jlibrarydemo.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import butterknife.BindView;


public class OneFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.blur_layout)
    BlurLayout mSampleLayout;
    @BindView(R.id.blur_layout2)
    BlurLayout mSampleLayout2;
    @BindView(R.id.blur_layout3)
    BlurLayout mSampleLayout3;
    @BindView(R.id.blur_layout4)
    BlurLayout mSampleLayout4;

    String[] images,titles;

    public static OneFragment getInstance() {
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBanner();
        initHoverView();
    }

    private void initHoverView() {
        BlurLayout.setGlobalDefaultDuration(450);
        View hover = LayoutInflater.from(getHoldingActivity()).inflate(R.layout.hover_sample, null);
        hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada)
                        .duration(550)
                        .playOn(v);
            }
        });
        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Swing)
                        .duration(550)
                        .playOn(v);
            }
        });
        mSampleLayout.setHoverView(hover);
        mSampleLayout.setBlurDuration(550);
        mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
        mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
        mSampleLayout.addChildAppearAnimator(hover, R.id.more, Techniques.FlipInX, 550, 500);

        mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
        mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
        mSampleLayout.addChildDisappearAnimator(hover, R.id.more, Techniques.FlipOutX, 550, 0);

        mSampleLayout.addChildAppearAnimator(hover, R.id.description, Techniques.FadeInUp);
        mSampleLayout.addChildDisappearAnimator(hover, R.id.description, Techniques.FadeOutDown);

        View hover2 = LayoutInflater.from(getHoldingActivity()).inflate(R.layout.hover_sample2, null);
        hover2.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getHoldingActivity(), "Pretty Cool, Right?", Toast.LENGTH_SHORT).show();
            }
        });
        mSampleLayout2.setHoverView(hover2);

        mSampleLayout2.addChildAppearAnimator(hover2, R.id.description, Techniques.FadeInUp);
        mSampleLayout2.addChildDisappearAnimator(hover2, R.id.description, Techniques.FadeOutDown);
        mSampleLayout2.addChildAppearAnimator(hover2, R.id.avatar, Techniques.DropOut, 1200);
        mSampleLayout2.addChildDisappearAnimator(hover2, R.id.avatar, Techniques.FadeOutUp);
        mSampleLayout2.setBlurDuration(1000);

        View hover3 = LayoutInflater.from(getHoldingActivity()).inflate(R.layout.hover_sample3, null);
        mSampleLayout3.setHoverView(hover3);
        mSampleLayout3.addChildAppearAnimator(hover3, R.id.eye, Techniques.Landing);
        mSampleLayout3.addChildDisappearAnimator(hover3, R.id.eye, Techniques.TakingOff);
        mSampleLayout3.enableZoomBackground(true);
        mSampleLayout3.setBlurDuration(1200);

        View hover4 = LayoutInflater.from(getHoldingActivity()).inflate(R.layout.hover_sample4,null);
        mSampleLayout4.setHoverView(hover4);
        mSampleLayout4.addChildAppearAnimator(hover4, R.id.cat, Techniques.SlideInLeft);
        mSampleLayout4.addChildAppearAnimator(hover4, R.id.mail, Techniques.SlideInRight);

        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.cat, Techniques.SlideOutLeft);
        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.mail, Techniques.SlideOutRight);

        mSampleLayout4.addChildAppearAnimator(hover4, R.id.content, Techniques.BounceIn);
        mSampleLayout4.addChildDisappearAnimator(hover4, R.id.content, Techniques.FadeOutUp);


        hover4.findViewById(R.id.cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/code4a"));
                startActivity(getWebPage);
            }
        });

        hover4.findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"jiangyantaodev@163.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About AndroidViewHover");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a good idea about this project..");

                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }

    private void initBanner() {
        images= getResources().getStringArray(R.array.url);
        titles= getResources().getStringArray(R.array.title);

        //显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题列表
        banner.setBannerTitle(titles);
        //设置动画
        banner.setBannerAnimation(Transformer.CubeOut);
        /**
         * 可以选择设置图片网址或者资源文件，默认用Glide加载
         * 如果你想设置默认图片就在xml里设置default_image
         * banner.setImages(images);
         */
        //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
        banner.setImages(images, new OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                /**
                 * 这里你可以根据框架灵活设置
                 */
                Glide.with(getHoldingActivity())
                        .load(url)
                        .into(view);
            }
        });
        //设置点击事件
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtil.showShort(getHoldingActivity(), "你点击了："+position);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_one;
    }
}
