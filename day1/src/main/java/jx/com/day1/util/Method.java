package jx.com.day1.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Method {
    private static Method instance;
    public static Method getInstance(){
        if (instance==null){
            instance=new Method();
        }
        return instance;
    }
    public interface CallBack<T>{
        void onSuccess(T t);
    }
    public void getRequest(final String path, final Class clazz, final CallBack callBack){
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                return getRequest(strings[0],clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                callBack.onSuccess(o);
            }
        }.execute(path);
    }
    public <E> E getRequest(String path,Class clazz){
        String request = getRequest(path);
        Gson gson=new Gson();
        E e= (E) gson.fromJson(request,clazz);
        return e;
    }
    //获取数据
    public String getRequest(String path){
        String result="";
        try {
            URL url=new URL(path);
            //打开连接
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            //设置打开连接
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            //请求吗
            int responseCode = connection.getResponseCode();
            if(responseCode==200){
                InputStream inputStream = connection.getInputStream();
               result= stream(inputStream);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //字节转换为字符
    public String stream(InputStream is) throws IOException {
        StringBuilder builder=new StringBuilder();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);
        for (String tem=br.readLine();tem!=null;tem=br.readLine()) {
            builder.append(tem);
        }
        return builder.toString();
    }
    //判断是否有网络
    public boolean hasNetWork(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isAvailable();
    }
}
