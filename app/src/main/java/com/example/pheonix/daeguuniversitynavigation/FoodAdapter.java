package com.example.pheonix.daeguuniversitynavigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//RecyclerViewにアイテムを注入するアダプターオブジェクト
class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //コンストラクタで「注入するレイアウトのウイジェットのID」を定義
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView Picture;
        TextView R_name;


        public MyViewHolder(View itemView) {
            super(itemView);
            Picture = itemView.findViewById(R.id.picture);
            R_name = itemView.findViewById(R.id.R_name);
        }
    }

    //MyRestaurantActivityで直接RecyclerViewに使う
    // Arrayをアダプターに伝える部分
    private ArrayList<FoodInfo> foodInfos;
    FoodAdapter(ArrayList<FoodInfo> foodInfos){
        this.foodInfos = foodInfos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view,parent,false);
        return new MyViewHolder(v);
    }

    //アダプターが伝達された「DrawableId」、「R_name」で作る
    //ビューの内容を決める
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.Picture.setImageResource(foodInfos.get(position).drawableId);
        myViewHolder.R_name.setText(foodInfos.get(position).R_name);
    }

    @Override
    public int getItemCount() {
        return foodInfos.size();
    }
}
