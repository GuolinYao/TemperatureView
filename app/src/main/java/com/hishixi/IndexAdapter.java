package com.hishixi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by seamus on 2018/3/21 11:01
 */

class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private List<DyeEntity> list;
    private Context mContext;

    public IndexAdapter(Context context, List<DyeEntity> list) {
        this.list = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new IndexViewHolder(mLayoutInflater.inflate(R.layout.item_index,
                parent, false));
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            });
        }
        holder.tvNum.setText(list.get(position).getLotid());
        holder.tvTime.setText(list.get(position).getLotdeliver());
        holder.tvWeight.setText(list.get(position).getDyeno());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView tvNum, tvTime, tvWeight;

        public IndexViewHolder(View view) {
            super(view);
            tvNum = view.findViewById(R.id.tv_num);
            tvTime = view.findViewById(R.id.tv_time);
            tvWeight = view.findViewById(R.id.tv_weight);
        }

    }
}
