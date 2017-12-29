package com.bala.gan.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bala.gan.model.KidModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by asisayag on 17/12/2017.
 */

public class KidsViewModel extends AndroidViewModel {

    MutableLiveData<List<KidModel>> kids;
    DatabaseReference ref;

    public KidsViewModel(@NonNull Application application) {
        super(application);
        ref = FirebaseDatabase.getInstance().getReference("kids");
        kids = new MutableLiveData<>();
    }

    public MutableLiveData<List<KidModel>> getKids() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                if (data.exists()) {
                    kids.postValue(toKids(data));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return kids;
    }

    private List<KidModel> toKids(DataSnapshot data) {
        String json = new Gson().toJson(data.getValue());
        List<KidModel> f = new Gson().fromJson(json, new TypeToken<List<KidModel>>(){}.getType());
        return f;
    }
}
