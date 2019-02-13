package com.matching.tech.bio.devices.fingerprintUtils;

public class MTUtilities {

    private static MTUtilities instance = null;

    public static MTUtilities getInstance() {
        if (null == instance) {
            synchronized (MTUtilities.class) {
                if (null == instance) {
                    instance = new MTUtilities();
                }
            }
        }
        return instance;
    }
    public byte[] getTemplateBytes(byte[] fingerBytes){

        return "test".getBytes();
    }
    public byte[] getWSQBytes(byte[] fingerBytes){

        return null;
    }
    public int matchTemplates(byte[] galleryTemplate,byte[] probeTemplate){

        return 0;
    }
    public int getMinutiaeCount(byte[] templateBytes){

        return 60;
    }
    public int getNFIQCount(byte[] templateBytes){

        return 0;
    }

}
