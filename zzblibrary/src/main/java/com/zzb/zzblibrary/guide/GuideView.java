package com.zzb.zzblibrary.guide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhy.autolayout.AutoLinearLayout;
import com.zzb.zzblibrary.R;

/**
 * 作者： 张梓彬
 * 日期： 2018/3/2 0002
 * 时间： 上午 9:40
 * 描述： 炫酷引导页，图片，颜色变换
 */

public class GuideView extends AutoLinearLayout {
    private Context mContext;
    private AutoLinearLayout layPoint;
    private ColorAnimationView colorAnimationView;
    private ViewPager viewPager;
    private ImageView[] imagePoint;
    public static final int pointDefaultHeight = 40;

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.lay_guide, this);
        colorAnimationView = (ColorAnimationView) findViewById(R.id.colorAnimationView);
        layPoint = (AutoLinearLayout) findViewById(R.id.layPoint);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    public void setData(FragmentManager fragmentManager, int[] resource, final int drawableSelect, final int drawableunSelect, final View view,int pointHeight, int... colors) {
        MyFragmentStatePager adpter = new MyFragmentStatePager(fragmentManager, resource);
        viewPager.setAdapter(adpter);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layPoint.getLayoutParams();
        if (pointHeight!=pointDefaultHeight){
            layoutParams.bottomMargin = pointHeight;
        }else {
            layoutParams.bottomMargin = pointDefaultHeight;
        }
        layPoint.setLayoutParams(layoutParams);
        /**
         *  首先，你必须在 设置 Viewpager的 adapter 之后在调用这个方法
         *  第二点，setmViewPager(ViewPager mViewPager,Object obj, int count, int... colors)
         *         第一个参数 是 你需要传人的 viewpager
         *         第二个参数 是 一个实现了ColorAnimationView.OnPageChangeListener接口的Object,用来实现回调
         *         第三个参数 是 viewpager 的 孩子数量
         *         第四个参数 int... colors ，你需要设置的颜色变化值~~ 如何你传人 空，那么触发默认设置的颜色动画
         * */
        colorAnimationView.setmViewPager(viewPager, resource.length,colors);
        imagePoint = new ImageView[resource.length];
        for (int i = 0; i < resource.length; i++) {
            ImageView imageView = new ImageView(mContext);
            if (i==0){
                imageView.setImageDrawable(mContext.getResources().getDrawable(drawableSelect));
            }else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(drawableunSelect));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setTag(i);
            imagePoint[i] = imageView;
            layPoint.addView(imagePoint[i]);

        }

        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    onScrollChangedListerer.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }catch (Exception e){}
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imagePoint.length; i++) {
                    imagePoint[i].setImageDrawable(mContext.getResources().getDrawable(drawableunSelect));
                }
                imagePoint[position].setImageDrawable(mContext.getResources().getDrawable(drawableSelect));
                view.setVisibility(GONE);
                if (position ==imagePoint.length-1){
                    view.setVisibility(VISIBLE);
                }

                try {
                    onScrollChangedListerer.onPageSelected(position);
                }catch (Exception e){}
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                try {
                    onScrollChangedListerer.onPageScrollStateChanged(state);
                }catch (Exception e){}
            }
        });

    }

    public class MyFragmentStatePager extends FragmentStatePagerAdapter {
        private int[] resource;

        public MyFragmentStatePager(FragmentManager fm, int[] resource) {
            super(fm);
            this.resource = resource;
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(position, resource);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public static class MyFragment extends Fragment {
        private int position;
        private int[] resource;

        public MyFragment(int position, int[] resource) {
            this.position = position;
            this.resource = resource;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resource[position]);
            return imageView;
        }
    }

    /**
     * 对外暴露一个滑动的接口
     * */
    public OnScrollChangedListerer onScrollChangedListerer;

    public interface OnScrollChangedListerer{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    public void setOnScrollChangedListerer(OnScrollChangedListerer onScrollChangedListerer) {
        this.onScrollChangedListerer = onScrollChangedListerer;
    }
}
