package com.arthitraders.employee.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthitraders.employee.R;
import com.arthitraders.employee.constant.Helper;
import com.arthitraders.employee.model.UserModel;
import com.arthitraders.employee.view.HomeScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TradersAdapter extends RecyclerView.Adapter<TradersAdapter.TradersViewHolder> {
    private Context context;
    private List<UserModel> traders;

    public TradersAdapter(Context context, List<UserModel> traders) {
        this.context = context;
        this.traders = traders;
    }

    @NonNull
    @Override
    public TradersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.traders, parent, false);
        return new TradersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradersViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.traders_name.setText(traders.get(position).getUserName());
            holder.icon.setText(traders.get(position).getUserName().substring(0,1).toUpperCase());
            holder.traders_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Helper.sharedpreferences =context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);

                    SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                    ed.putBoolean("Traders_First_Time",true);
                    ed.putString("dol_user_id",traders.get(position).getUserId());
                    ed.apply();

                    context.startActivity(new Intent(context, HomeScreen.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return traders.size();
    }

    public class TradersViewHolder extends RecyclerView.ViewHolder {
        TextView traders_name,icon;
        LinearLayout traders_lin;
        public TradersViewHolder(@NonNull View itemView) {
            super(itemView);
            traders_name=itemView.findViewById(R.id.traders_name);
            icon=itemView.findViewById(R.id.trader_img);
            traders_lin=itemView.findViewById(R.id.traders_lin);
        }
    }
}
