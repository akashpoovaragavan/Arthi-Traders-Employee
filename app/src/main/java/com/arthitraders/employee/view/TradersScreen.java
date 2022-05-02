package com.arthitraders.employee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthitraders.employee.R;
import com.arthitraders.employee.constant.Helper;
import com.arthitraders.employee.constant.VolleySingleton;
import com.arthitraders.employee.controller.TradersAdapter;
import com.arthitraders.employee.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradersScreen extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView traders_recycler;
    TradersAdapter tradersAdapter;
    SwipeRefreshLayout swipelayout;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traders_screen);
        swipelayout=findViewById(R.id.swipelayout);
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        traders_recycler=findViewById(R.id.traders_recycler);
        traders_recycler.setHasFixedSize(true);
        traders_recycler.setLayoutManager(new LinearLayoutManager(TradersScreen.this, LinearLayoutManager.VERTICAL, false));
        userList();
    }

    @Override
    public void onRefresh() {
        userList();
    }

    public void userList() {
        try {

            Helper.loading(TradersScreen.this);
            swipelayout.setRefreshing(false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.userlist_url,
                    response -> {
                        Helper.mProgressBarHandler.hide();
                        Log.e("UserlistActivity", response);
                        try {
                            JSONArray jarr = new JSONArray(response);
                            if(jarr.length()!=0) {
                                List<UserModel> items = new ArrayList<>();
                                for (int v = 0; v < jarr.length(); v++) {
                                    JSONObject obj = jarr.getJSONObject(v);
                                    String userid = obj.getString("dol_user_id");
                                    String username = obj.getString("dol_user_shop");
                                    String dol_user_check = obj.getString("dol_user_check");
                                    if(dol_user_check.trim().equals("approved")) {
                                        UserModel usermodel = new UserModel(username, userid, dol_user_check);
                                        items.add(usermodel);
                                    }
                                }
                                tradersAdapter=new TradersAdapter(TradersScreen.this,items);
                                traders_recycler.setAdapter(tradersAdapter);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "There is no users", Toast.LENGTH_SHORT).show();
                                Helper.mProgressBarHandler.hide();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Helper.mProgressBarHandler.hide();
                        }

                    },
                    error -> {
                        try {
                            Toast.makeText(TradersScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Helper.mProgressBarHandler.hide();
                            Log.e("err", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    return params;
                }
            };
            VolleySingleton.getInstance(TradersScreen.this).addToRequestQueue(stringRequest);
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