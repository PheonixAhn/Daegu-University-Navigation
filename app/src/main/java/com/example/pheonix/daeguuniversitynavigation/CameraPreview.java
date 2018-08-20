package com.example.pheonix.daeguuniversitynavigation;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private Camera mCamera;
    public List<Camera.Size> listPreviewSizes;
    private Camera.Size previewSize;
    private Context context;


    // SurfaceView コンストラクタ
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mCamera = MapActivity.getCamera();
        if(mCamera == null){
            mCamera = Camera.open();
        }
        listPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();

    }

    //  SurfaceView 生成する時呼ぶ
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        try {
            // カメラオブジェクトを連結
            if(mCamera  == null){
                mCamera  = Camera.open();
            }

            // カメラ設定
            Camera.Parameters parameters = mCamera .getParameters();

            // // カメラの画面が横、縦かによって画面を設定
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(surfaceHolder);

            // カメラプレビューをする
            mCamera.startPreview();

            // 自動フォーカス設定
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {

                    }
                }
            });
        } catch (IOException e) {
        }
    }

    // SurfaceView の大きさが変われる時呼ぶ
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {

        if (surfaceHolder.getSurface() == null){
            // プレビューがない時
            return;
        }
        // プレビューをまた設定する
        try {
            mCamera .stopPreview();

            Camera.Parameters parameters = mCamera .getParameters();

            // 画面の広さを設定
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera .setParameters(parameters);

            // 新たに変わった設定でプレビューをスタート
            mCamera .setPreviewDisplay(surfaceHolder);
            mCamera .startPreview();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // SurfaceViewが終了される時呼ぶ
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(mCamera != null){
            // 카메라 미리보기를 종료한다.
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    // 画面が回転する時サイズを量る
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (listPreviewSizes != null) {
            previewSize = getPreviewSize(listPreviewSizes, width, height);
        }
    }
    public Camera.Size getPreviewSize(List<Camera.Size> sizes, int w, int h) {

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }
}
