package com.me.demo.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.demo.R;

import java.util.LinkedList;

/**
 * Create by lzf on 2021-03-30
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private String TAG = getClass().getSimpleName();

    private LinkedList<String> mItems;
    private IMainItemListener mMainItemListener;

    public MainRecyclerAdapter(LinkedList<String> items) {
        this.mItems = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_main_adapter, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        holder.mContentTv.setText(mItems.get(position));
        holder.mContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMainItemListener != null) {
                    mMainItemListener.onMainItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void updateItemData(LinkedList<String> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView mContentTv;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mContentTv = itemView.findViewById(R.id.main_adapter_content_tv);
        }
    }

    public void registerMainItemListener(IMainItemListener mainItemListener) {
        mMainItemListener = mainItemListener;
    }
}
