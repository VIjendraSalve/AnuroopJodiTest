package com.whtech.anuroopjodi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whtech.anuroopjodi.Object.SubcasteObject;
import com.whtech.anuroopjodi.R;

import java.util.ArrayList;

public class SubcastePreferenceMultiSelectionAdapter extends RecyclerView.Adapter<SubcastePreferenceMultiSelectionAdapter.MyViewHolder> {

    public static ArrayList<SubcasteObject> subcasteObjectArrayList;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private LayoutInflater inflater;
    private Context mContext;


    public SubcastePreferenceMultiSelectionAdapter(Context mContext, ArrayList<SubcasteObject> subcasteObjectArrayList) {

        inflater = LayoutInflater.from(mContext);
        this.subcasteObjectArrayList = subcasteObjectArrayList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.subcaste_multi_selection_adapter_row_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.checkBox.setText("Checkbox " + position);
        holder.checkBox.setChecked(subcasteObjectArrayList.get(position).isSelected());
        holder.checkBox.setText(subcasteObjectArrayList.get(position).casteName);
        holder.variant_id.setText(subcasteObjectArrayList.get(position).id);

        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);

        //for default check in first item
        if (position == 0 && subcasteObjectArrayList.get(0).isSelected() && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (cb.isChecked()) {
                    if (lastChecked != null) {
                        // lastChecked.setChecked(false);
                        // educationObjectArrayList.get(lastCheckedPos).setSelected(false);
                    }

                    // lastChecked = cb;
                    // lastCheckedPos = clickedPos;
                } //else
                //   lastChecked = null;

                subcasteObjectArrayList.get(clickedPos).setSelected(cb.isChecked());
            }
        });

        /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();
                //Toast.makeText(mContext, educationObjectArrayList.get(pos).getPaymentMode() + " clicked!", Toast.LENGTH_SHORT).show();

                if (educationObjectArrayList.get(position).isSelected()) {
                    educationObjectArrayList.get(position).setSelected(false);
                } else {
                    educationObjectArrayList.get(position).setSelected(true);
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return subcasteObjectArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected CheckBox checkBox;
        protected TextView vendor_variant_id;
        protected TextView variant_id;
        protected TextView variant_value;
        protected TextView product_id;
        protected TextView variant_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb);
            vendor_variant_id = (TextView) itemView.findViewById(R.id.vendor_variant_id);
            variant_id = (TextView) itemView.findViewById(R.id.variant_id);
            variant_value = (TextView) itemView.findViewById(R.id.variant_value);
            product_id = (TextView) itemView.findViewById(R.id.product_id);
            variant_name = (TextView) itemView.findViewById(R.id.variant_name);

        }

    }
}



