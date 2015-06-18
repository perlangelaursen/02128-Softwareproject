package dtugroup.matchorskip;

import android.media.SoundPool;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by perlangelaursen on 16/06/15.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private VerifyFragment verifyFragment;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MIN_DISTANCE = 120;

    public GestureListener(VerifyFragment verifyFragment) {
        this.verifyFragment = verifyFragment;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
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
        } catch (Exception e) {
            // nothing for now
        }


        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        verifyFragment.start("bonus");
        return true;
    }
}
