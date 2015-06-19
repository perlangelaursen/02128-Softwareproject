package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
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
    private TextView pageNumber;
    private int[] instructionsArray;
    private GestureDetector gestureDetector;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MIN_DISTANCE = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        pageNumber = (TextView) findViewById(R.id.page_number);
        gestureDetector = new GestureDetector(this, new CustomGestureDetector());
        instructions = (ImageView) findViewById(R.id.instructions);
        instructions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
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
        instructionsArray = new int[6];
        instructionsArray[0] = R.drawable.i01;
        instructionsArray[1] = R.drawable.i02;
        instructionsArray[2] = R.drawable.i03;
        instructionsArray[3] = R.drawable.i04;
        instructionsArray[4] = R.drawable.i05;
        instructionsArray[5] = R.drawable.i06;
        page = 0;
        maxPage = instructionsArray.length;
        instructions.setImageResource(instructionsArray[page]);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Top swipe
            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                pgeDown();
            }
            // Bottom swipe
            else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                pgeUp();
            }
            update();
            return true;
        }
    }
}