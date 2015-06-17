package dtugroup.matchorskip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class PhotoActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    private ImageView cameraImageView;
    private ImageView defaultImageView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //Buttons
        cameraImageView = (ImageView) findViewById(R.id.cameraImg);
        defaultImageView = (ImageView) findViewById(R.id.defaultImg);
        cameraImageView.setImageResource(R.drawable.bonus);
        defaultImageView.setImageResource(R.drawable.bonus);

        setupListeners();
    }

    private void setupListeners() {
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                }
            }
        });
        defaultImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent defaultIntent = new Intent(PhotoActivity.this, GameActivity.class);
                defaultIntent.putExtra("Card", "Default");
                startActivity(defaultIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Intent gameIntent = new Intent(PhotoActivity.this, GameActivity.class);
                gameIntent.putExtra("Card", "Camera");
                Bundle extras = data.getExtras();
                gameIntent.putExtra("Data", extras);
                startActivity(gameIntent);
            } else {
                Toast.makeText(this, "Photo could not be saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}
