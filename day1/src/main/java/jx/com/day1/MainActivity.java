package jx.com.day1;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import jx.com.day1.adapter.ImageAdapter;
import jx.com.day1.bean.GoodsBean;
import jx.com.day1.util.Method;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String[] split;
    private String images;
    private ImageAdapter adapter;
    private LinearLayout linear;
    private TextView title,bargainPrice,price;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            sendEmptyMessageDelayed(0,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        getInfo();

    }

    private void initView() {
        title = findViewById(R.id.main_text1);
        bargainPrice = findViewById(R.id.main_text2);
        price = findViewById(R.id.main_text3);
        viewPager = findViewById(R.id.show_viewpager);
        linear = findViewById(R.id.linear);
    }

    private void getInfo() {
        Method.getInstance().getRequest("http://www.zhaoapi.cn/product/getProductDetail?pid=1", GoodsBean.class, new Method.CallBack<GoodsBean>() {

            @Override
            public void onSuccess(GoodsBean bean) {
                GoodsBean.Data data = bean.getData();
                images = data.getImages();
                split = images.split("\\|");
                title.setText(data.getTitle());
                bargainPrice.setText(data.getBargainPrice()+"");
                price.setText(data.getPrice()+"");
                bargainPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                Log.i("TAG",split+"");
                adapter = new ImageAdapter(split,MainActivity.this);
                handler.removeCallbacksAndMessages(null);
                int count = adapter.getCount()/2;
                count = count - count%split.length;
                viewPager.setCurrentItem(count);

                viewPager.setAdapter(adapter);
                handler.sendEmptyMessageDelayed(0,1000);
                setshape(split.length);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    private int index = -1;
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        linear.getChildAt(position % linear.getChildCount()).setSelected(true);
                        if(index >= 0){
                            linear.getChildAt(index % linear.getChildCount()).setSelected(false);
                        }
                        index = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
    }
    private void setshape(int length) {
        linear.removeAllViews();
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.myselect);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int marge = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            params.leftMargin = marge;
            params.rightMargin = marge;
            linear.addView(imageView,params);
        }
    }
}
