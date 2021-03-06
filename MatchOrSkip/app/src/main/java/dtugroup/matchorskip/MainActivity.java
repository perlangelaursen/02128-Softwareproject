package dtugroup.matchorskip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private Button playbutton, instructions, highscoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSetupWithCustomFont();
    }

    private void buttonSetupWithCustomFont() {
        playbutton = (Button) findViewById(R.id.playbutton);
        playbutton.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        instructions = (Button) findViewById(R.id.instructions);
        instructions.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));

        highscoreButton = (Button) findViewById(R.id.highscoreButton);
        highscoreButton.setTypeface(Typeface.createFromAsset(getAssets(), "ComingSoon.ttf"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.endgame));
        dialog.setMessage(getString(R.string.endGameDialog));
        dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.no), null);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        startActivity(intent);
    }

    public void instructions(View view) {
        Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void highscore(View view) {
        Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
        startActivity(intent);
    }
}
