package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by annaolgaardnielsen on 16/06/15.
 */
public class HighScoreActivity extends Activity {
    private SharedPreferences highscore;
    private String[] name = new String[10];
    private int[] point = new int[10];
    private ImageView backImageView;
    private TextView highScoreTitle, point1, name1, point2, name2, point3,
            name3, point4, name4, point5, name5;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        textViewSetupAndFontSetting();

        backImageView = (ImageView) findViewById(R.id.backImg);
        backImageView.setImageResource(R.drawable.back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void textViewSetupAndFontSetting() {
        highScoreTitle = (TextView) findViewById(R.id.highscoreTitle);
        highScoreTitle.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        point1 = (TextView) findViewById(R.id.point1);
        point1.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        name1 = (TextView) findViewById(R.id.name1);
        name1.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        point2 = (TextView) findViewById(R.id.point2);
        point2.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        name2 = (TextView) findViewById(R.id.name2);
        name2.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        point3 = (TextView) findViewById(R.id.point3);
        point3.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        name3 = (TextView) findViewById(R.id.name3);
        name3.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        point4 = (TextView) findViewById(R.id.point4);
        point4.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        name4 = (TextView) findViewById(R.id.name4);
        name4.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        point5 = (TextView) findViewById(R.id.point5);
        point5.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        name5 = (TextView) findViewById(R.id.name5);
        name5.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        reset = (Button) findViewById(R.id.reset);
        reset.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));
    }

    public void reset(View view) {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        SharedPreferences.Editor editor = highscore.edit();
        editor.clear();
        editor.commit();
        onResume();

    }

    public void setTextView(int nameId, int scoreId, int place) {
        TextView scoreView = (TextView) findViewById(scoreId);
        TextView nameView = (TextView) findViewById(nameId);
        scoreView.setText("" + point[place-1]);
        nameView.setText("" + name[place - 1]);
    }

    public void initArray() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);

        for (int i = 1; i <= 5; i++) {
            name[i-1] = highscore.getString("stringkey" + i, "");
            point[i-1] = highscore.getInt("intkey" + i, 0);
        }
    }

    public void update() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);
        initArray();
        sort();
    }

    public void sort() {
        int temp1 = 0;
        String temp2 = null;
        for(int i = 1; i < 10; i++) {
            int key = point[i];
            int j = i - 1;
            while(j >= 0 && key > point[j]) {
                temp1 = point[j+1];
                temp2 = name[j+1];
                point[j + 1] = point[j];
                name[j + 1] = name[j];
                point[j] = temp1;
                name[j] = temp2;
                j--;
            }
            point[j + 1] = key;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout layout = (GridLayout) findViewById(R.id.gridLayout);
        layout.getBackground().setAlpha(40);

        update();
        setTextView(R.id.name1, R.id.point1, 1);
        setTextView(R.id.name2,R.id.point2,2);
        setTextView(R.id.name3,R.id.point3,3);
        setTextView(R.id.name4,R.id.point4,4);
        setTextView(R.id.name5,R.id.point5,5);
    }

}
