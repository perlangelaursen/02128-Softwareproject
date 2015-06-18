package dtugroup.matchorskip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by perlangelaursen on 10/06/15
 */
public class GameActivity extends FragmentActivity implements VerifyFragment.Callbacks,
        FinishDialogFragment.FinishDialogListener {
    private TextView timer, score, highscoreView;
    private ImageView matchphoto, currentphoto;
    private Image match, current;
    private ImageView bonus;
    private Image[][] images;
    private int currentIndex;
    private int currentInc = 0;
    private int currentScore;
    private int lastHighScore;
    private CountDownTimer countDownTimer;
    private GestureDetector mGestureDetector;
    private VerifyFragment verifyFragment;
    private static final String TAG_FRAGMENT = "verify_fragment";
    private int currentID = 0;
    SharedPreferences highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        start();
    }

    public void start() {
        this.timer = (TextView) findViewById(R.id.timeView);
        this.score = (TextView) findViewById(R.id.scoreview);
        this.highscoreView = (TextView) findViewById(R.id.highscoreView);
        lastHighScore = getHighestScore();
        highscoreView.setText(getString(R.string.highscore0) + lastHighScore);
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
        if (getIntent().getStringExtra("Card").equals("Camera")) {
            Bundle extras = getIntent().getBundleExtra("Data");
            Bitmap image = (Bitmap) extras.get("data");
            Bitmap rotated = rotate(image);
            bonus = new Image(this, "Bonus", rotated, true);
        }
    }
    private Bitmap rotate(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotated;
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
        currentInc++;
        currentIndex = currentInc % 3;
        this.match = randomMatchPhoto(currentIndex);
        this.current = (Image) randomCurrentPhoto(currentIndex);

        matchphoto.setImageResource(match.getDrawImage());


        if(current.getBitmap() != null) {
            currentphoto.setImageBitmap(current.getBitmap());
        } else {
            currentphoto.setImageResource(current.getDrawImage());
        }
    }

    private void newCurrentPhoto() {
        this.current = (Image) randomCurrentPhoto(currentIndex);
        currentphoto.setImageResource(current.getDrawImage());

        if(current.getBitmap() != null) {
            currentphoto.setImageBitmap(current.getBitmap());
        }
    }

    private Image randomMatchPhoto(int i) {
        Random r = new Random();
        int j = r.nextInt(10);
        return images[i][j];
    }

    private ImageView randomCurrentPhoto(int i) {
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
                timer.setText(getString(R.string.time0) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText(getString(R.string.time));
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
        if(currentScore + results >= 0) {
            currentScore += results;
        }
        score.setText(getString(R.string.score0) + currentScore);
        if (currentScore > getHighestScore()) {
            highscoreView.setText(getString(R.string.highscore0) + currentScore);
        }
        score.setText(getString(R.string.score0) + currentScore);
        highscoreView.setText(getString(R.string.highscore0) + getHighestScore());
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
    public void onDialogNeutralClick() {
        Toast.makeText(GameActivity.this, getString(R.string.saveName), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    public int getLowestScore() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        return highscore.getInt("intkey" + getMinKey(),0);
    }

    public int getHighestScore() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        for (int i = 0; i < 9; i++) {
            int value = i+1;
            String key = "intkey"+ value;
            if (lastHighScore <= highscore.getInt(key,0)) {
                lastHighScore = highscore.getInt(key,0);
            }
        }
        return lastHighScore;
    }

    public void saveHighscore(int score, String name) {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        SharedPreferences.Editor editor = highscore.edit();
        if (score > highscore.getInt("intkey" + getMinKey(),0)) {
            editor.remove("intkey" + getMinKey());
            editor.remove("stringkey" + getMinKey());
            editor.putString("stringkey" + getMinKey(), name);
            editor.putInt("intkey" + getMinKey(), score);
        }
        editor.commit();
    }

    public int getMinKey() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        int minKey = 1;
        for (int i = 2; i <= 5; i++) {
            int key = i;
            if (highscore.getInt("intkey" + key, 0) <= highscore.getInt("intkey" + minKey, 0)) {
                minKey = key;
            }
        }
        return minKey;
    }

    public static void setDefaultImage() {

    }
}
