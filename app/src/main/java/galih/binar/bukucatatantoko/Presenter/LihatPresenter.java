package galih.binar.bukucatatantoko.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import galih.binar.bukucatatantoko.Database.CatatanDBHelper;
import galih.binar.bukucatatantoko.Database.PenggunaDBHelper;
import galih.binar.bukucatatantoko.Helper.Common;
import galih.binar.bukucatatantoko.Interfaces.GetAllCatatan;
import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Interfaces.HapusEditCatatanListener;
import galih.binar.bukucatatantoko.Interfaces.OnFinishListener;
import galih.binar.bukucatatantoko.Model.Catatan;
import galih.binar.bukucatatantoko.Model.Pengguna;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.View.Adapter.ListCatatanAdapter;
import galih.binar.bukucatatantoko.View.EditCatatanActivity;
import galih.binar.bukucatatantoko.View.Fragments.LihatFragment;

/**
 * Created by Galih Laras Prakoso on 7/8/2018.
 */

public class LihatPresenter extends Presenter<LihatFragment> {
    CatatanDBHelper catatanDBHelper;
    ListCatatanAdapter listCatatanAdapter;
    LihatFragment fragment;
    Common c;
    PenggunaDBHelper penggunaDBHelper;
    Pengguna pengguna;

    FirebaseAuth mAuth;

    public LihatPresenter(LihatFragment fragment){
        super.setActivity(fragment);
        init();
    }

    private void init() {
        fragment = (LihatFragment)activity;
        c = new Common(fragment.getContext());
        catatanDBHelper = new CatatanDBHelper();
        mAuth = FirebaseAuth.getInstance();

        fragment.binding.fragLihatListCatatan
                .setLayoutManager(new LinearLayoutManager(fragment.getContext(),
                        LinearLayoutManager.VERTICAL,false));

        SharedPreferences prefs = fragment.getActivity().getSharedPreferences("POSISI", Context.MODE_PRIVATE);

        String posisi = prefs.getString("POSISI","");

        Boolean gudang = false;

        if(posisi.equals("gudang")){
            gudang = true;
        }else{
            fragment.binding.fragLihatHeaderEdit.setVisibility(View.GONE);
            fragment.binding.fragLihatHeaderHapus.setVisibility(View.GONE);
        }

        listCatatanAdapter = new ListCatatanAdapter(new ArrayList<Catatan>(), new HapusEditCatatanListener() {
            @Override
            public void hapus(String id) {
                catatanDBHelper.hapusCatatan(id, new OnFinishListener() {
                    @Override
                    public void onFinished(Boolean result) {
                        if(result){
                            c.showT("Hapus catatan berhasil.");
                        }else{
                            c.showT("Hapus catatan gagal.");
                        }
                    }
                });

            }

            @Override
            public void edit(String id) {
                Intent intent = new Intent(fragment.getActivity(), EditCatatanActivity.class);
                intent.putExtra("ID",id);
                activity.startActivity(intent);
            }
        },gudang);


        fragment.binding.fragLihatListCatatan.setAdapter(listCatatanAdapter);

        getDataCatatan();
    }

    private void getDataCatatan(){
        catatanDBHelper.getAll(new GetAllCatatan() {
            @Override
            public void getAll(ArrayList<Catatan> allCatatan) {
                if(allCatatan.size()==0){
                       fragment.binding.fragLihatBelumAda.setVisibility(View.VISIBLE);
                }else{
                    fragment.binding.fragLihatBelumAda.setVisibility(View.GONE);
                }
                listCatatanAdapter.listCatatan = allCatatan;
                listCatatanAdapter.notifyDataSetChanged();
            }
        });
    }
}
