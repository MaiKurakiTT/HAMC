package com.hsd.hamc.video.view.widget;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.hamc.R;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by apple on 16/10/11.
 */
public class VideoTouchView extends RelativeLayout implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    //触摸控制声音和亮度
    private VideoView mVideoView;
    //视频地址
    private String path;
    //activity
    private Activity mActivity;
    //声音和亮度布局
    private View mVolumeBrightnessLayout;
    //操作图片背景
    private ImageView mOperationBg;
    //操作父窗体
    private ImageView mOperationPercent;
    private Uri uri;
    //音量管理者
    private AudioManager mAudioManager;
    /** 最大声音 */
    private int mMaxVolume;
    /** 当前声音 */
    private int mVolume = -1;
    /** 当前亮度 */
    private float mBrightness = -1f;
    /** 当前缩放模式 */
    private int mLayout = VideoView.VIDEO_LAYOUT_ZOOM;
    //手势控制器
    private GestureDetector mGestureDetector;
    //媒体控制
    private MediaController mMediaController;

    //加载进度条
    private ProgressBar pb;
    //加载速率和加载速度
    private TextView downloadRateView, loadRateView;
    private View view;


    public VideoTouchView(Context context) {

        this(context,null);
        System.out.print("video touch view 被创建");
    }

    public VideoTouchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.video_view, this, true);
//        view = View.inflate(getContext(), R.layout.video_view,null);
        mVolumeBrightnessLayout = view.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) view.findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView) view.findViewById(R.id.operation_percent);
        mVideoView = (VideoView) view.findViewById(R.id.surface_view);
        pb = (ProgressBar) view.findViewById(R.id.probar);
        downloadRateView = (TextView) view.findViewById(R.id.download_rate);
        loadRateView = (TextView) view.findViewById(R.id.load_rate);
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(getContext(),new MyGestureListener());

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {

        System.out.println("onInfo");
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }


    //手势识别器
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        /** 双击 */
//        public boolean onDoubleTap(MotionEvent e) {
//            Log.i("onDoubleTap" , "双击");
//            if (mLayout == VideoView.VIDEO_LAYOUT_ZOOM)
//                mLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
//            else
//                mLayout++;
//            if (mVideoView != null)
//                mVideoView.setVideoLayout(mLayout, 0);
//            return true;
//        }

        /** 滑动 */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.i("onScroll" , "滑动");
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int windowWidth = dm.widthPixels;
            int windowHeight = dm.heightPixels;

            if (mOldX > windowWidth *0.5 )// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth *0.5)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("onTouchEvent" , "onTouchEvent");
        System.out.println(mGestureDetector);
        boolean flag = mGestureDetector.onTouchEvent(event);
        System.out.print("flag =  " + flag);
        if (mGestureDetector.onTouchEvent(event))
             return true;

        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent" , "抬起手指");
                endGesture();
                break;
        }

        return super.onTouchEvent(event);
    }

    /** 手势结束 */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    /** 定时隐藏 */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mVolumeBrightnessLayout.setVisibility(View.GONE);
        }
    };


    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {


        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = view.findViewById(R.id.operation_full).getLayoutParams().width
                * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent*/

    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();


            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (view.findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }


    private Window getWindow(){

        if(mActivity == null){
            throw new NullPointerException("activity is null , VideoTouchView need hava a activity. " +
                    "suggesstion : setActivity()");
        }

        return mActivity.getWindow();
    };

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        Log.i("setmActivity",mActivity==null?"sss":"fff");
        this.mActivity = mActivity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        Log.i("setPath",mActivity==null?"sss":"fff");
//        if (!LibsChecker.checkVitamioLibs(mActivity))
//            return;
        mMediaController = new MediaController(getContext());
        //uri = Uri.parse(path);
       // mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(mActivity));

        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.requestFocus();
    }

    public ImageView getmOperationBg() {
        return mOperationBg;
    }

    public OnTouchListener getListener() {
        return listener;
    }

    public void setListener(OnTouchListener listener) {
        this.listener = listener;
    }

    public void setmOperationBg(ImageView mOperationBg) {
        this.mOperationBg = mOperationBg;
    }

    public ImageView getmOperationPercent() {
        return mOperationPercent;
    }

    public VideoView getmVideoView() {
        return mVideoView;
    }

    public void setmOperationPercent(ImageView mOperationPercent) {
        this.mOperationPercent = mOperationPercent;
    }

    private OnTouchListener listener;
    public interface OnTouchListener{
        void onError();
    }


}
