package com.nstuttle.datacentertoolkit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Flashlight extends AppCompatActivity {
    boolean isFlashLightOn = false;
    //Chks Flash ON/OFF, enables/disables
    public void runFlashlight(boolean flashSpt, View view) {
        if (flashSpt) {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (isFlashLightOn) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                view.setBackgroundColor(Color.WHITE);
                isFlashLightOn = false;
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                view.setBackgroundColor(Color.argb(125,75,236,90));
                isFlashLightOn = true;
            }
        } else {
            showNoFlashAlert();
        }
    }
    //Method shows alert "flashlight not supported"
    private void showNoFlashAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Your device hardware does not support Flashlight.")
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Oops!")
                .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
