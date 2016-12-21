package com.egco428.a13257;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ZzCornetto on 18/12/2559.
 */

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;

    public CustomAdapter(Context context, String[] strName) {
        this.mContext= context;
        this.strName = strName;
    }

    @Override
    public int getCount() {
        return strName.length;
    }

    @Override
    public Object getItem(int position) {
        return strName[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if(view == null)
            view = mInflater.inflate(R.layout.customlist, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.list_user);
        textView.setText(strName[position]);

        return view;
    }
}
