package com.matching.tech.bio.devices.colombo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.integratedbiometrics.ibscanultimate.IBScan;
import com.integratedbiometrics.ibscanultimate.IBScanDevice;
import com.integratedbiometrics.ibscanultimate.IBScanDeviceListener;
import com.integratedbiometrics.ibscanultimate.IBScanException;
import com.integratedbiometrics.ibscanultimate.IBScanListener;
import com.matching.tech.bio.R;
import com.matching.tech.bio.activities.Home;
import com.matching.tech.bio.activities.LoginActivity;
import com.matching.tech.bio.common.TaskListener;
import com.matching.tech.bio.utilities.Constants;
import com.matching.tech.bio.utilities.LogUtils;
import com.matching.tech.bio.utilities.Utilities;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class ColomboFingerService implements IBScanListener, IBScanDeviceListener {

    private Activity parentActivity;
    private Home homeActivity;
    LoginActivity loginActivity;
    private TextView liveView;
    private Bitmap bitmap;
    private IBScan ibScan;
    private IBScanDevice ibScanDevice;
    private IBScanDevice.ImageType imageType = IBScanDevice.ImageType.TYPE_NONE;
    private PlaySound playSound = new PlaySound();

    private ImageView capturedImage,rh,lh;
    private LinearLayout show_hand;
    private Button scan_finger;
    private TaskListener listener;
    private static String finger;
    private static int fingerPosition;
    private IBScanException.Type deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
    private Utilities utilities;
    private boolean isUserLoggedIn = false;
    public static final String TAG = ColomboFingerService.class.getSimpleName();

    public ColomboFingerService(LoginActivity parentActivity, TextView liveView, ImageView capturedImage, Button scan_finger,TaskListener listener){

        this.parentActivity = parentActivity;
        this.listener = listener;
        this.loginActivity = parentActivity;
        this.liveView = liveView;
        this.capturedImage = capturedImage;
        this.scan_finger = scan_finger;
        try{
            ibScan = IBScan.getInstance(parentActivity.getApplicationContext());
            ibScan.setScanListener(this);
        }catch (Exception e){
            deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
        }
        utilities = new Utilities(parentActivity);
        ColomboFingerService.finger = null;
        ColomboFingerService.fingerPosition = 1;
        isUserLoggedIn = false;
        handleTransitionToRefresh();
    }

    public ColomboFingerService(Home parentActivity, TextView liveView, ImageView capturedImage, Button scan_finger,TaskListener listener){

        this.parentActivity = parentActivity;
        this.listener = listener;
        this.homeActivity = parentActivity;
        this.liveView = liveView;
        this.capturedImage = capturedImage;
        this.scan_finger = scan_finger;
        try{
            ibScan = IBScan.getInstance(parentActivity.getApplicationContext());
            ibScan.setScanListener(this);
        }catch (Exception e){
            LogUtils.debug(TAG,"Exception :: ColomboFingerService() "+e.getMessage());
            deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
        }
        utilities = parentActivity.utils;
        isUserLoggedIn = true;
        handleTransitionToRefresh();
    }

    private void handleTransitionToRefresh(){

        final UsbManager manager = (UsbManager)parentActivity.getApplicationContext().getSystemService(Context.USB_SERVICE);
        final HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        LogUtils.debug(TAG,"DEVICE LIST :: "+deviceList);
        if( null != deviceList && deviceList.size() > 0){
            final Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            while (deviceIterator.hasNext()){
                final UsbDevice device       = deviceIterator.next();
                final boolean   isScanDevice = IBScan.isScanDevice(device);
                if (isScanDevice){
                    final boolean hasPermission = manager.hasPermission(device);
                    if (!hasPermission){
                        ibScan.requestPermission(device.getDeviceId());
                    }else{
                        if(deviceStatus == IBScanException.Type.DEVICE_NOT_INITIALIZED || deviceStatus == IBScanException.Type.DEVICE_INVALID_STATE){
                            init();
                        }
                    }
                }
            }
        }else{
            parentActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    if(!isUserLoggedIn)
                        capturedImage.setVisibility(View.GONE);
                }
            });

            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device_status:R.string.device_status_en)+" - " + parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device_not_found:R.string.device_not_found_en));
        }
    }

    private void showDeviceStatus(final String statusMessage){
        parentActivity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                liveView.setVisibility(View.VISIBLE);
                liveView.setText(Html.fromHtml(statusMessage), TextView.BufferType.SPANNABLE);
            }
        });
    }
    @Override
    public void scanDeviceAttached(int deviceId) {

        if(null != loginActivity){
            LogUtils.debug(TAG,parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" "+deviceId+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.attached:R.string.attached_en));
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" "+deviceId+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.attached:R.string.attached_en));

            if(null != ibScan){
                boolean hasPermission = ibScan.hasPermission(deviceId);
                LogUtils.debug(TAG,"Has Permission :: "+hasPermission);
                if (!hasPermission){
                    ibScan.requestPermission(deviceId);
                }else{
                    if(deviceStatus == IBScanException.Type.DEVICE_NOT_INITIALIZED || deviceStatus == IBScanException.Type.DEVICE_INVALID_STATE){
                        init();
                    }
                }
            }else{
                LogUtils.debug(TAG," ----- Cam capture in progress --------- ");
            }

        }else if(null != homeActivity && !Home.isCameraOpen){
            try {
                LogUtils.debug(TAG,parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" "+deviceId+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.attached:R.string.attached_en));
                showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" "+deviceId+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.attached:R.string.attached_en));
                boolean hasPermission = ibScan.hasPermission(deviceId);
                LogUtils.debug(TAG,"Has Permission :: "+hasPermission);
                if (!hasPermission){
                    ibScan.requestPermission(deviceId);
                }else{
                    if(deviceStatus == IBScanException.Type.DEVICE_NOT_INITIALIZED || deviceStatus == IBScanException.Type.DEVICE_INVALID_STATE){
                        init();
                    }
                }
            } catch (Exception e) {
                LogUtils.debug(TAG,"Has Permission :Exception: "+e.getMessage());
            }
        }else{
            LogUtils.debug(TAG,"Camera is opened..please wait here");
        }

    }

    @Override
    public void scanDeviceDetached(int deviceId) {
        LogUtils.debug(TAG,"scanDeviceDetached :: "+deviceId);
        deviceStatus = IBScanException.Type.DEVICE_INVALID_STATE;
        showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" "+deviceId+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.detached:R.string.detached_en));
        if(!isUserLoggedIn){
            parentActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    scan_finger.setEnabled(true);
                    scan_finger.setVisibility(View.VISIBLE);
                    listener.onTaskCompleted(Constants.FINGERPRINT_CAPTURE_TASK,null);
                }
            });
        }
    }

    @Override
    public void scanDevicePermissionGranted(int deviceId, boolean granted) {
        LogUtils.debug(TAG,"Permission is isGranted :: "+granted);
        if (granted){
            deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.permission_granted:R.string.permission_granted_en)+" "+ deviceId);
            if(deviceStatus == IBScanException.Type.DEVICE_NOT_INITIALIZED || deviceStatus == IBScanException.Type.DEVICE_INVALID_STATE){
                init();
            }
        } else{
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.permission_denied:R.string.permission_denied_en)+" "+ deviceId);
            deviceStatus = IBScanException.Type.DEVICE_INVALID_STATE;
        }
    }

    public void init(){
        try{
            if(deviceStatus != IBScanException.Type.ALREADY_INITIALIZED){
                ibScan.openDeviceAsync(0);
            }

        }catch (IBScanException ibse){
            deviceStatus = ibse.getType();
            LogUtils.debug(TAG,"re init failed......."+ibse.getType().toString());
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device_status:R.string.device_status_en)+" - " + ibse.getType().toString());
            handleTransitionToRefresh();
        }catch (Exception e){
            LogUtils.debug(TAG,"re init EXCEPTION.......");
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device_status:R.string.device_status_en)+" - " + e.getMessage());
            handleTransitionToRefresh();
        }
    }

    @Override
    public void scanDeviceInitProgress(int deviceIndex, final int progressValue) {
        LogUtils.debug(TAG,"initializing device ( " + progressValue + "% )"+" please wait...");
        if(progressValue == 100){
            deviceStatus = IBScanException.Type.ALREADY_INITIALIZED;
        }else{
            deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.initializing_device:R.string.initializing_device_en)+" ( " + progressValue + "% )"+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.please_wait:R.string.please_wait_en));
        }
    }

    @Override
    public void scanDeviceOpenComplete(int deviceIndex, IBScanDevice device, IBScanException exception) {
        LogUtils.debug(TAG,"------------scanDeviceOpenComplete-----------device--------"+device);
        LogUtils.debug(TAG,"------------scanDeviceOpenComplete-----------deviceIndex--------"+deviceIndex);
        if(null != device){
            showDeviceStatus(" - "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device_initialized:R.string.device_initialized_en)+" - ");

            imageType = IBScanDevice.ImageType.TYPE_NONE;
            deviceInitialized(device);
            deviceStatus = IBScanException.Type.ALREADY_INITIALIZED;
            try {
                final String deviceId = ibScanDevice.getProperty(IBScanDevice.PropertyId.PRODUCT_ID);
                LogUtils.debug(TAG,"Device ID :NEW: "+deviceId);

                parentActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        scan_finger.setVisibility(View.VISIBLE);
                        listener.onTaskCompleted(Constants.DEVICE_NAME_TASK,deviceId);
                    }
                });
            } catch (IBScanException e) {
                LogUtils.debug(TAG,"Device Initialization failed :: "+e.getMessage());
            }
        }else{
            String error = (exception == null) ? "(unknown)" : exception.getType().toString();
            showDeviceStatus(parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.device:R.string.device_en)+" " + deviceIndex + parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.could_not_be_initialized:R.string.could_not_be_initialized_en)+"  " + error);
            deviceStatus = IBScanException.Type.DEVICE_NOT_INITIALIZED;
            handleTransitionToRefresh();
        }
    }

    private void deviceInitialized(IBScanDevice device){
        try {
            LogUtils.debug(TAG,"------------deviceInitialized-----------device--------"+device);
            if(null != device){
                device.setProperty(IBScanDevice.PropertyId.ENABLE_POWER_SAVE_MODE, "TRUE");
                String imageW = device.getProperty(IBScanDevice.PropertyId.IMAGE_WIDTH);
                String imageH =device.getProperty(IBScanDevice.PropertyId.IMAGE_HEIGHT);
                int	imageWidth = Integer.parseInt(imageW);
                int	imageHeight = Integer.parseInt(imageH);
                bitmap = toDrawBitmap(imageWidth, imageHeight);
                Vector<String> typeVector = new Vector<>();
                for (IBScanDevice.ImageType imageType : IBScanDevice.ImageType.values()){
                    try{
                        boolean available = device.isCaptureAvailable(imageType, IBScanDevice.ImageResolution.RESOLUTION_500);
                        if (available){
                            typeVector.add(imageType.toDescription());
                        }
                    }catch (IBScanException ibse){
                        showDeviceStatus("Could not check capture availability " + ibse.getType().toString());
                    }
                }
                ibScanDevice = device;
            }

        } catch (IBScanException e) {
            LogUtils.debug(TAG,"Device initialized failed :: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void capture(String finger,int fingerPosition){
        try{
            showDeviceStatus("");
            ColomboFingerService.finger = finger;
            ColomboFingerService.fingerPosition = fingerPosition;

            String styledText = parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.place_your:R.string.place_your_en)+" "+"<b><font color='red'>"+finger+"</font></b>"+" "+parentActivity.getResources().getText(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.on_reader:R.string.on_reader_en);


            showDeviceStatus(styledText);
            imageType = setImageType(fingerPosition);
            LogUtils.debug(TAG,"imageType "+imageType);
            ibScanDevice.beginCaptureImage(imageType, IBScanDevice.ImageResolution.RESOLUTION_500, IBScanDevice.OPTION_AUTO_CAPTURE | IBScanDevice.OPTION_AUTO_CONTRAST);
            ibScanDevice.setScanDeviceListener(this);

        }catch(IBScanException ibse){
            if(deviceStatus == IBScanException.Type.DEVICE_NOT_FOUND && null !=homeActivity){
                homeActivity.captureInProgress = false;
            }
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
            showDeviceStatus("Exception :: capture()"+ibse.getType().toString());
            LogUtils.debug(TAG,"Exception :: capture()"+ibse.getType().toString());
            if(ibse.getType().equals(IBScanException.Type.DEVICE_INVALID_STATE)){
                deviceStatus = IBScanException.Type.DEVICE_INVALID_STATE;
                deviceInitialized(null);
                init();
            }
        }catch (Exception e){
            LogUtils.debug(TAG,"Exception :2:capture "+e.getMessage());
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
            deviceStatus = IBScanException.Type.DEVICE_INVALID_STATE;
            if(null == ibScanDevice){
                LogUtils.debug(TAG,"Re initializing device >>>>>>>>>>>>>");
                deviceStatus = IBScanException.Type.DEVICE_INVALID_STATE;
                init();
            }

        }
    }

    private IBScanDevice.ImageType setImageType(int fingerPosition){
        if(fingerPosition == 23 || fingerPosition == 78){
            LogUtils.debug(TAG,"TOW FINGERS CAPTURE");
            return  IBScanDevice.ImageType.FLAT_TWO_FINGERS;
        }else{
            LogUtils.debug(TAG,"SINGLE FINGERS CAPTURE");
            return  IBScanDevice.ImageType.FLAT_SINGLE_FINGER;
        }
    }
    /**
     * deviceImagePreviewAvailable(IBScanDevice ibScanDevice, IBScanDevice.ImageData imageData) call back fires when the device has preview image(live view).
     * */
    @Override
    public void deviceImagePreviewAvailable(IBScanDevice ibScanDevice, IBScanDevice.ImageData imageData) {
        try {
            ibScanDevice.createBmpEx(imageData.buffer, bitmap);
            if(null != bitmap){
                parentActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        capturedImage.setImageBitmap(bitmap);
                    }
                });
            }else {
                showDeviceStatus("Captured Data :NULL: ");
            }

        } catch (IBScanException e) {
//            scan_finger.setBackground(ContextCompat.getDrawable(parentActivity, R.drawable.button_next_style));
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deviceImageResultExtendedAvailable(IBScanDevice ibScanDevice, IBScanException imageStatus, IBScanDevice.ImageData imageData, IBScanDevice.ImageType imageType, int i, IBScanDevice.ImageData[] imageDatas, IBScanDevice.SegmentPosition[] segmentPositions) {
        try {
            ibScanDevice.createBmpEx(imageData.buffer, bitmap);
            if(null != bitmap){
                parentActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        capturedImage.setImageBitmap(bitmap);
                    }
                });
            }

            captureDone(imageData, imageType, imageDatas);
        } catch (IBScanException e1) {
//            scan_finger.setBackground(ContextCompat.getDrawable(parentActivity, R.drawable.button_next_style));
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
        }
    }
    private void captureDone(final IBScanDevice.ImageData image, IBScanDevice.ImageType imageType, IBScanDevice.ImageData[] imageDatas){
        try {

            deviceInitialized(null);
            parentActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {

                    try {
                        CaptureData captureData = new CaptureData();
                        captureData.setCaptureStatus("SUCCESS");
                        captureData.setWidth(image.width);
                        captureData.setHeight(image.height);
                        captureData.setNfiqScore(ibScanDevice.calculateNfiqScore(image));
                        captureData.setCapturedBytes(image.buffer);
                        captureData.setCapturedBitmap(image.toBitmap());
                        captureData.setCapturedFinger(fingerPosition);
                        listener.onTaskCompleted(Constants.FINGERPRINT_CAPTURE_TASK,captureData);
                    } catch (IBScanException e) {
//                        scan_finger.setBackground(ContextCompat.getDrawable(parentActivity, R.drawable.button_next_style));
                        scan_finger.setEnabled(true);
                        scan_finger.setVisibility(View.VISIBLE);
                        showDeviceStatus(" Error in Capture done()"+e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
//            scan_finger.setBackground(ContextCompat.getDrawable(parentActivity, R.drawable.button_next_style));
            scan_finger.setEnabled(true);
            scan_finger.setVisibility(View.VISIBLE);
            showDeviceStatus(" Error in Capture done()"+e.getMessage());
        }
    }

    @Override
    public void deviceAcquisitionBegun(IBScanDevice ibScanDevice, IBScanDevice.ImageType imageType) {
        if (imageType.equals(IBScanDevice.ImageType.ROLL_SINGLE_FINGER)){
            showDeviceStatus("Beginning acquisition...roll finger left");
        }
    }

    @Override
    public void deviceAcquisitionCompleted(IBScanDevice ibScanDevice, IBScanDevice.ImageType imageType) {
        try {
            if (imageType.equals(IBScanDevice.ImageType.ROLL_SINGLE_FINGER)){
                showDeviceStatus("Completed acquisition...roll finger right");
            }else{
                playSound.playSound();
            }
        } catch (Exception e) {
            LogUtils.debug(TAG,"------------------Exception while playing sound----------------------"+e.getMessage());
            showDeviceStatus("Unable to play capture sound");
        }
    }
    @Override
    public void deviceCommunicationBroken(IBScanDevice ibScanDevice) {
        parentActivity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
//                scan_finger.setBackground(ContextCompat.getDrawable(parentActivity, R.drawable.button_next_style));
                scan_finger.setEnabled(true);
                scan_finger.setVisibility(View.VISIBLE);
                listener.onTaskCompleted(Constants.FINGERPRINT_CAPTURE_TASK,null);
            }
        });

        showDeviceStatus(parentActivity.getResources().getString(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.communication_break:R.string.communication_break_en));
    }
    @Override
    public void deviceFingerCountChanged(IBScanDevice ibScanDevice, IBScanDevice.FingerCountState fingerCountState) {

    }
    @Override
    public void deviceFingerQualityChanged(IBScanDevice ibScanDevice, IBScanDevice.FingerQualityState[] fingerQualityStates) {

    }

    @Override
    public void deviceImageResultAvailable(IBScanDevice ibScanDevice, IBScanDevice.ImageData imageData, IBScanDevice.ImageType imageType, IBScanDevice.ImageData[] imageDatas) {

    }

    @Override
    public void devicePlatenStateChanged(IBScanDevice ibScanDevice, IBScanDevice.PlatenState platenState) {

    }

    @Override
    public void deviceWarningReceived(IBScanDevice ibScanDevice, IBScanException e) {

    }

    @Override
    public void devicePressedKeyButtons(IBScanDevice ibScanDevice, int i) {

    }

    @Override
    public void scanDeviceCountChanged(int i) {

    }
    public Bitmap toDrawBitmap(int width,int height){
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap != null){
            final byte[] imageBuffer = new byte[width * height * 4];
        	/*
        	 * The image in the buffer is flipped vertically from what the Bitmap class expects;
        	 * we will flip it to compensate while moving it into the buffer.
        	 */
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    imageBuffer[(y * width + x) * 4] =
                            imageBuffer[(y * width + x) * 4 + 1] =
                                    imageBuffer[(y * width + x) * 4 + 2] =
                                            (byte) 128;
                    imageBuffer[(y * width + x) * 4 + 3] = (byte)255;
                }
            }
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(imageBuffer));
        }
        return (bitmap);
    }
    public void closeDevice(){
        if (ibScanDevice != null){
            try{
                ibScanDevice.close();
                ibScan = null;
                LogUtils.debug(TAG," ------------ Device closed ---------- ");
            }catch (IBScanException ibse){
                showDeviceStatus(parentActivity.getResources().getString(Constants.AR.equalsIgnoreCase(utilities.getLanguage())?R.string.could_not_close_device:R.string.could_not_close_device_en)+" " + ibse.getType().toString());
            }
            ibScanDevice = null;
        }
    }
}
