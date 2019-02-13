package com.matching.tech.bio.devices.fingerprintUtils;

import android.graphics.Bitmap;
import android.util.Base64;

import com.lakota.biometrics.wsqparse.WsqEncoder;
import com.matching.tech.bio.utilities.Constants;
import com.matching.tech.bio.utilities.LogUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SegmentationUtil {

	private static SegmentationUtil instance;
	public static final String TAG = SegmentationUtil.class.getSimpleName();
	public static SegmentationUtil getInstance(){
		if(null == instance){
			synchronized(SegmentationUtil.class){
				if(null == instance){
					instance = new SegmentationUtil();
				}
			}
		}
		return instance;
	}

	public DMSegmentationResult segmentFingers(byte[] buffer, Bitmap capturedBitmap, int fingerPosition,int width, int height,int nfiqQuality,boolean templateRequired){
		DMSegmentationResult dmSegmentationResult = null;
		try{
			dmSegmentationResult = new DMSegmentationResult();
			ArrayList<DMFingerData> dmSegmentedFingers = new ArrayList<> ();
			DMFingerData dmFingerData = new DMFingerData();
			dmFingerData.setPosition(fingerPosition);
			dmFingerData.setNfiqQuality(nfiqQuality);
			dmFingerData.setWsqImage(convertToWSQ(buffer,width,height));
			dmFingerData.setFingerData(getBitmapBytes(capturedBitmap));
			dmFingerData.setCapturedBitmap(capturedBitmap);
			if(templateRequired){
				try {
					byte[] ansiTemplate = null;
					if(null != buffer){
						long te1 = System.currentTimeMillis();
						ansiTemplate = MTUtilities.getInstance().getTemplateBytes(buffer);
						long te2 = System.currentTimeMillis();
						LogUtils.debug(TAG,"Time taken to convert to template :: "+(te2-te1));
					}

					if(null!=ansiTemplate) {
						dmFingerData.setMinutiaeCount(MTUtilities.getInstance().getMinutiaeCount(ansiTemplate));
						dmFingerData.setTemplate(Base64.encodeToString(ansiTemplate,Base64.NO_WRAP));
					}
				} catch (Exception e) {
					LogUtils.debug(TAG,"Segmentation failed :: "+e.getMessage());
					dmSegmentationResult.setReturnMessage(Constants.MINUTIAE_EXTRACTION_FAILED_DESC);
					return dmSegmentationResult;
				}
			}
			dmSegmentedFingers.add(dmFingerData);
			dmSegmentationResult.setDmSegmentedFingers(dmSegmentedFingers);
		}catch (Exception e){
			LogUtils.debug(TAG,"Exception Due to :segmentFingers(): "+e.getMessage());
		}
		return dmSegmentationResult;
	}
	public String convertToWSQ(byte[] buffer,int width,int height){
		String wsqBytes = null;
		try {
			byte[] imageBuffer = new byte[width * height];
			for(int x=0,y=height-1;x<imageBuffer.length && y > 0 ;y--){
				for(int i = y * width,c=0; c < width; c++,i++,x++){
					imageBuffer[i] = buffer[x];
				}
			}
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			com.lakota.biometrics.wsqparse.Bitmap bp = new com.lakota.biometrics.wsqparse.Bitmap(imageBuffer,width,height,500,8,0);
			WsqEncoder.encode(boas,bp,0.75f,"empty");
			byte[] convertedBytes = boas.toByteArray();
			wsqBytes = Base64.encodeToString(convertedBytes,Base64.NO_WRAP);

			/*FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/image.wsq");
			fos.write(convertedBytes);
			fos.close();*/
		} catch (Exception e) {
			LogUtils.debug(TAG,"Exception in convertToWSQBytes ::  "+e.getMessage());
		}
		return wsqBytes;
	}
	public byte[] getBitmapBytes(Bitmap bitmap){
		byte[] bitMapBytes = null;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG,100,bout);
			bitMapBytes = bout.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  bitMapBytes;
	}
}
