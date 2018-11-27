package jx.com.day1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapter extends PagerAdapter {

    private String[] s;
    private Context context;

    public ImageAdapter(String[] s, Context context) {
        this.s = s;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        for (int i = 0; i < s.length; i++) {
            ImageLoader.getInstance().displayImage(s[i % s.length],imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
