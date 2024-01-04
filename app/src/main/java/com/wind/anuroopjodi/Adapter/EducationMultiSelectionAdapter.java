package com.wind.anuroopjodi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wind.anuroopjodi.Object.EducationObject;
import com.wind.anuroopjodi.R;

import java.util.ArrayList;

public class EducationMultiSelectionAdapter extends RecyclerView.Adapter<EducationMultiSelectionAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<EducationObject> educationObjectArrayList;
    private Context mContext;



    public EducationMultiSelectionAdapter(Context mContext, ArrayList<EducationObject> educationObjectArrayList) {

        inflater = LayoutInflater.from(mContext);
        this.educationObjectArrayList = educationObjectArrayList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.education_multi_selection_adapter_row_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.checkBox.setText("Checkbox " + position);
        holder.checkBox.setChecked(educationObjectArrayList.get(position).isSelected());
        holder.checkBox.setText(educationObjectArrayList.get(position).name );
        holder.variant_id.setText(educationObjectArrayList.get(position).id);

        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();
                //Toast.makeText(mContext, educationObjectArrayList.get(pos).getPaymentMode() + " clicked!", Toast.LENGTH_SHORT).show();

                if (educationObjectArrayList.get(pos).isSelected()) {
                    educationObjectArrayList.get(pos).setSelected(false);
                } else {
                    educationObjectArrayList.get(pos).setSelected(true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return educationObjectArrayList.size();
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



