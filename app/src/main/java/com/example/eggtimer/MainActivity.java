package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Button button;
    boolean isCounterActive=false;
    CountDownTimer countDownTimer;
    Button soundStopButton;
    MediaPlayer mediaPlayer;

    public void buttonClicked(View view) {

        if (isCounterActive) {
            reset();
        }else { isCounterActive=true;
                seekBar.setEnabled(false);
                button.setText("Stop");
                countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long l) {
                        updateTimer((int) l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone);
                        mediaPlayer.start();
                        reset();
                        soundStopButton.setVisibility(View.VISIBLE);
                    }
                }.start();
                Log.i("info", "pressed");
            }
        }

        public void reset(){
         isCounterActive=false;
         seekBar.setEnabled(true);
         button.setText("Start");
         seekBar.setProgress(0);
         textView.setText("0.00");
         countDownTimer.cancel();

        }


    public void updateTimer(int secLeft){
        int min = secLeft/60;
        int sec = secLeft%60;
        String secString=Integer.toString(sec);
        if(sec<=9){
            secString ="0"+ Integer.toString(sec);
        }
        textView.setText(Integer.toString(min) +":"+ secString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar)findViewById(R.id.seekBar2);
        textView =(TextView)findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        soundStopButton = (Button) findViewById(R.id.soundStopButton);

        seekBar.setMax(600);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void soundStop(View view) {
        soundStopButton.setVisibility(View.INVISIBLE);
        mediaPlayer.stop();
    }
}
