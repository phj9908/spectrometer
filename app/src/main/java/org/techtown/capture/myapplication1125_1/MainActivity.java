package org.techtown.capture.myapplication1125_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import static android.Manifest.permission.CAMERA;


public class MainActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "opencv";
    private Mat img_input;
    private Mat img_output;
    private Mat matInput1, matInput2;
    private Mat matMask=null, matNotMask=null;

    private CameraBridgeViewBase mOpenCvCameraView;

    public native int[] Returnroi(long matAddrInput); //img_roi Rect 리턴
    //public native int Return_roi(long matAddrInput); //roi 디버깅 용
    public native double[] imageprocessing(long matAddrInput); // 나중에 데이터 리턴만 하던지 데이터 리턴도 포함할건지 수정

    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    private boolean processing=false;
    private boolean click=false;
    private boolean frame=false;

    //private TextView counttxt;
    TimerTask timerTask;
    Timer timer = new Timer();

    //double[][]   data_return= new double[100][3];
    //static double[] data_array= new double[300];
    static double sum_B=0;
    static double sum_G=0;
    static double sum_R=0;
    static double count_frame=0;

    static double sum_alpha =0, sum_beta=0;

    Rect roi= new Rect();

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        TextView counttxt = (TextView) findViewById(R.id.count_txt);
//        counttxt.setText(" frame : "+(int)count_frame);

        Intent intent = new Intent(getApplicationContext(),DataActivity.class);

        Button data_button = (Button)findViewById(R.id.data_button);
        data_button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(count_frame>99){


                    double[] sum_array= {0.0,0.0,0.0,0.0,0.0,0.0}; // 데이터 전달할 배열, 입력 하기위해 초기화 해주기

                    sum_array[0]=sum_B;
                    sum_array[1]=sum_G;
                    sum_array[2]=sum_R;
                    sum_array[3]=sum_alpha;
                    sum_array[4]=sum_beta;
                    sum_array[5]=count_frame;

                    intent.putExtra("data",sum_array);
                    startActivity(intent);

                }else  Toast.makeText(MainActivity.this, "not enough data", Toast.LENGTH_SHORT).show();
            }
        });

        Button processing_button = (Button)findViewById(R.id.processing_button);
        processing_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    startTimerTask();

                Toast.makeText(MainActivity.this, "processing start...", Toast.LENGTH_LONG).show();
            }
        });

        mOpenCvCameraView = (CameraBridgeViewBase)findViewById(R.id.activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(0); // front-camera(1),  back-camera(0)
    }

    public void detect_roi(Rect roi){

        int[] roiArray = null;

        roiArray = Returnroi(img_input.getNativeObjAddr());

            roi.x = roiArray[0];
            roi.y = roiArray[1];
            roi.width = roiArray[2];
            roi.height = roiArray[3];
            if ( roiArray[0] != 0 )
            {
            Log.d(TAG, "roi_x " +  roiArray[0] + " found");
            Log.d(TAG, "roi_y " +  roiArray[1] + " found");
            Log.d(TAG, "roi_width " +  roiArray[2] + " found");
            Log.d(TAG, "roi_height " + roiArray[3] + " found");
                Log.d(TAG, "roi_size " +  roiArray[2]*roiArray[3] + " found");
                Log.d(TAG, "roi_size " +  roiArray[2]/roiArray[3] + " found");
        }
    }

    public void toggle(){
        if(processing){processing=false;}
        else processing=true;
    }

    private void startTimerTask()
    {
        ScheduledJob job = new ScheduledJob();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                job.run();
            }
        };
        timer.scheduleAtFixedRate(timerTask,0 ,20); // 10ns마다 작업이 실행되게 한다.
    }

    private void stopTimerTask()
    {
        processing=false;
        if(timerTask != null)
        {
            timerTask.cancel();
            timerTask = null;
        }
    }

    class ScheduledJob extends TimerTask {
        public void run(){
            if(count_frame<100)toggle();
            else processing=false;
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResume :: Internal OpenCV library not found.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "onResum :: OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


    public void onDestroy() {
        timer.cancel();
        super.onDestroy();

        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        img_input = inputFrame.rgba();

        if (processing) {

            if (img_output == null)
                img_output = new Mat(img_input.rows(), img_input.cols(), img_input.type());
            if (matInput1 == null)
                matInput1 = new Mat(img_input.rows(), img_input.cols(), img_input.type());
            if (matInput2 == null)
                matInput2 = new Mat(img_input.rows(), img_input.cols(), img_input.type());
            if (matNotMask == null)
                matNotMask = new Mat(img_input.rows(), img_input.cols(), img_input.type());

            detect_roi(roi); // Rect roi 위치,크기 반환
            if(roi.x!=0) frame=true;

            if(roi.x!=0) {
                frame = true;

                matMask = Mat.zeros(img_input.size(), img_input.type());
                matMask.submat(roi).setTo(Scalar.all(255)); //  roi영역의 위치, 크기만 픽셀값 255할당

                if (matMask != null) {

                    double[] data_array = null;

                    Core.bitwise_and(img_input, matMask, matInput1); // bitwise_and()로 roi영역 원본에 입히기
                    data_array = imageprocessing(matInput1.getNativeObjAddr()); // roi영역만 프로세싱

                    sum_B += data_array[0];
                    sum_G += data_array[1];
                    sum_R += data_array[2];
                    sum_alpha += data_array[3];
                    sum_beta += data_array[4];
                    if (frame) count_frame += data_array[5];

                    if(count_frame==100)
                        Toast.makeText(MainActivity.this, "processing finish", Toast.LENGTH_SHORT).show();

                    if (frame) {
                        Log.d(TAG, "frame[" + count_frame + "]: static_data_array[0] :::" + sum_B);
                        Log.d(TAG, "frame[" + count_frame + "]: static_data_array[1] :::" + sum_G);
                        Log.d(TAG, "frame[" + count_frame + "]: static_data_array[2] :::" + sum_R);
                        Log.d(TAG, "frame[" + count_frame + "]: static_data_array[3] :::" + sum_alpha);
                        Log.d(TAG, "frame[" + count_frame + "]: static_data_array[4] :::" + sum_beta);
                        Log.d(TAG, "frame[" + count_frame + "]: static_count_frame :::" + count_frame);
                    }
                    //Imgproc.rectangle(img_input, roi, new Scalar(255,255,255), 2); // roi 표시

                    if (matMask == null || matNotMask == null) {
                        return img_input;
                    }

                    Core.bitwise_not(matMask, matNotMask);
                    Core.bitwise_and(img_input, matNotMask, matInput2);
                    Core.bitwise_or(matInput1, matInput2, img_output);

                }
            }

            //counttxt.setText(" frame : "+count_image);

            return img_output;
        }
        else return img_input;
    }


    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }


    //여기서부턴 퍼미션 관련 메소드
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;


    protected void onCameraPermissionGranted() {
        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
        if (cameraViews == null) {
            return;
        }
        for (CameraBridgeViewBase cameraBridgeViewBase: cameraViews) {
            if (cameraBridgeViewBase != null) {
                cameraBridgeViewBase.setCameraPermissionGranted();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean havePermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                havePermission = false;
            }
        }
        if (havePermission) {
            onCameraPermissionGranted();
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onCameraPermissionGranted();
        }else{
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
    }


}