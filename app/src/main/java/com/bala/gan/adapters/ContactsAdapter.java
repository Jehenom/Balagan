package com.bala.gan.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bala.gan.R;
import com.bala.gan.model.KidModel;
import com.bala.gan.model.KindergartenModel;
import com.bala.gan.model.RelativeModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by asisayag on 11/11/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private KindergartenModel kidsList;

    private final static String FATHER = "father";
    private final static String MOTHER = "mother";

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public ContactsAdapter(Context context){
        mContext = context;
        kidsList = new KindergartenModel();
        setKids(new ArrayList<>());
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final KidModel currentKid = kidsList.kids.get(position);
        updateTextView(holder.v.findViewById(R.id.tv_kid_contact_name), currentKid.kidName);
        updateImage(holder.v.findViewById(R.id.iv_kid_contact_img), currentKid.kidImageURL);


        // Set mother info
        final RelativeModel mother = getRelative(currentKid.relatives, MOTHER);
        updateTextView(holder.v.findViewById(R.id.tv_kid_contact_mother_name), mother.relativeName);
        updateImageButtonListener(holder.v.findViewById(R.id.ib_kid_contact_mother_call), mother.relativePhone);


        // Set father info
        final RelativeModel father = getRelative(currentKid.relatives, FATHER);
        updateTextView(holder.v.findViewById(R.id.tv_kid_contact_father_name), father.relativeName);
        updateImageButtonListener(holder.v.findViewById(R.id.ib_kid_contact_father_call), father.relativePhone);

    }

    private void updateImage(final ImageView view, final String imageURL) {
        if(imageURL!= null & !imageURL.equals("")){
            StorageReference stRef = storage.getReferenceFromUrl("gs://gangan-4d912.appspot.com");
            stRef.child("images").child(imageURL).getBytes(1024*1024).addOnSuccessListener(bytes -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                bmp = getRoundedCornerBitmap(bmp, 200);
                view.setImageBitmap(bmp);
            }).addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure:"+e.getMessage());
            });
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void updateImageButtonListener(ImageButton view, final String relativePhone) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(relativePhone);
            }
        });
    }

    private void updateTextView(TextView view, String relativeName) {
        view.setText(relativeName);
    }

    private RelativeModel getRelative(List<RelativeModel> relatives, String father) {
        for (RelativeModel currRelative:relatives) {
            if (currRelative.relativeRelation.equals(father)) return currRelative;
        }
        return null;
    }

    private void call(String motherPhoneNumber) {
        String number = "tel:" + motherPhoneNumber;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));

        if (mContext.checkCallingOrSelfPermission(android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED) {
            mContext.startActivity(callIntent);
        }
    }

    @Override
    public int getItemCount() {
        return kidsList.kids.size();
    }

    public void setKids(List<KidModel> kids) {
        kidsList.kids = kids;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = (LinearLayout) itemView;
        }
    }
}
