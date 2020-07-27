package com.example.tourmate_final;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.R;
import com.example.tourmate_final.viewmodel.Loginviemodel;



/**
 * A simple {@link Fragment} subclass.
 */
public class Login_fragments extends Fragment {
    private EditText emalEt,passET;
    private Button loginBTn;
    private TextView statustTV;
    private Loginviemodel loginviemodel;
    private ImageView REgBTn;



    public Login_fragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginviemodel= ViewModelProviders.of(this).get(Loginviemodel.class);
        return inflater.inflate(R.layout.fragment_login_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emalEt=view.findViewById(R.id.usernameinput);
        passET=view.findViewById(R.id.passwrodinput);
        REgBTn=view.findViewById(R.id.registerbutton);
        loginBTn=view.findViewById(R.id.loginbtn);
        statustTV=view.findViewById(R.id.statusTV);

        loginBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emalEt.getText().toString();
                String password=passET.getText().toString();
                loginviemodel.login(email,password);

            }
        });
        REgBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Navigation.findNavController(view).navigate(R.id.action_login_fragments_to_registration_fragment);
            }
        });

        loginviemodel.statelivedata.observe(this, new Observer<Loginviemodel.Authenticationstate>() {
            @Override
            public void onChanged(Loginviemodel.Authenticationstate authenticationstate) {
                switch (authenticationstate){
                    case AUTHENTICATIONSTATED:
                        Navigation.findNavController(view).navigate(R.id.action_login_fragments_to_eventlistfragment);
                        break;
                    case UNAUTHENTICATIONSTATED:
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        loginviemodel.errmsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                statustTV.setText(s);
            }
        });
    }



}
