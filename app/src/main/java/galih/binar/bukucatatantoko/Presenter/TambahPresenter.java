package galih.binar.bukucatatantoko.Presenter;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import galih.binar.bukucatatantoko.Database.CatatanDBHelper;
import galih.binar.bukucatatantoko.Helper.Common;
import galih.binar.bukucatatantoko.Interfaces.OnFinishListener;
import galih.binar.bukucatatantoko.Model.Catatan;
import galih.binar.bukucatatantoko.View.Fragments.TambahFragment;

/**
 * Created by Galih Laras Prakoso on 7/8/2018.
 */

public class TambahPresenter extends Presenter<TambahFragment> {
    public Catatan catatan;
    TambahFragment fragment;
    DatePickerDialog.OnDateSetListener date;
    Common c;
    CatatanDBHelper catatanDBHelper;

    private static int PCS = 0;
    private static int DUS = 1;

    public TambahPresenter(TambahFragment fragment){
        super.setActivity(fragment);
        init();
    }

    private void init() {
        fragment = (TambahFragment)activity;
        c = new Common(fragment.getContext());
        catatan = new Catatan();
        initTanggalListener();
        catatanDBHelper = new CatatanDBHelper();

    }

    private void initTanggalListener() {
        final Calendar myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditText(myCalendar);
            }
        };

        fragment.binding.fragTambahTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(fragment.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateEditText(Calendar myCalendar){
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fragment.binding.fragTambahTgl.setText(sdf.format(myCalendar.getTime()));
    }

    public void tambahCatatan(){
        if(validasiInputCatatan()){

            if(fragment.binding.fragTambahSatuan.getSelectedItemPosition()==PCS){
                catatan.satuan = "PCS";
            }else{
                catatan.satuan = "DUS";
            }

            c.showL("Menambahkan catatan...");
            catatanDBHelper.tambahCatatan(catatan, new OnFinishListener() {
                @Override
                public void onFinished(Boolean result) {
                    c.disL();
                    if(result){
                        c.showT("Berhasil menambahkan catatan.");
                    }else{
                        c.showT("Gagal menambahkan catatan.");
                    }
                }
            });
        }else{
            c.showT("Tidak boleh ada field yang kosong.");
        }

        catatan = new Catatan();
        fragment.binding.setPresenter(this);
    }

    private boolean validasiInputCatatan(){
        return catatan.nama!=null && catatan.nama.length()!=0
                && catatan.banyak_barang!=null && catatan.banyak_barang.length()!=0
                && catatan.pemasok!=null && catatan.pemasok.length()!=0
                && catatan.tanggal!=null && catatan.tanggal.length()!=0;
    }
}
