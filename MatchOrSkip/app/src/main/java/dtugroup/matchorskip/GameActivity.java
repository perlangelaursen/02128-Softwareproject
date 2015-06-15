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
import android.widget.Toast;

import java.util.Random;

/**
 * Created by perlangelaursen on 10/06/15.
 */
public class GameActivity extends Activity implements VerifyFragment.Callbacks{
    private TextView timer, score;
    private ImageView matchphoto, currentphoto;
    private Image match, current;
    private Image[][] images;
    private int currentIndex;
    private int currentScore;
    private GestureDetector mGestureDetector;
    private VerifyFragment verifyFragment;
    private static final String TAG_FRAGMENT = "verify_fragment";
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
        currentScore = 0;

        setupVerifyFragment();

        setupGestureListenerToPhoto();

        // TODO Setup image array

        newPhotos();

        setupCountDown();

    }

    // TODO Setup image array
    // Hello

    private void newPhotos() {
        Random r = new Random();
        currentIndex = r.nextInt(3);
        this.match = randomPhoto(currentIndex);
        this.current = randomPhoto(currentIndex);
        matchphoto.setImageResource(match.getDrawImage());
        currentphoto.setImageResource(current.getDrawImage());
    }

    private void newCurrentPhoto() {
        this.current = randomPhoto(currentIndex);
        currentphoto.setImageResource(current.getDrawImage());
    }

    private Image randomPhoto(int i) {
        Random r = new Random();
        int j = r.nextInt(10);
        return images[i][j];
    }

    private void setupVerifyFragment() {
        verifyFragment = (VerifyFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (verifyFragment == null) {
            verifyFragment = new VerifyFragment();
            getFragmentManager().beginTransaction().add(verifyFragment, TAG_FRAGMENT).commit();
        }
    }

    private void setupGestureListenerToPhoto() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    // Left swipe
                    if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Call VerifyFragment
                    }
                    // Right swipe
                    else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Call VerifyFragment
                    }
                    // Top swipe
                    else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Call VerifyFragment
                    }
                    // Bottom swipe
                    else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        // TODO: Call VerifyFragment
                    }
                } catch (Exception e) {
                    // nothing for now
                }


                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // TODO: Call VerifyFragment
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

    @Override
    public String[] onPreExecute() {
        return new String[]{match.getID(), current.getID()};
    }

    @Override
    public void onProgressUpdate(boolean input) {
        String stringToToast = input ? getString(R.string.right) : getString(R.string.fail);
        Toast.makeText(GameActivity.this, stringToToast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(int results, boolean input) {
        currentScore += results;
        score.setText("Score: " + currentScore);
        if(input) {
            newPhotos();
        } else {
            newCurrentPhoto();
        }
    }
}
