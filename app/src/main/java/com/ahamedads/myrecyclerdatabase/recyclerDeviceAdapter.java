package com.ahamedads.myrecyclerdatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class recyclerDeviceAdapter extends RecyclerView.Adapter<recyclerDeviceAdapter.MyHoder>{

    ArrayList<recyclerDevice> list;
    private OnItemClickListener mListener;
    Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class MyHoder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView image_delete;
        ImageView image_on_off;

        public MyHoder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            image_delete = (ImageView) itemView.findViewById(R.id.image_delete);
            image_on_off=itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            image_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
    public recyclerDeviceAdapter(ArrayList<recyclerDevice> exampleList){
        list = exampleList;
    }
    public recyclerDeviceAdapter(ArrayList<recyclerDevice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_device,parent,false);
        MyHoder myHoder = new MyHoder(view,mListener);

        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        recyclerDevice mylist = list.get(position);
        holder.textView.setText(mylist.getDeviceName());

        //holder.image_on_off.setImageResource(R.);
    }

    @Override
    public int getItemCount() {

        return list.size();

//        int arr = 0;
//        try{
//            if(list.size()==0){
//                arr = 0;
//            }
//            else{
//                arr=list.size();
//            }
//        }catch (Exception e){
//        }
//        return arr;
//    }
    }
}