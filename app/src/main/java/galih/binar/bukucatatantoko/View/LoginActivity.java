package galih.binar.bukucatatantoko.View;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import galih.binar.bukucatatantoko.Presenter.LoginPresenter;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    LoginPresenter presenter;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        presenter = new LoginPresenter(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setPresenter(presenter);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
}
