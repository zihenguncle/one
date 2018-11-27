package jx.com.day1.app;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;

import jx.com.day1.R;

public class MyImageLoader extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = null;
        try {
            configuration = new ImageLoaderConfiguration.Builder(this)
                    .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                            .cacheOnDisk(true)
                            .cacheInMemory(true)
                            .showImageOnLoading(R.mipmap.ic_launcher)
                            .showImageOnFail(R.mipmap.ic_launcher)
                            .showImageForEmptyUri(R.mipmap.ic_launcher)
                            .build())
                    .diskCache(new LruDiskCache(new File(Environment.getExternalStorageDirectory(),"imagelur"),new Md5FileNameGenerator(),10*1024*1024))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageLoader.getInstance().init(configuration);
    }
}
