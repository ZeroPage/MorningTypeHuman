package org.zeropage.gdg.morningTypeHuman.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;

import org.zeropage.gdg.morningTypeHuman.R;


public class MapActivity extends FragmentActivity {
    private GoogleMap map;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getMap();
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        if(map != null) {
            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    textView.setText(map.getCameraPosition().target.toString());
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ToDo do somthing when the button is clicked. if you need position of camera, use these.

                map.getCameraPosition().target.latitude
                map.getCameraPosition().target.longitude

                 */
            }
        });
    }
}
