package galih.binar.bukucatatantoko.View.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import galih.binar.bukucatatantoko.Presenter.LihatPresenter;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.databinding.FragmentLihatBinding;

public class LihatFragment extends Fragment {

    public View v;
    LihatPresenter presenter;
    public FragmentLihatBinding binding;

    public LihatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_lihat, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lihat,container,false);

        v = binding.getRoot();

        initController();

        return v;
    }

    private void initController() {
        presenter = new LihatPresenter(this);
        binding.setPresenter(presenter);
    }

}
