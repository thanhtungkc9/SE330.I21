package com.example.thanhtung.simplynotes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ThanhTung on 02-May-18.
 */

public class GhiChuAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<GhiChu> ghiChuList;

    public GhiChuAdapter(Context context, int layout, List<GhiChu> ghiChuList){
        this.context=context;
        this.layout=layout;
        this.ghiChuList=ghiChuList;
    }
    public List<GhiChu> getListData()
    {
        return ghiChuList;
    }
    @Override
    public int getCount() {
        return ghiChuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout,null);
        TextView txtTieuDe= (TextView) convertView.findViewById(R.id.txtTieuDe);
        txtTieuDe.setText(ghiChuList.get(position).getTieuDe());

        TextView txtNgayTao= (TextView) convertView.findViewById(R.id.txtNgayTao);
        txtNgayTao.setText(ghiChuList.get(position).getNgayTao());

        TextView txtNoiDung= (TextView) convertView.findViewById(R.id.txtNoiDung);
        txtNoiDung.setText(ghiChuList.get(position).getNoiDung());

        TextView txtNgayNhacNho= (TextView) convertView.findViewById(R.id.txtNgayNhacNho);
        txtNgayNhacNho.setText(ghiChuList.get(position).getNgayNhacNho()+"  "+ghiChuList.get(position).getGioNhacNho());

        ImageView btnDelete=(ImageView) convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHandler database=new SQLiteDatabaseHandler(context);
                database.deleteNote(ghiChuList.get(position).getId());
                ghiChuList.remove(ghiChuList.get(position));
                notifyDataSetChanged();
            }
        });
        txtTieuDe.setTextColor(Color.parseColor(ghiChuList.get(position).getMauChu()));
        txtNoiDung.setTextColor(Color.parseColor(ghiChuList.get(position).getMauChu()));
        txtNgayTao.setTextColor(Color.parseColor(ghiChuList.get(position).getMauChu()));
        txtNgayNhacNho.setTextColor(Color.parseColor(ghiChuList.get(position).getMauChu()));
        convertView.setBackgroundColor(Color.parseColor(ghiChuList.get(position).getMauNen()));
        return convertView;
    }
}
