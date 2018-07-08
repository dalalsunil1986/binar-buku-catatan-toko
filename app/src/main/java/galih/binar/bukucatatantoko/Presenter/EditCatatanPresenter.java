package galih.binar.bukucatatantoko.Presenter;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import galih.binar.bukucatatantoko.Database.CatatanDBHelper;
import galih.binar.bukucatatantoko.Helper.Common;
import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Interfaces.OnFinishListener;
import galih.binar.bukucatatantoko.Model.Catatan;
import galih.binar.bukucatatantoko.View.EditCatatanActivity;

/**
 * Created by Galih Laras Prakoso on 7/8/2018.
 */

public class EditCatatanPresenter extends Presenter<EditCatatanActivity> {
    CatatanDBHelper catatanDBHelper;
    public Catatan catatan;
    DatePickerDialog.OnDateSetListener date;

    String ID;

    private static int PCS = 0;
    private static int DUS = 1;

    Common c;

    public EditCatatanPresenter(final EditCatatanActivity activity){
        super.setActivity(activity);
        init();
    }

    private void init() {
        initDataCatatan();
    }

    private void initDataCatatan() {
        catatanDBHelper = new CatatanDBHelper();

        c = new Common(activity);

        catatan = new Catatan();

        ID = activity.getIntent().getStringExtra("ID");

        c.showL("Mengambil data catatan...");
        final EditCatatanPresenter presenter = this;
        catatanDBHelper.getCatatan(ID, new GetDataFromDB() {
            @Override
            public void getData(Boolean status, Object data) {
                c.disL();
                Catatan result = (Catatan)data;

                catatan.nama = result.nama;
                catatan.tanggal= result.tanggal;
                catatan.banyak_barang = result.banyak_barang;
                catatan.satuan = result.satuan;
                catatan.pemasok = result.pemasok;
                catatan.tanggal = result.tanggal;

                if(result.satuan.equals("PCS")){
                    activity.binding.actEditSatuan.setSelection(0);
                }else{
                    activity.binding.actEditSatuan.setSelection(1);
                }

                activity.binding.setPresenter(presenter);
            }
        });

        initTanggalListener();
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

        activity.binding.actEditTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateEditText(Calendar myCalendar){
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        activity.binding.actEditTgl.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateCatatan(){
        if(validasiInputCatatan()){

            if(activity.binding.actEditSatuan.getSelectedItemPosition()==PCS){
                catatan.satuan = "PCS";
            }else{
                catatan.satuan = "DUS";
            }

            c.showL("Mengupdate catatan...");
            catatanDBHelper.updateCatatan(ID,catatan, new OnFinishListener() {
                @Override
                public void onFinished(Boolean result) {
                    c.disL();
                    if(result){
                        c.showT("Berhasil mengupdate catatan.");
                        activity.finish();
                    }else{
                        c.showT("Gagal mengupdate catatan.");
                    }
                }
            });
        }else{
            c.showT("Tidak boleh ada field yang kosong.");
        }
    }

    private boolean validasiInputCatatan(){
        return catatan.nama!=null && catatan.nama.length()!=0
                && catatan.banyak_barang!=null && catatan.banyak_barang.length()!=0
                && catatan.pemasok!=null && catatan.pemasok.length()!=0
                && catatan.tanggal!=null && catatan.tanggal.length()!=0;
    }


}
