package com.matching.tech.bio.utilities;

import java.io.Serializable;

public class FaceDetectionObj implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private byte[] processedBytes=null;
	private int responseCode;
	private int errorCode;
	private int timeTaken;

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}

	private String errorMessage;
	private String responseMessage;
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public byte[] getProcessedBytes() {
		return processedBytes;
	}
	public void setProcessedBytes(byte[] processedBytes) {
		this.processedBytes = processedBytes;
	}
	@Override
	public String toString() {
		return "ResponseObj [previewImageBytes=" + ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
	
	
}
