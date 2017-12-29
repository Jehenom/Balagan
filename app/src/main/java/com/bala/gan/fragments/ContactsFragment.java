package com.bala.gan.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bala.gan.R;
import com.bala.gan.adapters.ContactsAdapter;
import com.bala.gan.viewmodels.ContactsViewModel;

import static android.content.ContentValues.TAG;

/**
 * Created by asisayag on 11/11/2017.
 */

public class ContactsFragment extends android.support.v4.app.Fragment {

    ContactsViewModel vm ;
    ContactsAdapter adapter;
    public static ContactsFragment newInstance()
    {
        return new ContactsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ContactsViewModel(getActivity().getApplication());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView rv = rootView.findViewById(R.id.rv_contacts_list);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        adapter = new ContactsAdapter(container.getContext());
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of ContactsFragment");
        super.onResume();
        vm.getKids().observe(this,(kids)-> {
            adapter.setKids(kids);
            Log.d(TAG, "onResume: observed!");
        });
    }
}
