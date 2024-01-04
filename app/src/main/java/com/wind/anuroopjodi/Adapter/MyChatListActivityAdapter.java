package com.wind.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.InsideActivities.Vijendra.ChatDetailListActivity;
import com.wind.anuroopjodi.Object.Chat;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.Constants;
import com.wind.anuroopjodi.my_library.Shared_Preferences;

import java.util.ArrayList;

public class MyChatListActivityAdapter extends RecyclerView.Adapter<MyChatListActivityAdapter.SponsorsHolder> {

    private ArrayList<Chat> chatArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;

    public MyChatListActivityAdapter(ArrayList<Chat> chatArrayList) {
        this.chatArrayList = chatArrayList;
    }

    @Override
    public MyChatListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_view, parent, false);

        return new MyChatListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyChatListActivityAdapter.SponsorsHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Chat chat = chatArrayList.get(position);

        holder.tv_userName.setTypeface(holder.tv_userName.getTypeface(), Typeface.BOLD);

        if(Shared_Preferences.getPrefs(context, Constants.IsChat).equals("1")){
            holder.tv_info.setText("");
            holder.tv_info.setVisibility(View.GONE);
        }else {
            holder.tv_info.setVisibility(View.VISIBLE);
            holder.tv_info.setText("You have new message from this user for view this message please upgrade your package");
        }

        if(chatArrayList.get(position).getUserID().equals((chatArrayList.get(position).getReceiver_id()))) {
            holder.tv_userName.setText(chatArrayList.get(position).getReceiver_name());
        }else {
            holder.tv_userName.setText(chatArrayList.get(position).getSender_name());
        }

        if(chatArrayList.get(position).getUserID().equals((chatArrayList.get(position).getReceiver_id()))) {
            holder.tv_user_info.setText(chatArrayList.get(position).getReceiverCity()
                    +", "+chatArrayList.get(position).getReceiverState() );
        }else {
            holder.tv_user_info.setText(chatArrayList.get(position).getSenderCity()
                    +", "+chatArrayList.get(position).getSenderState() );
        }


        if(chatArrayList.get(position).getUserID().equals((chatArrayList.get(position).getReceiver_id()))) {
            Glide.with(context)
                    .load(SharedPref.getPrefs(context, IConstant.UserProfileImagePath) + chatArrayList.get(position).getReceiverProfile())
                    .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                    .into(holder.iv_userImage);
        }else {
            Glide.with(context)
                    .load(SharedPref.getPrefs(context, IConstant.UserProfileImagePath) + chatArrayList.get(position).getSenderProfile())
                    .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                    .into(holder.iv_userImage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatArrayList.get(position).getIs_block().equals("false")) {
                    if (Shared_Preferences.getPrefs(context, Constants.IsChat).equals("1")) {
                        Intent intent = new Intent(context, ChatDetailListActivity.class);
                        intent.putExtra(IConstant.ReceiverID, chatArrayList.get(position).getReceiver_id());
                        intent.putExtra(IConstant.SenderID, chatArrayList.get(position).getSender_id());
                        intent.putExtra(IConstant.SenderName, chatArrayList.get(position).getSender_name());
                        intent.putExtra(IConstant.ReceiverName, chatArrayList.get(position).getReceiver_name());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "You have new message from this user for view this message please upgrade your package", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "You can not chat with this person..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        

    }
    

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

   

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        TextView tv_userName, tv_user_info, tv_info;
        private ImageView iv_userImage;
       

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();

            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            tv_user_info = (TextView) itemView.findViewById(R.id.tv_user_info);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            iv_userImage = itemView.findViewById(R.id.iv_userImage);


        }
    }

}

