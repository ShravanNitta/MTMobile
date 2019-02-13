package com.matching.tech.bio.devices.colombo;

import android.graphics.Bitmap;

/**
 * CaptureData.java  - CaptureData class is used to hold the captured data along with status after capturing done and sends to Activity by using TaskListener.
 * @author Shravan Nitta
 * @version 1.0
 */

public class CaptureData {

    private String captureStatus;
    private Bitmap capturedBitmap;
    private Integer capturedFinger;
    private byte[] capturedBytes;
    private int width;
    private int height;
    private int nfiqScore;

    public int getNfiqScore() {
        return nfiqScore;
    }

    public void setNfiqScore(int nfiqScore) {
        this.nfiqScore = nfiqScore;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public byte[] getCapturedBytes() {
        return capturedBytes;
    }

    public void setCapturedBytes(byte[] capturedBytes) {
        this.capturedBytes = capturedBytes;
    }

    public String getCaptureStatus() {
        return captureStatus;
    }

    public void setCaptureStatus(String captureStatus) {
        this.captureStatus = captureStatus;
    }

    public Bitmap getCapturedBitmap() {
        return capturedBitmap;
    }

    public void setCapturedBitmap(Bitmap capturedBitmap) {
        this.capturedBitmap = capturedBitmap;
    }

    public Integer getCapturedFinger() {return capturedFinger;
    }
    public void setCapturedFinger(Integer capturedFinger) {
        this.capturedFinger = capturedFinger;
    }
}
