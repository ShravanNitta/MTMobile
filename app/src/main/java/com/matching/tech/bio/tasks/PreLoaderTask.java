package com.matching.tech.bio.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.matching.tech.bio.R;
import com.matching.tech.bio.activities.LoginActivity;

import java.io.IOException;
import java.io.InputStream;

public class PreLoaderTask extends AsyncTask<String, Void, String> {

    private Activity parentActivity;

    public PreLoaderTask(Activity parentActivity){
        this.parentActivity = parentActivity;
    }

    /**
     * onPreExecute() method is the android call back method to handle any UI task.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /**
     * doInBackground(String... params) method is android call back method to do background task.
     * @return it returns the result to the onPostExecute(String result) method.
     */
    @Override
    protected String doInBackground(String... params) {


        return null;
    }
    /** onPostExecute(String result) method will execute once the background task finished.
    *
    */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Intent intent= new Intent(parentActivity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        parentActivity.startActivity(intent);
        parentActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        parentActivity.finish();

    }
    /** onProgressUpdate(Void... values) method is used to show the progress of background task.
     *
     */
    @Override
    protected void onProgressUpdate(Void... values) {}

    private byte[] getFileBytes(int fileName){
        byte[] buffer = null;
        try {
            InputStream inputStream = parentActivity.getResources().openRawResource(fileName);
            buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
