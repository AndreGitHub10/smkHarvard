package com.example.smkharvard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class RegisterTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.form_register, container, false);

        return root;
    }

}
