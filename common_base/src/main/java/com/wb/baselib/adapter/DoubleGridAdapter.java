package com.wb.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:28
 * description:
 */
public class DoubleGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<String> topGridData;
//    private List<String> bottomGridData;
    private View.OnClickListener mListener;
    private boolean isReset;



    public void setSelectOption(boolean isReset) {
        this.isReset = isReset;
    }

    public DoubleGridAdapter(Context context, List<String> topGridData, List<String> bottomGridList, View.OnClickListener listener) {
        this.mContext = context;
        this.topGridData = topGridData;
//        this.bottomGridData = bottomGridList;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == topGridData.size() + 1) {
            return TYPE_TITLE;
        }

        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_TITLE:
                holder = new TitleViewHolder(mContext, parent);
                break;
            case TYPE_ITEM:
                holder = new ItemViewHolder(mContext, parent, mListener);
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        Log.e("position",position+"");
        switch (itemViewType) {
            case TYPE_TITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                if (position == 0) {
                    titleViewHolder.bind("课程类型");
                } else {
                    titleViewHolder.bind("Bottom");
                }
                break;
            case TYPE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                if (position < topGridData.size() + 1) {

                    itemViewHolder.bind(topGridData.get(position - 1),isReset);
                  //  itemViewHolder.bind(topGridData.get(position - 1),selectOption==position);
                } else {
//                    itemViewHolder.bind(bottomGridData.get(position - topGridData.size() - 2));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return topGridData.size()  + 1;
    }
}