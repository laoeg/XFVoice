package com.laoge.mylibrary.XFVoice.Class;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.laoge.mylibrary.XFVoice.listenrer.VoiceToTestCallBackInter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by laoge on 2017/4/18.
 */

public class VoiceToText {

    private Context context;
    /**
     * 构造函数
     * @param context 上下文
     */
    public VoiceToText(Context context) {
        this.context = context;
    }

    /**
     * 调出语音收入弹框 监听结果
     * @param voiceToTestCallBackInter
     */
    public void btnVoice(final VoiceToTestCallBackInter voiceToTestCallBackInter, final View view) {
        RecognizerDialog dialog = new RecognizerDialog(context,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                voiceToTestCallBackInter.callBack(parseIatResult(recognizerResult.getResultString()),view);
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(context, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    /**
     * 解析json数据 返回字符串
     * @param json json字符串
     * @return
     */
    private static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


}
