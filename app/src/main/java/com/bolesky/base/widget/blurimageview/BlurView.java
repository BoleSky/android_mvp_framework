package com.bolesky.base.widget.blurimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bolesky.base.App;
import com.bolesky.base.dagger.component.DaggerMainActivityComponent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import javax.inject.Inject;

/**
 * This imageView can show blur image.
 * Then it will be expanded to automatically display one image with two styles
 * one is small and blurry, another is the origin image,So here are two urls for these two images.
 */
public class BlurView extends RelativeLayout {

    @Inject
    DisplayImageOptions mDisplayImageOptions;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    Context mContext;

    public final static int DEFAULT_BLUR_FACTOR = 8;
    private int mBlurFactor = DEFAULT_BLUR_FACTOR;
    private String mOriginImageUrl;

    private ImageView imageView;
    private LoadingProgressView mLoadingProgressView;

    private boolean enableProgress = true;

    public BlurView(Context context) {
        this(context, null);
    }

    public BlurView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DaggerMainActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);

        initChildView();
    }

    private void initChildView() {
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        mLoadingProgressView = new LoadingProgressView(mContext);
        LayoutParams progressBarLayoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBarLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mLoadingProgressView.setLayoutParams(progressBarLayoutParams);
        mLoadingProgressView.setVisibility(GONE);

        addView(imageView);
        addView(mLoadingProgressView);
    }

    private SimpleImageLoadingListener blurLoadingListener = new SimpleImageLoadingListener() {
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            imageView.setImageBitmap(getBlurBitmap(loadedImage));
        }
    };

    SimpleImageLoadingListener fullLoadingListener = new SimpleImageLoadingListener() {

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            imageView.setImageBitmap(getBlurBitmap(loadedImage));
            mImageLoader.displayImage(mOriginImageUrl, imageView, mDisplayImageOptions,
                    null, new ImageLoadingProgressListener() {

                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            if (!enableProgress) {
                                return;
                            }
                            if (current < total) {
                                mLoadingProgressView.setVisibility(VISIBLE);
                                mLoadingProgressView.setCurrentProgressRatio((float) current / total);
                            } else {
                                mLoadingProgressView.setVisibility(VISIBLE);
                            }
                        }
                    });
        }
    };

    /**
     * This method will fetch bitmap from URL and make it blurry, display
     *
     * @param blurImageUrl the image resource URL which is needed to be blurry
     */
    public void setBlurImageByUrl(String blurImageUrl) {
        cancelImageRequestForSafety();
        mImageLoader.loadImage(blurImageUrl, blurLoadingListener);
    }

    public void setOriginImageByUrl(String originImageUrl) {
//        mOriginImageUrl = originImageUrl;
        mImageLoader.displayImage(originImageUrl, imageView);
    }

    /**
     * This will load two Images literally. The small size blurry one and the big size original one.
     *
     * @param blurImageUrl   This is a small image url and will be loaded fast and will be blurry
     *                       automatically.
     * @param originImageUrl After show the blurry image, it will load the origin image automatically
     *                       and replace the blurry one after finish loading.
     */
    public void setFullImageByUrl(String blurImageUrl, String originImageUrl) {
        mOriginImageUrl = originImageUrl;
        cancelImageRequestForSafety();
        mImageLoader.loadImage(blurImageUrl, fullLoadingListener);
    }

    private Bitmap getBlurBitmap(Bitmap loadedBitmap) {
        // make this bitmap mutable
        loadedBitmap = loadedBitmap.copy(loadedBitmap.getConfig(), true);
        return FastBlurUtil.doBlurring(loadedBitmap, getBlurFactor(), true);
    }

    private int getBlurFactor() {
        return mBlurFactor;
    }

    public void setBlurFactor(int blurFactor) {
        if (blurFactor < 0) {
            throw new IllegalArgumentException("blurFactor must not be less than 0");
        }
        mBlurFactor = blurFactor;
    }

    public void cancelImageRequestForSafety() {
        mImageLoader.cancelDisplayTask(imageView);
    }

    public void clear() {
        cancelImageRequestForSafety();
        imageView.setImageBitmap(null);
    }

    /**
     * If you disable progress, then it won't show a loading progress view when you're loading image.
     * Default the progress view is enabled.
     */
    public void disableProgress() {
        enableProgress = false;
    }

    public void setProgressBgColor(int bgColor) {
        mLoadingProgressView.setProgressBgColor(bgColor);
    }

    public void setProgressColor(int color) {
        mLoadingProgressView.setProgressColor(color);
    }

}
