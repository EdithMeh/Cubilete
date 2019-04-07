package com.example.elopez.kraftvisual;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * Constants for sensors
     */
    private static final float SHAKE_THRESHOLD = 1.1f;
    private static final int SHAKE_WAIT_TIME_MS = 250;

    /**
     * The sounds to play when a pattern is detected
     */
    private static MediaPlayer soundAcc;
    private static MediaPlayer soundGyro;

    /**
     * Sensors
     */

    private SensorManager mSensorManager;
    private Sensor mSensorAcc;
    private long mShakeTime = 0;

    private ImageView imgDado1;
    private ImageView imgDado2;
    private ImageView imgDado3;
    private ImageView imgDado4;
    private ImageView imgDado5;
    private ImageView imgDado6;

    int image[] = {
            R.drawable.dv1,
            R.drawable.dv2,
            R.drawable.dv3,
            R.drawable.dv4,
            R.drawable.dv5,
            R.drawable.dv6
    };

    int imageAm[] = {
            R.drawable.da1,
            R.drawable.da2,
            R.drawable.da3,
            R.drawable.da4,
            R.drawable.da5,
            R.drawable.da6
    };

    int imageRo[] = {
            R.drawable.dr1,
            R.drawable.dr2,
            R.drawable.dr3,
            R.drawable.dr4,
            R.drawable.dr5,
            R.drawable.dr6
    };

    private TextView txtjuego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Instanciate the sound to use
        soundAcc = MediaPlayer.create(this, R.raw.dado);
        imgDado1 = (ImageView) findViewById(R.id.imageView2);
        imgDado2 = (ImageView) findViewById(R.id.imageView3);
        imgDado3 = (ImageView) findViewById(R.id.imageView4);
        imgDado4 = (ImageView) findViewById(R.id.imageView5);
        imgDado5 = (ImageView) findViewById(R.id.imageView6);
        imgDado6 = (ImageView) findViewById(R.id.imageView7);
        txtjuego = findViewById(R.id.textView);
    }

    int filaDados = 1;

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                 return;
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShake(event);
        }
    }

    /**
     * Detect a shake based on the ACCELEROMETER sensor
     *
     * @param event
     */
    private void detectShake(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;

            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            if (gForce > SHAKE_THRESHOLD) {
                final ImageView vaso = (ImageView) findViewById(R.id.imageView);
                soundAcc.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                       // Toast.makeText(MainActivity.this, "I'm Finished", Toast.LENGTH_SHORT).show();
                        int dado1 = ((int) (Math.random() * 6)) + 1;
                        int dado2 = ((int) (Math.random() * 6)) + 1;
                        int dado3 = ((int) (Math.random() * 6)) + 1;
                        int dado4 = ((int) (Math.random() * 6)) + 1;
                        int dado5 = ((int) (Math.random() * 6)) + 1;
                        int dado6 = ((int) (Math.random() * 6)) + 1;
                        imgDado1.setImageResource(imageAm[dado1 - 1]);
                        imgDado2.setImageResource(imageAm[dado2 - 1]);
                        if (filaDados == 1){
                            imgDado1.setVisibility(View.VISIBLE);
                            imgDado2.setVisibility(View.VISIBLE);
                        }
                        if (filaDados == 2){
                            imgDado3.setImageResource(image[dado3 - 1]);
                            imgDado4.setImageResource(image[dado4 - 1]);
                            imgDado1.setVisibility(View.VISIBLE);
                            imgDado2.setVisibility(View.VISIBLE);
                            imgDado3.setVisibility(View.VISIBLE);
                            imgDado4.setVisibility(View.VISIBLE);
                        }
                        if (filaDados == 3){
                            imgDado5.setImageResource(imageRo[dado5 - 1]);
                            imgDado6.setImageResource(imageRo[dado6 - 1]);
                            imgDado1.setVisibility(View.VISIBLE);
                            imgDado2.setVisibility(View.VISIBLE);
                            imgDado3.setVisibility(View.VISIBLE);
                            imgDado4.setVisibility(View.VISIBLE);
                            imgDado5.setVisibility(View.VISIBLE);
                            imgDado6.setVisibility(View.VISIBLE);
                        }
                        vaso.clearAnimation();
                    }
                });

                Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanim);
                vaso.setAnimation(shake);
                vaso.startAnimation(shake);
                soundAcc.start();
            }
        }
    }

    public void mas(View v){
        imgDado1.setVisibility(View.INVISIBLE);
        imgDado2.setVisibility(View.INVISIBLE);
        imgDado3.setVisibility(View.INVISIBLE);
        imgDado4.setVisibility(View.INVISIBLE);
        imgDado5.setVisibility(View.INVISIBLE);
        imgDado6.setVisibility(View.INVISIBLE);
        Button mas = findViewById(R.id.button2);
        Button menos = findViewById(R.id.button3);
        if (filaDados == 1){
            menos.setEnabled(true);
        }
        filaDados++;
        if (filaDados == 3){
            mas.setEnabled(false);
        }
    }

    public void menos(View v){
        imgDado1.setVisibility(View.INVISIBLE);
        imgDado2.setVisibility(View.INVISIBLE);
        imgDado3.setVisibility(View.INVISIBLE);
        imgDado4.setVisibility(View.INVISIBLE);
        imgDado5.setVisibility(View.INVISIBLE);
        imgDado6.setVisibility(View.INVISIBLE);
        Button mas = findViewById(R.id.button2);
        Button menos = findViewById(R.id.button3);
        if (filaDados == 3){
            mas.setEnabled(true);
        }
        filaDados--;
        if (filaDados == 1){
            menos.setEnabled(false);
        }
    }

    public void reset(View vista) {
        ImageView vaso = (ImageView) findViewById(R.id.imageView);
        txtjuego.setText("");
        imgDado1.setVisibility(View.INVISIBLE);
        imgDado2.setVisibility(View.INVISIBLE);
        vaso.setImageResource(R.drawable.cub);
        super.onResume();
        mSensorManager.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "Reset", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
