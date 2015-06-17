package dtugroup.matchorskip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by perlangelaursen on 14/06/15.
 */
public class Image extends ImageView {
    private String id;
    private int drawImage;
    private boolean bonus;
    private Bitmap bitmap;

    public Image(Context context, String id, int drawImage, boolean bonus) {
        super(context);
        this.id = id;
        this.drawImage = drawImage;
        //this.bitmap = BitmapFactory.decodeResource(context.getResources(), drawImage);
        this.bonus = bonus;
    }

    public Image(Context context, String id, Bitmap bitmap, boolean bonus) {
        super(context);
        this.id = id;
        this.bonus = bonus;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if(bitmap == null) {
            this.bitmap = BitmapFactory.decodeResource(getResources(), drawImage);
        }
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


}
