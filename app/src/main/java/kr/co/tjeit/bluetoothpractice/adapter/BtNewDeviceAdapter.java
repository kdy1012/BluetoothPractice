package kr.co.tjeit.bluetoothpractice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import kr.co.tjeit.bluetoothpractice.R;
import kr.co.tjeit.bluetoothpractice.data.BtDevice;

/**
 * Created by the on 2017-09-13.
 */

public class BtNewDeviceAdapter extends ArrayAdapter<BtDevice> {

    Context mContext;
    List<BtDevice> mList;
    LayoutInflater inf;

    public BtNewDeviceAdapter(Context context, List<BtDevice> list) {
        super(context, R.layout.btdeivce_list_item, list);

        mContext = context;
        mList = list;
        inf = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = inf.inflate(R.layout.btdeivce_list_item, null);
        }


        return row;
    }
}
