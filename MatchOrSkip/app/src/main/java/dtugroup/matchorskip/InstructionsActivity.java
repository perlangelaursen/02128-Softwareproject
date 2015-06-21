package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dariush on 18/06/2015.
 */

public class InstructionsActivity extends Activity {
    private int page, maxPage;
    private ImageView backImageView;
    private ImageView instructions;
    private TextView pageNumber, imageText;
    private int[] instructionsArray;
    private String[] textArray;
    private GestureDetector gestureDetector;
    private TextView instructions_title;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MIN_DISTANCE = 120;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        titleSetup();

        pageNumber = (TextView) findViewById(R.id.page_number);
        pageNumber.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));
        imageText = (TextView) findViewById(R.id.imageText);
        imageText.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));
        gestureHandlingSetup();
        backButtonSetup();
        imageArraySetup();
        textArraySetup();
    }

    private void titleSetup() {
        instructions_title = (TextView) findViewById(R.id.instructions_title);
        instructions_title.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));
    }

    private void gestureHandlingSetup() {
        gestureDetector = new GestureDetector(this, new CustomGestureDetector());
        instructions = (ImageView) findViewById(R.id.instructions);
        instructions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void backButtonSetup() {
        backImageView = (ImageView) findViewById(R.id.backImg);
        backImageView.setImageResource(R.drawable.back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void imageArraySetup() {
        instructionsArray = new int[8];
        instructionsArray[0] = R.drawable.i01;
        instructionsArray[1] = R.drawable.i02;
        instructionsArray[2] = R.drawable.i03;
        instructionsArray[3] = R.drawable.i04;
        instructionsArray[4] = R.drawable.i05;
        instructionsArray[5] = R.drawable.i06;
        instructionsArray[6] = R.drawable.i07;
        instructionsArray[7] = R.drawable.i08;
        page = 0;
        maxPage = instructionsArray.length;
        instructions.setImageResource(instructionsArray[page]);
    }

    private void textArraySetup() {
        textArray = new String[8];
        textArray[0] = getString(R.string.textarray0);
        textArray[1] = getString(R.string.textarray1);
        textArray[2] = getString(R.string.textarray2);
        textArray[3] = getString(R.string.textarray3);
        textArray[4] = getString(R.string.textarray4);
        textArray[5] = getString(R.string.textarray5);
        textArray[6] = getString(R.string.textarray6);
        textArray[7] = getString(R.string.textarray7);
    }

    public void pgeUp() {
        if (page < maxPage - 1) {
            page++;
        }
    }

    public void pgeDown() {
        if (page > 0) {
            page--;
        }
    }

    public void update() {
        instructions.setImageResource(instructionsArray[page]);
        pageNumber.setText((page + 1) + " / " + maxPage);
        imageText.setText(textArray[page]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (leftSwipe(e1, e2, velocityY)) {
                pgeDown();
            }
            else if (rightSwipe(e1, e2, velocityY)) {
                pgeUp();
            }
            update();
            return true;
        }

        private boolean leftSwipe(MotionEvent e1, MotionEvent e2, float velocityY) {
            return e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY;
        }

        private boolean rightSwipe(MotionEvent e1, MotionEvent e2, float velocityY) {
            return e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY;
        }
    }
}