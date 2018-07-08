package galih.binar.bukucatatantoko.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import galih.binar.bukucatatantoko.Database.PenggunaDBHelper;
import galih.binar.bukucatatantoko.Helper.Common;
import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Model.Credential;
import galih.binar.bukucatatantoko.Model.Pengguna;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.View.LoginActivity;
import galih.binar.bukucatatantoko.View.MainActivity;

/**
 * Created by Galih Laras Prakoso on 7/7/2018.
 */

public class LoginPresenter extends Presenter<LoginActivity> {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public Credential credential;
    private Common c;
    private PenggunaDBHelper penggunaDBHelper;

    public LoginPresenter(LoginActivity activity){
        super.setActivity(activity);
        init();
    }

    private void init() {
        c = new Common(activity);
        initFirebaseAuth();
        credential = new Credential();
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            currentUser = mAuth.getCurrentUser();
            goToMain();
        }
    }

    private void goToMain(){
        penggunaDBHelper = new PenggunaDBHelper();
        c.showL("Mengautentikasi...");
        penggunaDBHelper
                .getPengguna(mAuth.getCurrentUser().getEmail(), new GetDataFromDB() {
                    @Override
                    public void getData(Boolean status, Object data) {
                        c.disL();
                        Pengguna pengguna = (Pengguna)data;

                        SharedPreferences.Editor pref = activity.getSharedPreferences("POSISI", Context.MODE_PRIVATE).edit();
                        pref.putString("POSISI", pengguna.posisi);
                        pref.commit();

                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
    }

    public void doLogin(){
        if(credential.email != null && credential.password != null
                && credential.email.length()!=0 && credential.password.length()!=0){
            c.showL("Mengautentikasi...");
            mAuth.signInWithEmailAndPassword(credential.email,credential.password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                c.disL();
                                c.showT("Login berhasil.");
                                goToMain();
                            }else{
                                c.showT("Login gagal, cek kembali email, password dan koneksi internet anda.");
                            }

                        }
                    });
        }else{
            c.showT("Email dan password tidak boleh kosong.");
        }
    }
}
