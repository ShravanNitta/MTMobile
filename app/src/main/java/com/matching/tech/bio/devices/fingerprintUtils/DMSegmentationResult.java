package com.matching.tech.bio.devices.fingerprintUtils;

import java.util.ArrayList;

/**
 * DMSegmentationResult.java  - DMSegmentationResult is java object to hold the SegmentationResult.
 * @author Shravan Nitta
 * @version 1.0
 */
public class DMSegmentationResult {
	
	public ArrayList<DMFingerData> dmSegmentedFingers;
	public int returnCode;    		/////operation return code
	public String returnMessage;		/////operation return Message
	public byte[] finalWSQImage;	////////final image as wsq
	boolean isWrongSlap;
	private String timeTaken;

	public String getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}
	public ArrayList<DMFingerData> getDmSegmentedFingers() {
		return dmSegmentedFingers;
	}
	public void setDmSegmentedFingers(ArrayList<DMFingerData> dmSegmentedFingers) {
		this.dmSegmentedFingers = dmSegmentedFingers;
	}
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public byte[] getFinalWSQImage() {
		return finalWSQImage;
	}
	public void setFinalWSQImage(byte[] finalWSQImage) {
		this.finalWSQImage = finalWSQImage;
	}
	public boolean isWrongSlap() {
		return isWrongSlap;
	}
	public void setWrongSlap(boolean isWrongSlap) {
		this.isWrongSlap = isWrongSlap;
	}
	
}
