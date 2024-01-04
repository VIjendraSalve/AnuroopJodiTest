package com.wind.anuroopjodi.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Helper.DateTimeFormat;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Object.ChatDetails;
import com.wind.anuroopjodi.R;

//import com.wht.anuroopjodi.R;


import java.util.ArrayList;

public class ChatDetailsListActivityAdapter extends RecyclerView.Adapter<ChatDetailsListActivityAdapter.SponsorsHolder> {

    private ArrayList<ChatDetails> ticketArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tv_admin_response, tv_cust_response, tv_date, tv_date_another;
        private LinearLayout ll_admin_response,ll_cust_response;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();


            ll_cust_response = (LinearLayout) view.findViewById(R.id.ll_cust_response);
            ll_admin_response = (LinearLayout) view.findViewById(R.id.ll_admin_response);
            tv_admin_response = (TextView) view.findViewById(R.id.tv_admin_response);
            tv_cust_response = (TextView) view.findViewById(R.id.tv_cust_response);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_date_another = (TextView) view.findViewById(R.id.tv_date_another);




        }
    }


    public ChatDetailsListActivityAdapter(ArrayList<ChatDetails> ticketArrayList) {
        this.ticketArrayList = ticketArrayList;
    }

    @Override
    public ChatDetailsListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_response_list_view, parent, false);

        return new ChatDetailsListActivityAdapter.SponsorsHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ChatDetailsListActivityAdapter.SponsorsHolder holder, final int position) {
        final ChatDetails chatDetails = ticketArrayList.get(position);

        if(chatDetails.getReceiver().equals(SharedPref.getPrefs(context, IConstant.USER_ID))){
            holder.ll_admin_response.setVisibility(View.VISIBLE);
            holder.ll_cust_response.setVisibility(View.GONE);
            holder.tv_admin_response.setVisibility(View.VISIBLE);
            holder.tv_cust_response.setVisibility(View.GONE);
            holder.tv_admin_response.setText(chatDetails.getMessage());
            holder.tv_date.setText(DateTimeFormat.getDate2(chatDetails.getMessageTime()));
        }else {
            holder.ll_admin_response.setVisibility(View.GONE);
            holder.ll_cust_response.setVisibility(View.VISIBLE);
            holder.tv_admin_response.setVisibility(View.GONE);
            holder.tv_cust_response.setVisibility(View.VISIBLE);
            holder.tv_cust_response.setText(chatDetails.getMessage());
            holder.tv_date_another.setText(DateTimeFormat.getDate2(chatDetails.getMessageTime()));
        }

        

    }

    @Override
    public int getItemCount() {
        return ticketArrayList.size();
    }

    
}

