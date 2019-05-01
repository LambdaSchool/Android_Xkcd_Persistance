package com.jakeesveld.android_xkcd_persistence;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    ArrayList<Comic> dataList;

    public FavoritesListAdapter(ArrayList<Comic> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorites_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Comic data = dataList.get(i);
        viewHolder.textNumber.setText(Integer.toString(data.getId()));
        viewHolder.textName.setText(data.getTitle());
        viewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XkcdDbDAO.deleteComic(data.getInfo());
            }
        });

        viewHolder.buttonGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.parent.getContext(), MainActivity.class);
                intent.putExtra("Comic", data.getId());
                viewHolder.parent.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textNumber;
        Button buttonRemove;
        Button buttonGoto;
        View parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_view_name);
            textNumber = itemView.findViewById(R.id.text_comic_number);
            buttonRemove = itemView.findViewById(R.id.button_remove);
            buttonGoto = itemView.findViewById(R.id.button_goto);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
