package galih.binar.bukucatatantoko.Database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import galih.binar.bukucatatantoko.Interfaces.GetAllCatatan;
import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Interfaces.OnFinishListener;
import galih.binar.bukucatatantoko.Model.Catatan;
import galih.binar.bukucatatantoko.Model.Pengguna;

/**
 * Created by Galih Laras Prakoso on 7/8/2018.
 */

public class CatatanDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String COLLECTION = "catatan";

    public void hapusCatatan(String id,final OnFinishListener listener){
        db.collection(COLLECTION).document(id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(COLLECTION, "DocumentSnapshot successfully deleted!");
                        listener.onFinished(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(COLLECTION, "Error deleting document", e);
                        listener.onFinished(false);
                    }
                });

    }

    public void getCatatan(String id, final GetDataFromDB getDataFromDB){
        db.collection(COLLECTION).document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final Catatan catatan = new Catatan();

                            DocumentSnapshot snapshot = task.getResult();

                            catatan.id= snapshot.getId();
                            catatan.nama = snapshot.getString(Catatan.COL_NAMA);
                            catatan.satuan = snapshot.getString(Catatan.COL_SATUAN);
                            catatan.banyak_barang = snapshot.getString(Catatan.COL_BANYAK);
                            catatan.pemasok= snapshot.getString(Catatan.COL_PEMASOK);
                            catatan.tanggal= snapshot.getString(Catatan.COL_TANGGAL);

                            getDataFromDB.getData(true,catatan);
                        }else{
                            getDataFromDB.getData(false,null);
                        }
                    }
                });
    }

    public void getAll(final GetAllCatatan listener){
        db.collection(COLLECTION).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                ArrayList<Catatan> hasil = new ArrayList<>();

                if(e!=null){
                    Log.w(COLLECTION, "Listen failed.", e);
                    listener.getAll(null);
                }else {
                    for (QueryDocumentSnapshot doc : value) {
                        Catatan catatan = new Catatan();
                        if (doc.get(Catatan.COL_NAMA) != null) {
                            catatan.id = doc.getId();
                            catatan.nama=doc.getString(Catatan.COL_NAMA);
                            catatan.banyak_barang=doc.getString(Catatan.COL_BANYAK);
                            catatan.pemasok=doc.getString(Catatan.COL_PEMASOK);
                            catatan.satuan=doc.getString(Catatan.COL_SATUAN);
                            catatan.tanggal=doc.getString(Catatan.COL_TANGGAL);

                            hasil.add(catatan);
                        }
                    }

                    listener.getAll(hasil);
                }
            }
        });
    }

    public void tambahCatatan(Catatan catatan, final OnFinishListener listener){
        HashMap<String,Object> mapCatatan = new HashMap<>();

        mapCatatan.put(Catatan.COL_NAMA,catatan.nama);
        mapCatatan.put(Catatan.COL_BANYAK,catatan.banyak_barang);
        mapCatatan.put(Catatan.COL_PEMASOK,catatan.pemasok);
        mapCatatan.put(Catatan.COL_TANGGAL,catatan.tanggal);
        mapCatatan.put(Catatan.COL_SATUAN,catatan.satuan);

        db.collection(COLLECTION).add(mapCatatan)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onFinished(true);
                        Log.d(COLLECTION, "Catatan ditambah dengan ID : " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFinished(false);
                        Log.d(COLLECTION, "Gagal menambah catatan : " + e.getMessage());
                    }
        });
    }
}
