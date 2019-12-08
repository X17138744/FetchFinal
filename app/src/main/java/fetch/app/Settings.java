package fetch.app;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Settings extends AppCompatActivity {
        //permissions also referenced in manifest

        //instances of switch and activity
        Switch courseLocationSwitch, fineLocationSwitch, internetSwitch;
        private Activity activity;
        //int later needed to identify requested permission
        private int COURSE_LOCATION_CODE=1;
        private int FINE_LOCATION_CODE=2;
        private int INTERNET_CODE=3;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //activity needed to set context and enable showing of permissions dialogs
            activity = this;

            fineLocationSwitch = findViewById(R.id.fineLocationSwitch);
            fineLocationSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fineLocationSwitch.isChecked()) {
                        //if switch is checked/enabled, run the below method
                        requestFineLocPermission();
                    } else {
                        //starts intent to open app permissions etc in system settings in order to revoke permissions
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
            });
            courseLocationSwitch = findViewById(R.id.courseLocationSwitch);
            courseLocationSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (courseLocationSwitch.isChecked()) {
                        //if switch is checked/enabled, run the below method
                        requestCourseLocPermission();
                    } else {
                        //starts intent to open app permissions etc in system settings in order to revoke permissions
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
            });
            internetSwitch = findViewById(R.id.internetSwitch);
            internetSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (internetSwitch.isChecked()) {
                        //if switch is checked/enabled, run the below method
                        requestInternetPermission();
                    } else {
                        //starts intent to open app permissions etc in system settings in order to revoke permissions
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
            });
        }
        private void requestFineLocPermission(){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){ //points to manifest to which permission to enable
                new AlertDialog.Builder(this)
                        .setTitle("Permission for Fine Location access") //what permission is being requested
                        .setMessage("Permission needed to access your precise location") //why is it needed
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() { //positive button if use grants permission
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //opens systems dialog for asking user for permission
                                ActivityCompat.requestPermissions(Settings.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            // if user denys permission, dismiss the dialog, and return the switch to false/off state
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                fineLocationSwitch.setChecked(false);
                            }
                        })
                        .create().show();
            } else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_CODE);
            }
        }
        private void requestCourseLocPermission(){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){ //points to manifest to which permission to enable
                new AlertDialog.Builder(this)
                        .setTitle("Permission for Course Location access") //what permission is being requested
                        .setMessage("Permission needed to access your approximate location") //why is it needed
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() { //positive button if use grants permission
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //opens systems dialog for asking user for permission
                                ActivityCompat.requestPermissions(Settings.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},COURSE_LOCATION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            // if user denys permission, dismiss the dialog, and return the switch to false/off state
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                courseLocationSwitch.setChecked(false);
                            }
                        })
                        .create().show();
            } else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},COURSE_LOCATION_CODE);
            }
        }
    private void requestInternetPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)){ //points to manifest to which permission to enable
            new AlertDialog.Builder(this)
                    .setTitle("Permission for app internet access") //what permission is being requested
                    .setMessage("Permission needed to access internet in-app") //why is it needed
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() { //positive button if use grants permission
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //opens systems dialog for asking user for permission
                            ActivityCompat.requestPermissions(Settings.this, new String[] {Manifest.permission.INTERNET},INTERNET_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        // if user denys permission, dismiss the dialog, and return the switch to false/off state
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            internetSwitch.setChecked(false);
                        }
                    })
                    .create().show();
        } else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},INTERNET_CODE);
        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if(requestCode == FINE_LOCATION_CODE){
                //lets user know if permission granting/denying was successful
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            else if(requestCode == COURSE_LOCATION_CODE){
                //lets user know if permission granting/denying was successful
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            else if(requestCode == INTERNET_CODE){
                //lets user know if permission granting/denying was successful
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
