package galih.binar.bukucatatantoko.View.Adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import galih.binar.bukucatatantoko.Interfaces.HapusEditCatatanListener;
import galih.binar.bukucatatantoko.Model.Catatan;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.databinding.FragLihatItemCatatanBinding;

/**
 * Created by Galih Laras Prakoso on 7/8/2018.
 */

public class ListCatatanAdapter extends RecyclerView.Adapter<ListCatatanAdapterViewHolder> {

    public ArrayList<Catatan> listCatatan;
    HapusEditCatatanListener listener;

    public ListCatatanAdapter(ArrayList<Catatan> listCatatan, HapusEditCatatanListener listener) {
        this.listCatatan = listCatatan;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListCatatanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragLihatItemCatatanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.frag_lihat_item_catatan,parent,false);

        ListCatatanAdapterViewHolder viewHolder = new ListCatatanAdapterViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListCatatanAdapterViewHolder holder, final int position) {
        final Catatan catatan = listCatatan.get(position);
        holder.binding.setCatatan(catatan);
        holder.binding.fragLihatItemCatatanHapus
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.hapus(catatan.id);
                    }
                });
        holder.binding.fragLihatItemCatatanEdit
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.edit(catatan.id);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return listCatatan.size();
    }
}

class ListCatatanAdapterViewHolder extends RecyclerView.ViewHolder{
    FragLihatItemCatatanBinding binding;
    public ListCatatanAdapterViewHolder(FragLihatItemCatatanBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

