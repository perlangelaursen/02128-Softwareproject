package dtugroup.matchorskip;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by perlangelaursen on 14/06/15.
 */
public class Image extends ImageView {
    private String id;
    private int drawImage;

    public boolean isBonus() {
        return bonus;
    }

    private boolean bonus;

    public Image(Context context, String id, int drawImage, boolean bonus) {
        super(context);
        this.id = id;
        this.drawImage = drawImage;
        this.bonus = bonus;
    }

    public int getDrawImage() {
        return drawImage;
    }

    public String getID() {
        return id;
    }


}
