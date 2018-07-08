package galih.binar.bukucatatantoko.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import galih.binar.bukucatatantoko.Database.PenggunaDBHelper;
import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Model.Pengguna;
import galih.binar.bukucatatantoko.R;
import galih.binar.bukucatatantoko.View.Fragments.LihatFragment;
import galih.binar.bukucatatantoko.View.Fragments.TambahFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PenggunaDBHelper penggunaDBHelper;
    FirebaseAuth mAuth;

    LihatFragment lihatFragment;
    TambahFragment tambahFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        initFragments();
        mAuth = FirebaseAuth.getInstance();
        initToolBarAndDrawer();
        initNavigationView();
        navigateToFragment(lihatFragment,"Lihat Catatan");
    }

    private void initFragments() {
        lihatFragment = new LihatFragment();
        tambahFragment = new TambahFragment();
    }

    private void initNavigationView() {
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View header = navigationView.getHeaderView(0);

        final TextView nama = header.findViewById(R.id.act_main_nav_nama);
        final TextView email = header.findViewById(R.id.act_main_nav_email);

        final Menu nav_Menu = navigationView.getMenu();

        penggunaDBHelper = new PenggunaDBHelper();

        penggunaDBHelper
                .getPengguna(mAuth.getCurrentUser().getEmail(), new GetDataFromDB() {
                    @Override
                    public void getData(Boolean status, Object data) {
                        Pengguna pengguna = (Pengguna)data;

                        if(pengguna.posisi.equals("gudang")){
                            nav_Menu.findItem(R.id.nav_tambah_catatan).setVisible(true);
                        }else{
                            nav_Menu.findItem(R.id.nav_tambah_catatan).setVisible(false);
                        }

                        nama.setText(pengguna.nama);
                        email.setText(pengguna.email);

                    }
                });

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolBarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.dotted_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_lihat_catatan:
                navigateToFragment(lihatFragment,"Lihat Catatan");
                break;
            case R.id.nav_tambah_catatan:
                navigateToFragment(tambahFragment,"Tambah Catatan");
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToFragment(Fragment fragment, String name){
        final FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        setTitle(name);
        fragTrans.replace(R.id.main_content_wrapper, fragment, name);
        fragTrans.commit();
    }

}
