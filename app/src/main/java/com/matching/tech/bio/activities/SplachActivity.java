package com.matching.tech.bio.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.matching.tech.bio.R;
import com.matching.tech.bio.tasks.PreLoaderTask;
import com.matching.tech.bio.utilities.Constants;
import com.matching.tech.bio.utilities.LogUtils;
import com.matching.tech.bio.utilities.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SplachActivity extends AppCompatActivity {

    private String[] perms;
    private Utilities util;
    private String language = "en";
    private LinkedHashMap<String,Boolean> allPermissions;
    private ArrayList<String> permissionsToRequest = new ArrayList();
    public static final String TAG = SplachActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        util = new Utilities(this);
        perms = getResources().getStringArray(R.array.all_permissions);
        allPermissions = new LinkedHashMap<>();
        for(String permission:perms){
            allPermissions.put(permission,false);
        }

        checkPermissionsToRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0){
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), Constants.SUCCESS_CODE);
            }else{
                LogUtils.debug(TAG,"::::::::::::::::ALL PERMISSIONS GRANTED::::::::::::::: ");
                startBackgroundProcess();
            }
        }else{
            LogUtils.debug(TAG,"::::::::::::::::SDK BELOW 23 SO ALL PERMISSIONS GRANTED::::::::::::::: ");
            startBackgroundProcess();
        }

    }
    private void checkPermissionsToRequest(){

        for(String permission : allPermissions.keySet()){
            if(hasPermission(permission)){
                allPermissions.put(permission,true);
            }else{
                allPermissions.put(permission,false);
            }
        }
        for(String permission : allPermissions.keySet()){
            if(!allPermissions.get(permission)){
                permissionsToRequest.add(permission);
            }
        }
    }
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    /**
     * onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) is the android call back method .
     * it will execute when the user accepts or denies the permission on permission dialog.
     */
    @NonNull
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case Constants.SUCCESS_CODE:
                for(int i = 0;i<grantResults.length;i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        permissionsToRequest.remove(permissions[i]);
                    }
                }
                for(String permission : allPermissions.keySet()){
                    if(permissionsToRequest.contains(permission)){
                        allPermissions.put(permission,false);
                    }else{
                        allPermissions.put(permission,true);
                    }
                }
                if (permissionsToRequest.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if(shouldShowRequestPermissionRationale(permissionsToRequest.get(0))){
                            View view =  util.buildConfirmationErrorDialog(getResources().getString(R.string.confirmation_en),"Please allow access to location permissions");
                            TextView ok_tv = view.findViewById(R.id.ok);
                            ok_tv.setTypeface(util.getTypeFace(Constants.AR.equalsIgnoreCase(language)?"regular":"bold"));

                            android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(SplachActivity.this);
                            dialogBuilder.setView(view);
                            final android.app.AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            ok_tv.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(View v) {
                                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), Constants.SUCCESS_CODE);
                                    alertDialog.dismiss();

                                }
                            });
                        }else{
                            //user selected Never ask me again here  and rejected this permission
                            View view =  util.buildConfirmationErrorDialog(getResources().getString(R.string.confirmation_en),"Application will not function properly without this permission");
                            TextView ok_tv = view.findViewById(R.id.ok);
                            ok_tv.setTypeface(util.getTypeFace(Constants.AR.equalsIgnoreCase(language)?"regular":"bold"));

                            android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(SplachActivity.this);
                            dialogBuilder.setView(view);
                            final android.app.AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            ok_tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LogUtils.debug(TAG,"Permission denied ::so not initializing 3rd party libraries " );
                                    Intent intent= new Intent(SplachActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    alertDialog.dismiss();

                                }
                            });
                        }

                    }
                }else{
                    startBackgroundProcess();
                }
                break;
        }
    }
    /**
     * startBackgroundProcess() method will start the background task and load the below libraries in the background.
     * 1.Innovatrics Segmentation ( For fingerprint segmentation).
     * 2.Innovatrics IFace.( For Face ICAO Check).
     * 3.Innovatrics Ansi ISO ( For fingerprint matching and duplicate checks).
     */
    private void startBackgroundProcess(){
        runOnUiThread(new Runnable() {
            public void run() {
                PreLoaderTask task = new PreLoaderTask(SplachActivity.this);
                task.execute();
            }
        });
    }
}
