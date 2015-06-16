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
        scoreView.setText(point[place-1]);
        nameView.setText(name[place-1]);
    }

    @Override
    protected void onResume() {
        super.onResume();

        highscore = this.getSharedPreferences("highscore", MODE_PRIVATE);

        name = new String[10];
        point = new int[10];

        for (int i = 1; i <= 10; i++) {
            name[i-1] = highscore.getString("name" + i, "");
            point[i-1] = highscore.getInt("name" + i, 0);
        }
        setTextView(R.id.name1,R.id.point1,1);
        setTextView(R.id.name2,R.id.point2,2);
        setTextView(R.id.name3,R.id.point3,3);
        setTextView(R.id.name4,R.id.point4,4);
        setTextView(R.id.name5,R.id.point5,5);
        setTextView(R.id.name6,R.id.point6,6);
        setTextView(R.id.name7,R.id.point7,7);
        setTextView(R.id.name8,R.id.point8,8);
        setTextView(R.id.name9,R.id.point9,9);
        setTextView(R.id.name10,R.id.point10,10);

    }

}
