package com.example.hp.assignment.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.assignment.MainActivity;
import com.example.hp.assignment.R;
import com.example.hp.assignment.database.DatabaseHelper;
import com.example.hp.assignment.model.Lop;

import java.util.List;

public class ClassAdapter extends BaseAdapter{

    public Context context;
    public List<Lop> lopList;
    public DatabaseHelper sqLite;

    public ClassAdapter(Context context, List<Lop> productList){
        this.lopList = productList;
        this.context = context;
        sqLite = new DatabaseHelper(context);

    }

    @Override
    public int getCount() {
        return lopList.size();
    }

    @Override
    public Object getItem(int position) {
        return lopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        TextView tvID = convertView.findViewById(R.id.tvID);
        TextView tvName = convertView.findViewById(R.id.tvName);
        ImageView imgDel = convertView.findViewById(R.id.imgDel);
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lop lop = lopList.get(position);
                if (sqLite.deleteClass(lop.getId())>0){
                    lopList.remove(lop);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Lop lop1=(Lop) getItem(position);
        tvID.setText(lop1.getId());
        tvName.setText(lop1.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lop lop = lopList.get(position);
                Intent intent= new Intent(context, MainActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("id",lop.getId());
                bundle.putString("name",lop.getName());
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
