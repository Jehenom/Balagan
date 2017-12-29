package com.bala.gan.viewmodels;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by asisayag on 02/12/2017.
 */

public class ContactsViewModel extends KidsViewModel{


    public ContactsViewModel(@NonNull Application app) {
        super(app);
    }



//    private String getItemsJson() {
//        String str = "";
//        try
//        {
//            AssetManager assetManager = this.getApplication().getAssets();
//            InputStream in = assetManager.open("data/data.json");
//            InputStreamReader isr = new InputStreamReader(in);
//            char [] inputBuffer = new char[100];
//
//            int charRead;
//            while((charRead = isr.read(inputBuffer))>0)
//            {
//                String readString = String.copyValueOf(inputBuffer,0,charRead);
//                str += readString;
//            }
//        }
//        catch(IOException ioe)
//        {
//            ioe.printStackTrace();
//        }
//
//        return str;
//    }

}
