package com.whtech.anuroopjodi.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whtech.anuroopjodi.Object.PackageObject;
import com.whtech.anuroopjodi.R;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    private ArrayList<PackageObject> packageObjectArrayList;
    private Context context;

    private int lastSelectedPosition = -1;

    public PackageAdapter(Context ctx, ArrayList<PackageObject> packageObjectArrayList) {
        context = ctx;
        this.packageObjectArrayList = packageObjectArrayList;

    }

    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_adapter_row_item, parent, false);

        PackageAdapter.ViewHolder viewHolder =
                new PackageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PackageAdapter.ViewHolder holder,
                                 int position) {
        PackageObject offersModel = packageObjectArrayList.get(position);
        holder.offerName.setText(offersModel.packageName);
        holder.tvBenifit.setText("( "+ offersModel.validity + " Day(s) )");

        holder.tvRetailerRate.setText("\u20B9 " + packageObjectArrayList.get(position).packageAmount);
        holder.tvTradePrice.setText("\u20B9 " + packageObjectArrayList.get(position).strike_thr_packageAmount);
        holder.tvTradePrice.setPaintFlags(holder.tvTradePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvEmail.setText(packageObjectArrayList.get(position).visible_email +" Email View");
        holder.tvChat.setText(packageObjectArrayList.get(position).visible_mobile+" Mobile View");

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        if (packageObjectArrayList.get(position).isSelected()) {
            holder.selectionState.setChecked(true);
        } else {
            holder.selectionState.setChecked(false);
        }

        if (packageObjectArrayList.get(position).chatFeature.equalsIgnoreCase("0")) {
            holder.rlChat.setVisibility(View.GONE);
        } else {
            holder.rlChat.setVisibility(View.VISIBLE);
        }

        if (packageObjectArrayList.get(position).suggesionWeb.equalsIgnoreCase("0")) {
            holder.rlEmail.setVisibility(View.GONE);
        } else {
            holder.rlEmail.setVisibility(View.VISIBLE);
        }

        if (packageObjectArrayList.get(position).horoscopes.equalsIgnoreCase("0")) {
            holder.rlHoroscope.setVisibility(View.GONE);
        } else {
            holder.rlHoroscope.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return packageObjectArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView offerName, tvBenifit,tvTradePrice, tvRetailerRate, tvEmail, tvChat;
        public RadioButton selectionState;
        public RelativeLayout rlChat,rlEmail,rlHoroscope,rlServices;
        public ViewHolder(View view) {
            super(view);
            tvBenifit = (TextView) view.findViewById(R.id.tvBenifit);
            offerName = (TextView) view.findViewById(R.id.offer_name);
            selectionState = (RadioButton) view.findViewById(R.id.offer_select);
            rlChat =  view.findViewById(R.id.rlChat);
            rlEmail =  view.findViewById(R.id.rlEmail);
            rlHoroscope =  view.findViewById(R.id.rlHoroscope);
            rlServices =  view.findViewById(R.id.rlServices);
            tvTradePrice =  view.findViewById(R.id.tvTradePrice);
            tvRetailerRate =  view.findViewById(R.id.tvRetailerRate);
            tvEmail =  view.findViewById(R.id.tvEmail);
            tvChat =  view.findViewById(R.id.tvChat);
        }
    }
}

