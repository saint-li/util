package com.saint.utillib.gloading;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saint.widget.GLoading;


/**
 * demo for wrap fragment
 */
public class WrapRootViewFragment extends Fragment {
    private GLoading.Holder holder;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imageView = new ImageView(inflater.getContext());
        holder = GLoading.getDefault().wrap(imageView).withRetry(new Runnable() {
            @Override
            public void run() {
                //change picture url to a correct one
//                loadImage(getRandomImage());
            }
        });
        //demo load failed with an error image url
        loadImage("");
        return holder.getWrapper();
    }

    private void loadImage(String picUrl) {
        holder.showLoading();
//        Glide.with(this)
//                .load(picUrl)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.showLoadFailed();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.showLoadSuccess();
//                        return false;
//                    }
//                })
//                .into(imageView);
    }

}
