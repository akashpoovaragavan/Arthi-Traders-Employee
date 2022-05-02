package com.arthitraders.employee.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import com.arthitraders.employee.R;
import com.arthitraders.employee.constant.Helper;
import com.arthitraders.employee.constant.VolleySingleton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
AppCompatButton login;
EditText username,password;
SharedPreferences sharedpreferences;
boolean check_traders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getpermission();
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        login.setOnClickListener(v -> {
            String log_user=username.getText().toString();

            if (log_user.isEmpty()){
                username.setError("Username required");
                username.requestFocus();
            }else {
                logincheck(log_user);
            }
        });
    }

//// Permissions
    private void getpermission() {
        Dexter.withContext(LoginScreen.this).withPermissions(Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    /// End




    public void logincheck(String usernames) {
        try {
            final String username = usernames;
            final String passwords = password.getText().toString().trim();

            Helper.loading(LoginScreen.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.login_url,
                    response -> {
                        Helper.mProgressBarHandler.hide();
                        Log.e("logincheck", response);
                        try {
                            if(response.equals("INVALID")) {
                                Toast.makeText(LoginScreen.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                String dol_user_id      = obj.getString("id");
                                String dol_user_name        = obj.getString("username");



                                Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                                ed.putString("dol_user_name", dol_user_name);
                                ed.putString("dol_emp_id", dol_user_id);

                                ed.putBoolean("FIRSTTIME_LOGIN", true);
                                ed.apply();
                                check_traders=Helper.sharedpreferences.getBoolean("Traders_First_Time",false);
                                Intent intent;
                                if(!check_traders){
                                    intent = new Intent(LoginScreen.this, TradersScreen.class);
                                }else{
                                    intent = new Intent(LoginScreen.this, HomeScreen.class);
                                }
                                startActivity(intent);
                                overridePendingTransition(R.anim.right_enter,R.anim.left_out);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Helper.mProgressBarHandler.hide();
                        }

                    },
                    error -> {
                        try {
                            Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Helper.mProgressBarHandler.hide();
                            Log.e("err", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", passwords);
                    return params;
                }
            };
            VolleySingleton.getInstance(LoginScreen.this).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}