package dtugroup.matchorskip;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

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
    private int currentInc = 0;
    private int currentScore;
    private CountDownTimer countDownTimer;
    private GestureDetector mGestureDetector;
    private VerifyFragment verifyFragment;
    private static final String TAG_FRAGMENT = "verify_fragment";
    private int currentID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        start();
    }

    public void start() {
        this.timer = (TextView) findViewById(R.id.timeView);
        this.score = (TextView) findViewById(R.id.scoreview);
        this.matchphoto = (ImageView) findViewById(R.id.matchphoto);
        this.currentphoto = (ImageView) findViewById(R.id.currentphoto);
        this.currentScore = 0;

        setupVerifyFragment();

        setupGestureListenerToPhoto();

        setupImageArray();

        setupBonusImage();

        newPhotos();

        setupCountDown();
    }

    private void setupBonusImage() {
        bonus = new Image(this, "Bonus", R.drawable.bonus, true);
    }

    private void setupImageArray() {
        images = new Image[3][10];
        // Miscellaneous
        images[0][0] = new Image(this, "A01", R.drawable.a01, false);
        images[0][1] = new Image(this, "A02", R.drawable.a02, false);
        images[0][2] = new Image(this, "A03", R.drawable.a03, false);
        images[0][3] = new Image(this, "A04", R.drawable.a04, false);
        images[0][4] = new Image(this, "A05", R.drawable.a05, false);
        images[0][5] = new Image(this, "A06", R.drawable.a06, false);
        images[0][6] = new Image(this, "A07", R.drawable.a07, false);
        images[0][7] = new Image(this, "A08", R.drawable.a08, false);
        images[0][8] = new Image(this, "A09", R.drawable.a09, false);
        images[0][9] = new Image(this, "A10", R.drawable.a10, false);

        // Food
        images[1][0] = new Image(this, "J01", R.drawable.j01, false);
        images[1][1] = new Image(this, "J02", R.drawable.j02, false);
        images[1][2] = new Image(this, "J03", R.drawable.j03, false);
        images[1][3] = new Image(this, "J04", R.drawable.j04, false);
        images[1][4] = new Image(this, "J05", R.drawable.j05, false);
        images[1][5] = new Image(this, "J06", R.drawable.j06, false);
        images[1][6] = new Image(this, "J07", R.drawable.j07, false);
        images[1][7] = new Image(this, "J08", R.drawable.j08, false);
        images[1][8] = new Image(this, "J09", R.drawable.j09, false);
        images[1][9] = new Image(this, "J10", R.drawable.j10, false);

        // Sightseeing
        images[2][0] = new Image(this, "T01", R.drawable.t01, false);
        images[2][1] = new Image(this, "T02", R.drawable.t02, false);
        images[2][2] = new Image(this, "T03", R.drawable.t03, false);
        images[2][3] = new Image(this, "T04", R.drawable.t04, false);
        images[2][4] = new Image(this, "T05", R.drawable.t05, false);
        images[2][5] = new Image(this, "T06", R.drawable.t06, false);
        images[2][6] = new Image(this, "T07", R.drawable.t07, false);
        images[2][7] = new Image(this, "T08", R.drawable.t08, false);
        images[2][8] = new Image(this, "T09", R.drawable.t09, false);
        images[2][9] = new Image(this, "T10", R.drawable.t10, false);
    }

    private void newPhotos() {
        Random r = new Random();
        currentInc++;
        currentIndex = currentInc % 3;
        this.match = randomMatchPhoto(currentIndex);
        this.current = randomCurrentPhoto(currentIndex);
        matchphoto.setImageResource(match.getDrawImage());
        currentphoto.setImageResource(current.getDrawImage());
    }

    private void newCurrentPhoto() {
        this.current = randomCurrentPhoto(currentIndex);
        currentphoto.setImageResource(current.getDrawImage());
    }

    private Image randomMatchPhoto(int i) {
        Random r = new Random();
        int j = r.nextInt(10);
        return images[i][j];
    }

    private Image randomCurrentPhoto(int i) {
        Random r = new Random();
        int j = r.nextInt(11);
        while (true) {
            if (j != currentID) {
                currentID = j;
                if (j == 10) {
                    return bonus;
                } else {
                    return images[i][j];
                }
            } else {
                j = r.nextInt(11);
            }
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
        mGestureDetector = new GestureDetector(this, new GestureListener(verifyFragment));

        this.currentphoto = (ImageView) findViewById(R.id.currentphoto);
        currentphoto.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void setupCountDown() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                new FinishDialogFragment().show(getSupportFragmentManager(), "Game Over");
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public Image[] onPreExecute() {
        return new Image[]{match, current};
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
