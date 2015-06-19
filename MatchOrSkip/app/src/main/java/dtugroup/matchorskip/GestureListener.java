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
        // Top swipe
        if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            verifyFragment.start("keep");
        }
        // Bottom swipe
        else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            verifyFragment.start("skip");
        }


        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(activity.getCurrent().isBonus()) {
            verifyFragment.start("bonus");
        } else if (activity.getCurrent().isRush()){
            verifyFragment.start("rush");
        }
        return true;
    }
}
