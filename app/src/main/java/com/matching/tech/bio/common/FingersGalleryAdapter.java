package com.matching.tech.bio.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matching.tech.bio.R;
import com.matching.tech.bio.devices.fingerprintUtils.DMFingerData;
import com.matching.tech.bio.utilities.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class FingersGalleryAdapter extends BaseAdapter {

    private Context context;
    private String language;
    private Map<Integer,LinkedHashMap<Integer,DMFingerData>> images ;
    private Typeface typeface;

    public FingersGalleryAdapter(Context c, Map<Integer,LinkedHashMap<Integer,DMFingerData>> images, String language) {
        context = c;
        this.images = images;
        this.language = language;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Cairo/Cairo-SemiBold.ttf");
    }

    /**
     * getCount() method is used to get the number of images
     */
    public int getCount() {
        return images.size();
    }
    /**
     * getCount() method is used to get the ID of an item
     */
    public Object getItem(int position) {
        return position;
    }

    /**
     * getItemId() method is used to get the ID of an item
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * getItemId() method is used to get the ImageView view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LinkedHashMap<Integer,DMFingerData> map =images.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gallery_view, null);
        ImageView capturedImage = view.findViewById(R.id.capturedImage);
        TextView finger_text = view.findViewById(R.id.finger_text);
        TextView finger_minutiae = view.findViewById(R.id.finger_minutiae);
        finger_text.setTypeface(typeface);
        finger_minutiae.setTypeface(typeface);


        for(Integer key : map.keySet()){
            Bitmap bmpImage = images.get(position).get(key).getCapturedBitmap();
            BitmapDrawable ob = new BitmapDrawable(context.getResources(), bmpImage);
            capturedImage.setBackgroundDrawable(ob);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getNfiqImage(images.get(position).get(key).getNfiqQuality()));
            capturedImage.setImageBitmap(bitmap); // set image in ImageView

            int fPosition = images.get(position).get(key).getPosition();
            int  minutiaeCount = images.get(position).get(key).getMinutiaeCount();

            finger_text.setText(getFingerMsg(fPosition));
            finger_minutiae.setText(context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.minutiae:R.string.minutiae_en)+" - "+minutiaeCount);
        }
        return view;
    }
    /**
     * getNfiqImage(int nfiq) method is used to get the drawable resource id of the image based on the nfiq quality.
     * @param nfiq is the NFIQ of the captured fingerprint.
     * @return it returns drawable resource id of the image.*/
    private int getNfiqImage(int nfiq){
        int res = R.drawable.nfiq1;
        switch(nfiq){
            case 1:
                res = R.drawable.nfiq1;
                break;
            case 2:
                res = R.drawable.nfiq2;
                break;
            case 3:
                res = R.drawable.nfiq3;
                break;
            case 4:
                res = R.drawable.nfiq4;
                break;
            case 5:
                res = R.drawable.nfiq5;
                break;

        }
        return res;
    }
    /**
     * getFingerMsg(int value) method is used to get the finger text based on the finger position.
     * @param value is the finger position.
     * @return it returns finger text based on the language*/
    public String getFingerMsg(int value) {
        String fingerMsg=null;
        if(value == 1){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_thumb:R.string.right_thumb_en);
        }
        if(value == 2){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_index:R.string.right_index_en);
        }
        if(value == 3){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_middle:R.string.right_middle_en);
        }
        if(value == 4){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_ring:R.string.right_ring_en);
        }
        if(value == 5){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_little:R.string.right_little_en);
        }
        if(value == 6){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_thumb:R.string.left_thumb_en);
        }
        if(value == 7){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_index:R.string.left_index_en);
        }
        if(value == 8){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_middle:R.string.left_middle_en);
        }
        if(value == 9){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_ring:R.string.left_ring_en);
        }
        if(value == 10){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_little:R.string.left_little_en);
        }
        if(value == 23){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.right_index_middle:R.string.right_index_middle_en);
        }
        if(value == 78){
            fingerMsg = context.getResources().getString(Constants.AR.equalsIgnoreCase(language)?R.string.left_index_middle:R.string.left_index_middle_en);
        }
        return fingerMsg ;
    }
}