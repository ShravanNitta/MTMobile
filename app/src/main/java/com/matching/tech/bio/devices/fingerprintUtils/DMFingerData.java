package com.matching.tech.bio.devices.fingerprintUtils;

import android.graphics.Bitmap;

/**
 * DMFingerData.java  - DMFingerData is java object to hold the Segmented finger data.
 * @author Shravan Nitta
 * @version 1.0
 */
public class DMFingerData {
	
	private static final long serialVersionUID = 1L;
	private int nfiqQuality;
	private int minutiaeCount;
	private String finger;
	private int position;
	private boolean duplicate;
	private String template;
	private String wsqImage;
	private byte[] fingerData;
	private Bitmap capturedBitmap;

	public Bitmap getCapturedBitmap() {
		return capturedBitmap;
	}

	public void setCapturedBitmap(Bitmap capturedBitmap) {
		this.capturedBitmap = capturedBitmap;
	}

	public int getNfiqQuality() {
		return nfiqQuality;
	}
	public void setNfiqQuality(int nfiqQuality) {
		this.nfiqQuality = nfiqQuality;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public byte[] getFingerData() {
		return fingerData;
	}

	public void setFingerData(byte[] fingerData) {
		this.fingerData = fingerData;
	}

	public String getWsqImage() {
		return wsqImage;
	}

	public void setWsqImage(String wsqImage) {
		this.wsqImage = wsqImage;
	}

	public int getMinutiaeCount() {
		return minutiaeCount;
	}
	public void setMinutiaeCount(int minutiaeCount) {
		this.minutiaeCount = minutiaeCount;
	}
	public String getFinger() {
		return finger;
	}
	public void setFinger(String finger) {
		this.finger = finger;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isDuplicate() {
		return duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
}
