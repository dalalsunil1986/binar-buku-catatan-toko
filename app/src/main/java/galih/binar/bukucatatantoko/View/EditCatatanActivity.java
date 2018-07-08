package galih.binar.bukucatatantoko.View;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import galih.binar.bukucatatantoko.Presenter.EditCatatanPresenter;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.databinding.ActivityEditCatatanBinding;

public class EditCatatanActivity extends AppCompatActivity {
    public ActivityEditCatatanBinding binding;
    EditCatatanPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_catatan);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_catatan);
        presenter = new EditCatatanPresenter(this);
        binding.setPresenter(presenter);
        setTitle("Edit Catatan");
    }
}
