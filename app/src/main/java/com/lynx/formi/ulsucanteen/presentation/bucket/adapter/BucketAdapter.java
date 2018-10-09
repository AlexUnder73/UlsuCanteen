package com.lynx.formi.ulsucanteen.presentation.bucket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.LootViewHolder> {

    public interface OnCountChangeListener {
        void onIncrementClick(String id, int price);

        void onDecrementClick(String id, int price);

    }

    private OnCountChangeListener onCountChangeListener = null;

    public void setOnCountChangeListener(OnCountChangeListener onCountChangeListener) {
        this.onCountChangeListener = onCountChangeListener;
    }

    private Context ctx;
    private List<Food> foodList;

    public BucketAdapter(Context ctx) {
        this.ctx = ctx;
        foodList = new ArrayList<>();
    }

    public void setFoodList(List<Food> foodList) {
        if (foodList == null) return;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public LootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loot_eat_item, parent, false);
        return new LootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LootViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.txtCount.setText(food.count);

        if (Integer.valueOf(food.count) == 1) {
            holder.btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            holder.btnDecrement.setClickable(false);
        }else if (Integer.valueOf(food.count) == 9) {
            holder.btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            holder.btnIncrement.setClickable(false);
        } else{
            holder.btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_enable));
            holder.btnIncrement.setClickable(true);

            holder.btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_enable));
            holder.btnDecrement.setClickable(true);
        }

        holder.txtTitle.setText(food.title);
        holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        Picasso.with(ctx).load(food.imgUrl).into(holder.imgPhoto);

        holder.btnIncrement.setOnClickListener((v) ->

        {
            if (onCountChangeListener != null)
                onCountChangeListener.onIncrementClick(food.id, Integer.valueOf(food.price));

            if(onIncrementClick(Integer.valueOf(food.count), holder.btnIncrement, holder.btnDecrement)){
                food.count = String.valueOf(Integer.valueOf(food.count) + 1);
            }

            holder.txtCount.setText(food.count);
            holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        });

        holder.btnDecrement.setOnClickListener((v) ->

        {
            if (onCountChangeListener != null)
                onCountChangeListener.onDecrementClick(food.id, Integer.valueOf(food.price));

            if (onDecrementClick(Integer.valueOf(food.count), holder.btnDecrement, holder.btnIncrement)) {
                food.count = String.valueOf(Integer.valueOf(food.count) - 1);
            }

            holder.txtCount.setText(food.count);
            holder.txtPrice.setText(String.valueOf(Integer.valueOf(food.price) * Integer.valueOf(food.count)));
        });
    }

    private boolean onDecrementClick(int count, ImageButton btnDecrement, ImageButton btnIncrement) {
        if (count < 2) {
            btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            btnDecrement.setClickable(false);
            return false;
        }

        if(count == 2){
            btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            btnDecrement.setClickable(false);
            return true;
        }

        btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_enable));
        btnIncrement.setClickable(true);

        return true;
    }

    private boolean onIncrementClick(int count, ImageButton btnIncrement, ImageButton btnDecrement) {
        if (count > 8) {
            btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            btnIncrement.setClickable(false);
            return false;
        }

        if(count == 8){
            btnIncrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_disable));
            btnIncrement.setClickable(false);
            return true;
        }

        btnDecrement.setBackground(ctx.getResources().getDrawable(R.drawable.btn_style_enable));
        btnDecrement.setClickable(true);
        return true;
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class LootViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView txtTitle, txtPrice, txtCount;

        private ImageButton btnIncrement, btnDecrement;

        public LootViewHolder(View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgLootEatPhoto);
            txtPrice = itemView.findViewById(R.id.txtLootEatPrice);
            txtTitle = itemView.findViewById(R.id.txtLootEatTitle);

            btnDecrement = itemView.findViewById(R.id.btnLootCountMinus);
            btnIncrement = itemView.findViewById(R.id.btnLootCountPlus);
            txtCount = itemView.findViewById(R.id.txtLootEatCount);
        }
    }
}