package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
    private TextView highScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
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

    public void backToMainMenu(View view) {
        Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
