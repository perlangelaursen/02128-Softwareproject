package dtugroup.matchorskip;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by perlangelaursen on 10/06/15.
 */
public class GameActivity extends Activity {
    private TextView timer, score;
    private ImageView matchphoto, currentphoto;
    private GestureDetector mGestureDetector;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MIN_DISTANCE = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        timer = (TextView) findViewById(R.id.timeView);
        score = (TextView) findViewById(R.id.scoreview);
        matchphoto = (ImageView) findViewById(R.id.matchphoto);
        currentphoto = (ImageView) findViewById(R.id.currentphoto);

        setupGestureListenerToPhoto();

        //
        setupCountDown();

    }

    private void setupGestureListenerToPhoto() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    // Left swipe
                    if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Handle Swipes and update score
                    }
                    // Right swipe
                    else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Handle Swipes and update score
                    }
                    // Top swipe
                    else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Handle Swipes and update score
                    }
                    // Bottom swipe
                    else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Handle Swipes and update score
                    }
                } catch (Exception e) {
                    // nothing for now
                }
                return true;
            }
        });

        currentphoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mGestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
    }

    private void setupCountDown() {
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                // TODO: Make a dialog
            }
        }.start();
    }
}
