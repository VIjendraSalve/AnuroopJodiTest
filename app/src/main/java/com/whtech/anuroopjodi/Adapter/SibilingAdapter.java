package com.whtech.anuroopjodi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.whtech.anuroopjodi.Object.CommonYesNoObject;
import com.whtech.anuroopjodi.R;

import java.util.ArrayList;

public class SibilingAdapter extends RecyclerView.Adapter<SibilingAdapter.ViewHolder> {
    private ArrayList<CommonYesNoObject> mPersonList;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context context;
    int selectedPosition;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClickForAdminTemplate(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView tvMonth;
        RelativeLayout rlBackground;

        public ViewHolder(View v) {
            super(v);

            tvMonth = (TextView) itemView.findViewById(R.id.tvLeaveType);
            rlBackground = itemView.findViewById(R.id.rlBackground);
        }

    }

    public void add(int position, CommonYesNoObject item) {
        mPersonList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mPersonList.indexOf(item);
        mPersonList.remove(position);
        notifyItemRemoved(position);
    }

    public SibilingAdapter(Context context, ArrayList<CommonYesNoObject> personList,
                           OnItemClickListener listener) {
        this.context = context;
        this.mPersonList = personList;
        this.listener = listener;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gender_adapter_row_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        DisplayMetrics metricscard = context.getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        //holder.rlBackground.getLayoutParams().height = cardheight / 12;
        holder.rlBackground.getLayoutParams().width = (int) (cardwidth / 4);

        holder.tvMonth.setText(mPersonList.get(position).getName());
        Log.d("LeavesAdapter", "onBindViewHolder: " + mPersonList.get(position).getName());

        if (mPersonList.get(position).isSelected()) {
            /*    holder.rlBackground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryFullDark));*/
            holder.tvMonth.setTextColor(context.getResources().getColor(R.color.white));

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.rlBackground.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_round_color_primary));
            } else {
                holder.rlBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.border_round_color_primary));
            }
            listener.onItemClickForAdminTemplate(1);
        } else {
            //holder.rlBackground.setBackgroundColor(context.getResources().getColor(R.color.gray_lt));
            holder.tvMonth.setTextColor(context.getResources().getColor(R.color.black));
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.rlBackground.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_round_gray));
            } else {
                holder.rlBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.border_round_gray));
            }
            listener.onItemClickForAdminTemplate(0);
        }


    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }
}



