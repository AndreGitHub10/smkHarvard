package com.example.smkharvard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    Button login_btn;
    EditText email, password;
    float v = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.form_login, container, false);

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        login_btn = root.findViewById(R.id.login_btn);

        email.setTranslationX(800);
        password.setTranslationX(800);
        login_btn.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        login_btn.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
