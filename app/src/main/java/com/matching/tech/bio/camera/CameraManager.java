package com.matching.tech.bio.camera;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.Surface;

import com.matching.tech.bio.utilities.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraManager implements ICamera, Camera.PictureCallback, Camera.PreviewCallback {

    private boolean running = false;
    private static final double ASPECT_TOLERANCE = 0.1;
    private Camera camera = null;
    private int cameraId;
    private boolean frontCameraActive;
    private int displayOrientation = 0;
    private Dimension previewAspect;
    private Events listener;
    public static final String TAG = CameraManager.class.getSimpleName();
    public CameraManager(Events listener){ //0 for back and 1 for front
        this.listener = listener;
        cameraId = findFrontFacingCamera();

    }
    /**
     * init(Dimension aspect, Dimension img, int displayRotation, SurfaceTexture previewScreen,int cameraFacing,String flashMode) ICamera listener call back method is used to initialize the camera.
     * @param aspect is the aspect ratio.
     * @param img is the image display dimension.
     * @param previewScreen is the SurfaceTexture show the live preview.
     * @param cameraFacing is camera facing whether Front camera or back camera.
     * @param flashMode is the camera flash mode i.e ON or OFF.
     * @return nothing
     * */
    @Override
    public boolean init(Dimension aspect, Dimension img, int displayRotation, SurfaceTexture previewScreen,int cameraFacing,String flashMode) {

        boolean result  = true;
        try{
            if(isRunning()){
                stopPreview();
            }
            release();
            _init(aspect, img, displayRotation, previewScreen,cameraFacing,flashMode);
            if(isRunning()){
                startPreview();
            }
        }catch (Exception e){

        }
        return result;
    }
    /**
     * _init(Dimension aspect, Dimension img, int displayRotation, SurfaceTexture previewScreen,int cameraFacing,String flashMode) ICamera listener call back method is used to initialize the camera parameters.
     * @param aspect is the aspect ratio.
     * @param img is the image display dimension.
     * @param previewScreen is the SurfaceTexture show the live preview.
     * @param cameraFacing is camera facing whether Front camera or back camera.
     * @param flashMode is the camera flash mode i.e ON or OFF.
     * */
    private void _init(Dimension aspect, Dimension img, int displayRotation, SurfaceTexture previewScreen,int cameraFacing,String flashMode) {
        try {
            this.camera = Camera.open(cameraFacing);
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo);
            Camera.Parameters parameters = camera.getParameters();

            if(cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK){
                parameters.setFlashMode(flashMode);
                LogUtils.debug(TAG,"FLASH MODE :: "+flashMode+" ---> Camera Facing :: "+cameraFacing);
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }else{
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                LogUtils.debug(TAG,"FLASH MODE :: "+flashMode+" ---> Camera Facing :: "+cameraFacing);
            }


            parameters.setRotation(getCameraRotation(displayRotation, cameraInfo));
            displayOrientation = getDisplayOrientation(displayRotation, cameraInfo);
            camera.setDisplayOrientation(displayOrientation);
            frontCameraActive = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;

            camera.setParameters(parameters);
            camera.setPreviewCallback(this);
            camera.setPreviewTexture(previewScreen);
        } catch (IOException e) {
            LogUtils.debug(TAG,"Exception in _init :: "+e.getMessage());
        }
    }
    /**
     * getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) method is used to get the Optimal preview size based on available camera sizes.
     * @param sizes is the list of available sizes.
     * @param width is the width of the preview
     * @param height is the height of the preview.
     * @return  it will return best camera size for preview and capture.*/
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)height / width;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = height;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    /**
     * isInited() is the ICamera call back method to check whether the camera is initialized or not
     * @return it returns boolean value indicating camera is initialized or not.
     * */
    @Override
    public boolean isInited() {
        return camera !=null;
    }
    /**
     * release() is the ICamera call back method to release camera.
     * */
    @Override
    public void release() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    /**
     * startPreview() is the ICamera call back method to start camera preview.
     * */
    @Override
    public void startPreview() {
        if (camera != null) {
            camera.setPreviewCallback(this);
            camera.startPreview();
            running = true;
        }
    }
    /**
     * stopPreview() is the ICamera call back method to stop camera preview.
     * */
    @Override
    public void stopPreview() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            running = false;
        }
    }
    /**
     * isRunning() is the ICamera call back method to check camera is running or not.
     * @return it returns boolean value indicating camera is still running or not.
     * */
    @Override
    public boolean isRunning() {
        return this.running;
    }

    /**
     * capture() is the ICamera call back method to capture the image.
     * */
    @Override
    public void capture() {
        try {
            if( camera != null )
                camera.takePicture(null, null, this);
        } catch (Exception e) {
            LogUtils.debug(TAG,"-------------Failed to takePicture()-----------");
        }
    }
    /**
     * getNbCameras() is the ICamera call back method to get the number of cameras connected to mobile.
     * @return it returns number of cameras connected.
     * */
    @Override
    public int getNbCameras() {
        return Camera.getNumberOfCameras();
    }
    /**
     * getActiveCameraId() is the ICamera call back method to get the active camera id.
     * @return it returns active camera id.
     * */
    @Override
    public int getActiveCameraId() {
        return cameraId;
    }
    /**
     * isFrontCameraActive() is the ICamera call back method to check whether the front camera is active or not.
     * @return it returns boolean value indicating front camera is active or not.
     * */
    @Override
    public boolean isFrontCameraActive() {
        return frontCameraActive;
    }

    /**
     * getPreviewAspect() is the ICamera call back method to get the preview aspect ration dimension.
     * @return it returns preview dimension.
     * */
    @Override
    public Dimension getPreviewAspect() {
        return previewAspect;
    }
    /**
     * findFrontFacingCamera() is the ICamera call back method to get front facing camera id.
     * @return it returns camera facing.
     * */
    public static int findFrontFacingCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return i;
            }
        }
        return 0;
    }
    /**
     * findBackFacingCamera() is the ICamera call back method to get back facing camera id.
     * @return it returns camera facing.
     * */
    public static int findBackFacingCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return i;
            }
        }
        return 0;
    }
    /**
     * onPictureTaken() is the ICamera call back method .it will call when the user has taken the picture.
     * */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        LogUtils.debug(TAG,"onpicture taken ::");
        Photo photo = getPhoto(data, camera, displayOrientation);
        if( photo != null )
            listener.onCapture( photo );
    }
    /**
     * onPreviewFrame() is the ICamera call back method to show preview frame.
     * */
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Photo photo = getPhoto(data, camera, displayOrientation);
        if( photo != null )
            listener.onPreview( photo );
    }
    /**
     * getPhoto(byte[] data, Camera camera, int displayOrientation) method is used to get the captured image in Photo object from the captured data.
     * @param data is the captured photo bytes.
     * @param camera is the camera object.
     * @param displayOrientation is the camera display orientation
     * @return it returns Photo object containing image data.
     * */
    public static Photo getPhoto(byte[] data, Camera camera, int displayOrientation) {
        if( camera == null )
            return null;

        Camera.Parameters parameters = camera.getParameters();
        Camera.Size previewSize = parameters.getPreviewSize();
        int rotation = inverse(displayOrientation);
        int imageFormat = parameters.getPreviewFormat();

        if(imageFormat == ImageFormat.NV21)
            return new Photo(data, previewSize, rotation);
        else
            return null;
    }
    /**
     * getDisplayOrientation(int displayRotation, Camera.CameraInfo cameraInfo) method is used to get the display orientation.
     * @param displayRotation is the camera display rotation.
     * @param cameraInfo is the camera info object containing camera facing and other params.
     * @return it returns Display Orientation.
     * */

    public static int getDisplayOrientation(int displayRotation, Camera.CameraInfo cameraInfo) {

        int degrees = 0;
        switch (displayRotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        }
        else {  // back-facing camera
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }

        return result;
    }

    /**
     * inverse( int degrees ) method is used to invert the image based on the degree.
     * @param degrees is value to inverse
     * @return it will return inverted degrees*/
    public static int inverse( int degrees ) {
        return 360 - (degrees % 360);
    }

    /**
     * getCameraRotation(int displayRotation, Camera.CameraInfo cameraInfo) method is used to get the display Rotation.
     * @param displayRotation is the camera display rotation.
     * @param cameraInfo is the camera info object containing camera facing and other params.
     * @return it returns Camera Rotation angle.
     * */
    public static int getCameraRotation(int displayRotation, Camera.CameraInfo cameraInfo) {

        int degrees = 0;
        switch (displayRotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degrees) % 360;
        }
        else {  // back-facing camera
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }

        return result;
    }
    /**
     * getPictureSize(List<Camera.Size> supportedPictureSizes, int displayOrientation, int width, int height) method is used to get the Optimal Picture size based on available camera sizes.
     * @param supportedPictureSizes is the list of available sizes.
     * @param displayOrientation is the camera displayOrientation.
     * @param width is the width of the picture
     * @param height is the height of the picture.
     * @return  it will return best camera size for capture.*/
    public static Camera.Size getPictureSize(List<Camera.Size> supportedPictureSizes, int displayOrientation, int width, int height) {
        ArrayList<Camera.Size> matchingSizes = new ArrayList<>();

        double targetAspect = (double) width / height;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : supportedPictureSizes) {
            double aspect = (double) getSizeWidth(size, displayOrientation) / getSizeHeight(size, displayOrientation);
            double diff = Math.abs(aspect - targetAspect);

            if (diff < minDiff) {
                minDiff = diff;
                matchingSizes.clear();
                matchingSizes.add(size);
            }
            else if (Math.abs(diff - minDiff) < ASPECT_TOLERANCE) {
                matchingSizes.add(size);
            }
        }

        Camera.Size bestSize = null;

        for (Camera.Size size : matchingSizes) {
            if (bestSize == null || size.width > bestSize.width) {
                bestSize = size;
            }
        }

        if (bestSize == null) {
            return supportedPictureSizes.get(0);
        }
        else {
            return bestSize;
        }
    }
    /**
     * getPreviewSize(List<Camera.Size> supportedPictureSizes, int displayOrientation, int width, int height) method is used to get the Optimal Preview size based on available camera sizes.
     * @param supportedPreviewSizes is the list of available sizes.
     * @param displayOrientation is the camera displayOrientation.
     * @param width is the width of the Preview
     * @param height is the height of the Preview.
     * @return  it will return best camera size for Preview.*/

    public static Camera.Size getPreviewSize(List<Camera.Size> supportedPreviewSizes, int displayOrientation, int width, int height) {
        ArrayList<Camera.Size> matchingSizes = new ArrayList<>();

        double targetAspect = (double) width / height;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : supportedPreviewSizes) {
            double aspect = (double) getSizeWidth(size, displayOrientation) / getSizeHeight(size, displayOrientation);
            double diff = Math.abs(aspect - targetAspect);

            if (diff < minDiff) {
                minDiff = diff;
                matchingSizes.clear();
                matchingSizes.add(size);
            }
            else if (Math.abs(diff - minDiff) < ASPECT_TOLERANCE) {
                matchingSizes.add(size);
            }
        }

        Camera.Size bestSize = null;
        int widthDiff = Integer.MAX_VALUE;

        for (Camera.Size size : matchingSizes) {
            int diff = Math.abs(getSizeWidth(size, displayOrientation) - width);

            if (bestSize == null || diff < widthDiff) {
                bestSize = size;
                widthDiff = diff;
            }
        }

        if (bestSize == null) {
            return supportedPreviewSizes.get(0);
        }
        else {
            return bestSize;
        }
    }

    /**
     * getSizeWidth(Camera.Size size, int displayOrientation) method is used to get the Size Width.
     * @param size is the camera sizes.
     * @param displayOrientation is the camera displayOrientation.
     * @return  it will return best camera size.*/
    public static int getSizeWidth(Camera.Size size, int displayOrientation) {
        if (displayOrientation == 0 || displayOrientation == 180) {
            return size.width;
        }
        else {
            return size.height;
        }
    }
    /**
     * getSizeHeight(Camera.Size size, int displayOrientation) method is used to get the Size Height.
     * @param size is the camera sizes.
     * @param displayOrientation is the camera displayOrientation.
     * @return  it will return best camera size.*/
    public static int getSizeHeight(Camera.Size size, int displayOrientation) {
        if (displayOrientation == 0 || displayOrientation == 180) {
            return size.height;
        }
        else {
            return size.width;
        }
    }
}
