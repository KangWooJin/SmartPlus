package com.smartplus.smartplus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by seose on 2015-05-17.
 */
public class ListAdapter extends BaseAdapter {

    ListOne[] lists = new ListOne[SC.MAX_LIST];
    Context mContext;
    LayoutInflater inflater;
    int tag;

    public ListAdapter(Context c, ListOne[] lists_, int TAG_) {
        mContext = c;

        lists = lists_;
        inflater = (LayoutInflater) c
                .getSystemService(c.LAYOUT_INFLATER_SERVICE);
        tag = TAG_;
    }

    public void reflashList(ListOne[] list_) {
        lists = list_;
    }

    public void setTag(int tag_) {
        tag = tag_;
    }

    @Override
    public int getCount() {
        return SC.MAX_LIST;
    }

    @Override
    public Object getItem(int position) {
        return lists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {
        public TextView txtt;
        public ImageView imgg;
        public LinearLayout ll;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup vgroup) {


        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_one, vgroup, false); // 마지막 false?

            holder.txtt = (TextView) convertView.findViewById(R.id.tv_list_one_tv);
            holder.imgg = (ImageView) convertView.findViewById(R.id.iv_list_one_img);
            holder.ll = (LinearLayout)convertView.findViewById(R.id.ll_list_one_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (tag == 0)  // 일반상태 배치
        {

            if (lists[position].titlename.compareTo("NONE") == 0) {
                holder.txtt.setText("");
                holder.imgg.setImageDrawable(null);
            }
            else if(lists[position].titlename.compareTo("전구") == 0) {
                holder.txtt.setText(lists[position].titlename);
                holder.imgg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.i_right));
            }
            else {
            holder.txtt.setText(lists[position].titlename);
            holder.imgg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.i_air));
        }
        }
        else if (tag == 1)// 체크상태
        {
            if (lists[position].titlename.compareTo("NONE") == 0)
            {
                holder.txtt.setText("");
                holder.imgg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.n_check));
            }
        }
        else if (tag == 10)// 체크상태 배치변경
        {
            if (lists[position].titlename.compareTo("NONE") == 0)
            {
                holder.txtt.setText("");
                holder.imgg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.n_check));
            }
        }

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag==1)
                {
                    onSelectedListener.getPos(position);
                }
                if(tag==10)
                {
                    SC.IS_ARRAYED=true;
                    onSelectedListener.getPos(position);
                    return;
                }
                if(tag==0 && lists[position].titlename.compareTo("NONE") != 0)
                {
                    Log.i("ListAdapter", "LAUNCH");
                    onSelectedListener.getPos(position);
                }
            }
        });

        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(lists[position].titlename.compareTo("NONE") != 0)
                    onLongSelectedListener.getPos(position);

                return true;
            }
        });


        return convertView;
    }

    public void setSelectedListener(OnSelectedListener onSelectedListener_)
    {
        onSelectedListener = onSelectedListener_;
    }

    public OnSelectedListener onSelectedListener;

    public interface OnSelectedListener
    {
        int getPos(int pos);
    }


    public void setLongSelectedListener(OnLongSelectedListener onLongSelectedListener_)
    {
        onLongSelectedListener = onLongSelectedListener_;
    }

    public OnLongSelectedListener onLongSelectedListener;

    public interface OnLongSelectedListener
    {
        int getPos(int pos);
    }


}
