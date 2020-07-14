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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.pojos.UserInformationPojo;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.Loginviemodel;


/**
 * A simple {@link Fragment} subclass.
 */
public class  Registration_fragment extends Fragment {
    private EditText nameET,EmailEt,passwordET,confirmET;
    private Button registerBTN;
    private TextView showError;
    private Loginviemodel loginviemodel;


    public Registration_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginviemodel= ViewModelProviders.of(this).get(Loginviemodel.class);
        return inflater.inflate(R.layout.fragment_registration_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameET=view.findViewById(R.id.userNameET);
        EmailEt=view.findViewById(R.id.userEmailEt);
        passwordET=view.findViewById(R.id.userPasswordET);
        confirmET=view.findViewById(R.id.userRetypePassword);
        registerBTN=view.findViewById(R.id.registerbutton2);
        showError=view.findViewById(R.id.showtextTV);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameET.getText().toString();
                String email=EmailEt.getText().toString();
                String password=passwordET.getText().toString();
                String conirmpass=confirmET.getText().toString();
            if (name.isEmpty() && email.isEmpty() &&password.isEmpty()&&conirmpass.isEmpty()){
                Toast.makeText(getActivity(), "provide info", Toast.LENGTH_SHORT).show();
                nameET.setError("insert name");
                EmailEt.setError("provide valid email");
            }else {
                if (password.equals(conirmpass)) {

                    UserInformationPojo userInformationPojo = new UserInformationPojo(null, name, email, password);
                    loginviemodel.register(userInformationPojo);
                }else {
                    Toast.makeText(getActivity(), "not match", Toast.LENGTH_SHORT).show();
                }
            }
            }

        });
      loginviemodel.statelivedata.observe(this, new Observer<Loginviemodel.Authenticationstate>() {
          @Override
          public void onChanged(Loginviemodel.Authenticationstate authenticationstate) {
              switch (authenticationstate){
                  case AUTHENTICATIONSTATED:
                      Navigation.findNavController(view).navigate(R.id.action_registration_fragment_to_eventlistfragment);
                      break;
                  case UNAUTHENTICATIONSTATED:
                      break;
              }
          }
      });

        loginviemodel.errmsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showError.setText(s);
            }
        });

    }
}
