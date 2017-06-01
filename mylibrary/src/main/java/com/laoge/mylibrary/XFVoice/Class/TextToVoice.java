package com.laoge.mylibrary.XFVoice.Class;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.laoge.mylibrary.XFVoice.util.ApkInstaller;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by laoge on 2017/4/18.
 */

public class TextToVoice {


    private Context mContext;
    private String text;
    private SynthesizerListener synthesizerListener;
    //语音合成对象
    public SpeechSynthesizer mTts;
    ApkInstaller mInstaller ;
    private SharedPreferences mSharedPreferences;

    /**
     * 构造函数
     * @param context 上下文
     * @param text 需要语音播放的内容
     * @param synthesizerListener 语音播放监听器
     */
    public TextToVoice(Context context, String text, SynthesizerListener synthesizerListener) {
        this.mContext = context;
        this.text =  text;
        this.synthesizerListener = synthesizerListener;
        mInstaller = new  ApkInstaller((Activity) context);
        mSharedPreferences = context.getSharedPreferences("com.iflytek.setting", MODE_PRIVATE);
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);

    }

    /**
     * 参数设置
     * @param
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        /*mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");*/

        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED,"50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME,  "50");
        /**
         * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
         * 开发者如需自定义参数，请参考在线合成参数设置
         */
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext,"初始化失败,错误码："+code,Toast.LENGTH_LONG).show();
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                setParam();
                Toast.makeText(mContext,"正在合成...",Toast.LENGTH_LONG).show();
                int codes = mTts.startSpeaking(text, synthesizerListener);
                if (codes != ErrorCode.SUCCESS) {
                    if(codes == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
                        //未安装则跳转到提示安装页面
                        mInstaller.install();
                    }else {
                        Toast.makeText(mContext,"语音合成失败,错误码: " + codes,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };

}
