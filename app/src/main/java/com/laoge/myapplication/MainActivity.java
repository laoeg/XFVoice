package com.laoge.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iflytek.cloud.SpeechError;
import com.laoge.mylibrary.XFVoice.Class.TextToVoice;
import com.laoge.mylibrary.XFVoice.Class.VoiceToText;
import com.laoge.mylibrary.XFVoice.listenrer.VoiceToTestCallBackInter;
import com.yinghe.whiteboardlib.fragment.WhiteBoardFragment;

public class MainActivity extends AppCompatActivity implements VoiceToTestCallBackInter, WhiteBoardFragment.PathCallBack {

    private Button b1, b2;
    private EditText et1;
    private LinearLayout back;
    public LinearLayout sure;
    private String path = "";
    private VoiceToText voiceToText = new VoiceToText(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        back = (LinearLayout) findViewById(R.id.back);
        sure = (LinearLayout) findViewById(R.id.submit);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final WhiteBoardFragment wb = WhiteBoardFragment.newInstance();
        ft.add(R.id.fl_main, wb, "wb").commit();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!path.equals("") && path != null) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("result", path);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "图片未保存", Toast.LENGTH_SHORT).show();
            }

            }

        });



      /*  SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 58f55ef9");
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        et1 = (EditText)findViewById(R.id.et1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click1();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click2();
            }
        });*/

    }

    public void click1() {
        voiceToText.btnVoice(this, b1);
    }

    public void click2() {
        //voiceToText.btnVoice(this,b2);
        TextToVoice textToVoice = new TextToVoice(this, ((EditText) findViewById(R.id.et2)).getText().toString(), new com.iflytek.cloud.SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                Toast.makeText(MainActivity.this, "开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {
                System.out.println("进度：" + i);
            }

            @Override
            public void onCompleted(SpeechError speechError) {
                Toast.makeText(MainActivity.this, "说完了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

    @Override
    public void callBack(String s, View view) {
        if (view.getId() == R.id.b1) {
            et1.append(s);
        } else
            System.out.println("-----");
    }

    @Override
    public void onCallBack(final String path) {
        this.path = path;
    }
}
