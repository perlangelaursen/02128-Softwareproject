package dtugroup.matchorskip;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by perlangelaursen on 14/06/15.
 */
public class Image extends ImageView {
    private String id;
    private int drawImage;
    private boolean bonus;
    private boolean rush;
    private Bitmap bitmap;

    public Image(Context context, String id, int drawImage, boolean bonus, boolean rush) {
        super(context);
        this.id = id;
        this.drawImage = drawImage;
        this.bonus = bonus;
        this.rush = rush;
    }

    public Image(Context context, String id, Bitmap bitmap, boolean bonus, boolean rush) {
        super(context);
        this.id = id;
        this.bonus = bonus;
        this.rush = rush;
        this.drawImage = 0;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isBonus() {
        return bonus;
    }

    public int getDrawImage() {
        return drawImage;
    }

    public String getID() {
        return id;
    }

    public boolean isRush() {
        return rush;
    }
}
