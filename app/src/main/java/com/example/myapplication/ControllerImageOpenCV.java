package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerImageOpenCV {
    private static String TAG = "ControllerImageOpenCV";

    private static Map<String, String> mapKeys = new MapKeysBraille().getMapKeys();

    private String key;

    private Bitmap imageBitmap;

    private List<Integer> listKey = null;

    public ControllerImageOpenCV(){
        OpenCVLoader.initDebug();
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getKey() {
        return key;
    }

    public void processImage(){
        Mat rgba = new Mat();
        Mat imageProcessMat = new Mat();

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither = false;
        o.inSampleSize = 4;

        Utils.bitmapToMat(imageBitmap, rgba);

        Imgproc.cvtColor(rgba, imageProcessMat, Imgproc.COLOR_RGB2GRAY);

        Imgproc.medianBlur(imageProcessMat, imageProcessMat, 3);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.morphologyEx(imageProcessMat, imageProcessMat, Imgproc.MORPH_CLOSE, kernel);

        Imgproc.threshold(imageProcessMat, imageProcessMat, 127, 255, Imgproc.THRESH_BINARY);

        //rotacionar a imagem
        Rect roi = new Rect(2100, 1700, 100, 450);
        Mat subImage = imageProcessMat.submat(roi);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierachy = new Mat();
        Imgproc.findContours(subImage, contours, hierachy, 1,2);
        Integer count = 0;
        for (int i = 0; i < contours.size(); i++){
            Double contoursArea = Imgproc.contourArea(contours.get(i));
            if(contoursArea > 650)
                count++;
        }

        if(count == 0){
            Mat matrix = Imgproc.getRotationMatrix2D(new Point(imageProcessMat.width() / 2, imageProcessMat.height() / 2), 180, 1);
            //Imgproc.warpAffine(imageProcessMat, imageProcessMat, matrix, imageProcessMat.size(), Imgproc.INTER_LINEAR, 0, new Scalar(0, 0, 0));
        }

        listKey = new ArrayList<Integer>();
        key = null;


        setKey(imageProcessMat, 1640, 1900, 150, 150);
        setKey(imageProcessMat, 1640, 1790, 150, 150);

        setKey(imageProcessMat, 1740, 1900, 150, 150);
        setKey(imageProcessMat, 1740, 1790, 150, 150);

        setKey(imageProcessMat, 1850, 1900, 150, 150);
        setKey(imageProcessMat, 1850, 1790, 150, 150);

        key = "Nenhuma letra correspondente";
        String keyString = "";

        for(Integer item : listKey){
            keyString += item.toString();
        }

        Log.d(TAG, "processImage: letraString: " + keyString);

        for(String item : mapKeys.keySet()){
            if(keyString.equals(mapKeys.get(item))){
                key = item;
            }
        }
    }

    private void setKey(Mat image, int x, int y, int width, int height){
        Rect roi = new Rect(x, y, width, height);
        Mat sub = image.submat(roi);

        listKey.add(identifyCircles(sub));
    }

    private int identifyCircles(Mat image){
        Mat circles = new Mat();

        Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT,1, 10, 100, 3,76, 100);

        Log.d(TAG, "identifyCircles: cols" + circles.cols());
        Log.d(TAG, "identifyCircles: cols" + circles);

        return (circles.cols() > 1 ? 1 : 0);
    }
}
