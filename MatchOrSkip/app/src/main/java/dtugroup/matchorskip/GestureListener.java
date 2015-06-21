package dtugroup.matchorskip;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by perlangelaursen on 16/06/15.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private VerifyFragment verifyFragment;
    private GameActivity activity;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MIN_DISTANCE = 120;

    public GestureListener(VerifyFragment verifyFragment, GameActivity activity) {
        this.verifyFragment = verifyFragment;
        this.activity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (topSwipe(e1, e2, velocityY)) {
            verifyFragment.start("keep");
        }
        else if (bottomSwipe(e1, e2, velocityY)) {
            verifyFragment.start("skip");
        }


        return true;
    }

    private boolean bottomSwipe(MotionEvent e1, MotionEvent e2, float velocityY) {
        return e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY;
    }

    private boolean topSwipe(MotionEvent e1, MotionEvent e2, float velocityY) {
        return e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(isBonusCard()) {
            verifyFragment.start("bonus");
        } else if (isRushHourCard()){
            verifyFragment.start("rush");
        }
        return true;
    }

    private boolean isRushHourCard() {
        return activity.getCurrent().isRush();
    }

    private boolean isBonusCard() {
        return activity.getCurrent().isBonus();
    }
}
