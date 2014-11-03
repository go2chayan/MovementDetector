package com.github.go2chayan.movementdetector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class MovementDetectActivity extends Activity implements 
SensorEventListener {


	// Variables to hold sensor information
	private SensorManager mSensorManager;
	private float[] mRotationMatrix;
	private float[] mOrientation;
	
	// Text views to show Azimuth, Pitch and Roll
	TextView txtAzimuth;
	TextView txtPitch;
	TextView txtRoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_detect);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        
        // Three textviews to show azimuth, pitch and roll data 
        txtAzimuth = (TextView)findViewById(R.id.txtAzimuth);
		txtPitch = (TextView)findViewById(R.id.txtPitch);
		txtRoll = (TextView)findViewById(R.id.txtRoll);
		
		//initialize rotationmatrix and orientation vector
	    mRotationMatrix = new float[16];
	    mOrientation = new float[3];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movement_detect, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      startTracking();
    }

    @Override
    protected void onPause() {
      super.onPause();
      stopTracking();
    }
    
    /**
     * Starts tracking the user's heading.
     */
    private void startTracking() {
      mSensorManager.registerListener(this,
          mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
          SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Stops tracking the user's heading.
     */
    private void stopTracking() {
      mSensorManager.unregisterListener(this);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

    	// Write code for taking picture
    	
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
		      SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
		      SensorManager.remapCoordinateSystem(mRotationMatrix,
		          SensorManager.AXIS_X, SensorManager.AXIS_Z, mRotationMatrix);
		      SensorManager.getOrientation(mRotationMatrix, mOrientation);

		      txtAzimuth.setText(String.format("%.0f",Math.toDegrees(mOrientation[0])));
		      txtPitch.setText(String.format("%.0f",Math.toDegrees(mOrientation[1])));
		      txtRoll.setText(String.format("%.0f",Math.toDegrees(mOrientation[2])));
		}
		
	}
    
}
