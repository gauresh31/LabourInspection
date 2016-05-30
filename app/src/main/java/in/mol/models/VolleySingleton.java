package in.mol.models;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import in.mol.labourinspection.LabourInspection;

/**
 * Created by Gauresh on 17-03-2016.
 */
public class VolleySingleton {

    public static VolleySingleton vInstance = null;
    RequestQueue mRequestQueue;
    ImageLoader imageLoader;
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(LabourInspection.getAppContext());
//        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
//            private LruCache<String,Bitmap> cache = new LruCache<>(
//                    (int) (Runtime.getRuntime().maxMemory()/1024/8));
//
//            @Override
//            public Bitmap getBitmap(String url) {
//                return cache.get(url);
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//                cache.put(url,bitmap);
//            }
//        });
    }

    public static VolleySingleton getInstance(){
        if(vInstance == null){
            vInstance = new VolleySingleton();
        }

        return vInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

//    public ImageLoader getImageLoader(){
//        return imageLoader;
//    }
}