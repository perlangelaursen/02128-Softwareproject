package dtugroup.matchorskip;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by perlangelaursen on 10/06/15
 */
public class GameActivity extends FragmentActivity implements VerifyFragment.Callbacks,
        FinishDialogFragment.FinishDialogListener{
    private TextView timer, score;
    private ImageView matchphoto, currentphoto;
    private Image match, current, bonus;
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

        start();
    }

    public void start() {
        timer = (TextView) findViewById(R.id.timeView);
        score = (TextView) findViewById(R.id.scoreview);
        matchphoto = (ImageView) findViewById(R.id.matchphoto);
        currentphoto = (ImageView) findViewById(R.id.currentphoto);
        currentScore = 0;

        setupVerifyFragment();

        setupGestureListenerToPhoto();

        setupImageArray();

        setupBonusImage();

        newPhotos();

        setupCountDown();
    }

    private void setupBonusImage() {
        bonus = new Image(this, "Bonus", R.drawable.Bonus);
    }

    private void setupImageArray() {
        images = new Image[3][10];
        // Miscellaneous
        images[0][0] = new Image(this, "A01", R.drawable.A01);
        images[0][1] = new Image(this, "A02", R.drawable.A02);
        images[0][2] = new Image(this, "A03", R.drawable.A03);
        images[0][3] = new Image(this, "A04", R.drawable.A04);
        images[0][4] = new Image(this, "A05", R.drawable.A05);
        images[0][5] = new Image(this, "A06", R.drawable.A06);
        images[0][6] = new Image(this, "A07", R.drawable.A07);
        images[0][7] = new Image(this, "A08", R.drawable.A08);
        images[0][8] = new Image(this, "A09", R.drawable.A09);
        images[0][9] = new Image(this, "A10", R.drawable.A10);

        // Food
        images[1][0] = new Image(this, "J01", R.drawable.J01);
        images[1][1] = new Image(this, "J02", R.drawable.J02);
        images[1][2] = new Image(this, "J03", R.drawable.J03);
        images[1][3] = new Image(this, "J04", R.drawable.J04);
        images[1][4] = new Image(this, "J05", R.drawable.J05);
        images[1][5] = new Image(this, "J06", R.drawable.J06);
        images[1][6] = new Image(this, "J07", R.drawable.J07);
        images[1][7] = new Image(this, "J08", R.drawable.J08);
        images[1][8] = new Image(this, "J09", R.drawable.J09);
        images[1][9] = new Image(this, "J10", R.drawable.J10);

        // Sightseeing
        images[2][0] = new Image(this, "T01", R.drawable.T01);
        images[2][1] = new Image(this, "T02", R.drawable.T02);
        images[2][2] = new Image(this, "T03", R.drawable.T03);
        images[2][3] = new Image(this, "T04", R.drawable.T04);
        images[2][4] = new Image(this, "T05", R.drawable.T05);
        images[2][5] = new Image(this, "T06", R.drawable.T06);
        images[2][6] = new Image(this, "T07", R.drawable.T07);
        images[2][7] = new Image(this, "T08", R.drawable.T08);
        images[2][8] = new Image(this, "T09", R.drawable.T09);
        images[2][9] = new Image(this, "T10", R.drawable.T10);
    }

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
        int j = r.nextInt(11);
        if(j == 10) {
            return bonus;
        }
        else {
            return images[i][j];
        }
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
                        verifyFragment.start("regular", "skip");
                    }
                    // Right swipe
                    else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        verifyFragment.start("regular", "keep");
                    }
                    // Top swipe
                    else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        verifyFragment.start("regular", "skip");
                    }
                    // Bottom swipe
                    else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                            Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        verifyFragment.start("regular", "keep");
                    }
                } catch (Exception e) {
                    // nothing for now
                }


                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                verifyFragment.start("bonus", "keep");
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
                new FinishDialogFragment().show(getSupportFragmentManager(), "Game Over");
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

    @Override
    public void onDialogPositiveClick() {
        start();
    }

    @Override
    public void onDialogNegativeClick() {
        finish();
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }
}
