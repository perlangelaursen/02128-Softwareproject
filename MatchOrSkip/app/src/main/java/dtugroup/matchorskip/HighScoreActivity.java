package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by annaolgaardnielsen on 16/06/15.
 */
public class HighScoreActivity extends Activity {

    private SharedPreferences highscore;
    private String[] name;
    private int[] point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

    }

    public void startMenu(View view) {
        Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        Intent intent = new Intent(HighScoreActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void setTextView(int nameId, int scoreId, int place) {
        TextView scoreView = (TextView) findViewById(scoreId);
        TextView nameView = (TextView) findViewById(nameId);
        scoreView.setText(point[10-(place+1)]);
        nameView.setText(name[10-(place+1)]);
    }

    public void update() {
        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);

        for (int i = 1; i <= 10; i++) {
            name[i-1] = highscore.getString("key"+i, "");
            point[i-1] = highscore.getInt("key"+i, 0);
        }

        int temp1;
        String temp2;
        for (int j = 0; j <= 9; j++) {
            for (int k = j; k > 0; k--) {
                if (point[j] < point[j-1]) {
                    temp1 = point[j];
                    temp2 = name[j];
                    point[j] = point[j-1];
                    name[j] = name[j-1];
                    point[j-1] = temp1;
                    name[j-1] = temp2;
                }
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        update();

        setTextView(R.id.name1,R.id.point1,1);
        setTextView(R.id.name2,R.id.point2,2);
        setTextView(R.id.name3,R.id.point3,3);
        setTextView(R.id.name4,R.id.point4,4);
        setTextView(R.id.name5,R.id.point5,5);
        setTextView(R.id.name6,R.id.point6,6);
        setTextView(R.id.name7, R.id.point7, 7);
        setTextView(R.id.name8,R.id.point8,8);
        setTextView(R.id.name9,R.id.point9,9);
        setTextView(R.id.name10,R.id.point10,10);

    }

}
