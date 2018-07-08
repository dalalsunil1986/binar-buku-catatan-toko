package galih.binar.bukucatatantoko.View.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import galih.binar.bukucatatantoko.Presenter.TambahPresenter;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.databinding.FragmentTambahBinding;

public class TambahFragment extends Fragment {

    View v;

    public FragmentTambahBinding binding;

    TambahPresenter presenter;

    public TambahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_tambah, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tambah,container,false);

        v = binding.getRoot();

        initController();

        return v;
    }

    private void initController() {
        presenter = new TambahPresenter(this);
        binding.setPresenter(presenter);
    }

}
