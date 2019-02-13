package com.matching.tech.bio.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

/**
 * ICamera.java  - ICamera interface has basic methods to communicate with the camera.
 * @author Shravan Nitta
 * @version 1.0
 */
public interface ICamera {
    interface Events {
        public void onPreview(Photo photo);
        public void onCapture(Photo photo);
    }
    /**
     * Dimension.java  - Dimension class is used to hold camera preview and capture dimensions.
     */
    class Dimension {
        public Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public final int width;
        public final int height;
    }
    /**
     * Photo.java  - Photo class is used to hold captured data in bytes and preview size and camera rotation.
     */
    class Photo {
        public Photo(byte[] data, Camera.Size previewSize, int rotation) {
            this.data = data;
            this.previewSize = previewSize;
            this.rotation = rotation;
        }

        public final byte[] data;
        public final Camera.Size previewSize;
        public final int rotation;
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
    boolean init(Dimension aspect, Dimension img, int displayRotation, SurfaceTexture previewScreen, int cameraFacing, String flashMode);

    /**
     * isInited() is the ICamera call back method to check whether the camera is initialized or not
     * @return it returns boolean value indicating camera is initialized or not.
     * */
    boolean isInited();

    /**
     * release() is the ICamera call back method to release camera.
     * */
    void release();

    /**
     * startPreview() is the ICamera call back method to start camera preview.
     * */
    void startPreview();

    /**
     * stopPreview() is the ICamera call back method to stop camera preview.
     * */
    void stopPreview();

    /**
     * isRunning() is the ICamera call back method to check camera is running or not.
     * @return it returns boolean value indicating camera is still running or not.
     * */
    boolean isRunning();
    /**
     * capture() is the ICamera call back method to capture the image.
     * */
    void capture();
    /**
     * getNbCameras() is the ICamera call back method to get the number of cameras connected to mobile.
     * @return it returns number of cameras connected.
     * */
    int getNbCameras();
    /**
     * getActiveCameraId() is the ICamera call back method to get the active camera id.
     * @return it returns active camera id.
     * */
    int getActiveCameraId();
    /**
     * isFrontCameraActive() is the ICamera call back method to check whether the front camera is active or not.
     * @return it returns boolean value indicating front camera is active or not.
     * */
    boolean isFrontCameraActive();
    /**
     * getPreviewAspect() is the ICamera call back method to get the preview aspect ration dimension.
     * @return it returns preview dimension.
     * */
    Dimension getPreviewAspect();
}
