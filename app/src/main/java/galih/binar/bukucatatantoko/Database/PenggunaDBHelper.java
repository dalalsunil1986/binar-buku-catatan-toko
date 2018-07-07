package galih.binar.bukucatatantoko.Database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import galih.binar.bukucatatantoko.Interfaces.GetDataFromDB;
import galih.binar.bukucatatantoko.Model.Pengguna;

/**
 * Created by Galih Laras Prakoso on 7/7/2018.
 */

public class PenggunaDBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String COLLECTION = "pengguna";

    public void getPengguna(String email, final GetDataFromDB getDataFromDB){
        db.collection(COLLECTION).document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final Pengguna pengguna = new Pengguna();

                            DocumentSnapshot snapshot = task.getResult();
                            pengguna.email = snapshot.getId();
                            pengguna.nama = (String)snapshot.get("nama");
                            pengguna.posisi = (String)snapshot.get("posisi");

                            getDataFromDB.getData(true,pengguna);
                        }else{
                            getDataFromDB.getData(false,null);
                        }

                    }
                });

    }

}
