package com.example.myapplication;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class ControllerSpeak {
    private static String TAG = "ControllerSpeak";

    private TextToSpeech mTTs;

     public ControllerSpeak(Context context){
         mTTs = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
             @Override
             public void onInit(int status) {
                 if(status == TextToSpeech.SUCCESS) {
                     mTTs.setSpeechRate(0.5f);
                     int result = mTTs.setLanguage(Locale.getDefault());

                     if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                         Log.d(TAG, "onInit: TTS: Language not supported");
                     }
                 } else {
                     Log.d(TAG, "onInit: TTS: Initialization failed");
                 }
             }
         });
     }

    public TextToSpeech getmTTs() {
        return mTTs;
    }
}
