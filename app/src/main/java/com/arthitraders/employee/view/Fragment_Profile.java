package com.arthitraders.employee.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arthitraders.employee.R;
import com.arthitraders.employee.constant.Helper;

import java.util.Objects;


public class Fragment_Profile extends Fragment {
    ImageView back;
    TextView title,logout,name;
    LinearLayout payment,about;
    String username;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        title=view.findViewById(R.id.title);
        back=view.findViewById(R.id.back);
        title.setText("Profile");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeScreen.class));
            }
        });

        name=view.findViewById(R.id.name);


        Helper.sharedpreferences = requireContext().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        username=Helper.sharedpreferences.getString("dol_user_name","");

        name.setText(username);

        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                ed.putBoolean("FIRSTTIME_LOGIN", false);
                ed.putBoolean("Traders_First_Time",false);
                ed.clear();
                ed.apply();
                startActivity(new Intent(getContext(),LoginScreen.class));
            }
        });




        payment=view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getContext(),PaymentMethodScreen.class));
            }
        });
        about=view.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AboutScreen.class));

            }
        });

        return view;
    }
}
