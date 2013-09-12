package org.zeropage.gdg.morningTypeHuman.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.LaunchActivityController;
import org.zeropage.gdg.morningTypeHuman.model.FileStorage;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // init here.
        FileStorage.init(this);

        LaunchActivityController controller = new LaunchActivityController(this);

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launch, menu);
        return true;
    }
    
}
