package com.awak25.authentificationanddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    private EditText edtNama, edtAlamat;
    private RadioButton rbLakilaki, rbPerempuan;
    private Button btnSimpan;
    private Spinner spinPendidikan;

    private DatabaseReference databaseReference;

    public static String TABLE_BIODATA = "biodata";
    private RecyclerView rvBiodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        edtNama = findViewById(R.id.edt_nama);
        edtAlamat = findViewById(R.id.edt_alamat);
        rbLakilaki = findViewById(R.id.rb_laki_laki);
        rbPerempuan = findViewById(R.id.rb_perempuan);
        btnSimpan = findViewById(R.id.btn_simpan);
        spinPendidikan = findViewById(R.id.spin_materi);
        rvBiodata = findViewById(R.id.rv_biodata);

        rvBiodata.setLayoutManager(new LinearLayoutManager(this));
        rvBiodata.setHasFixedSize(true);

        reloadData();

        databaseReference = FirebaseDatabase.getInstance().getReference(TABLE_BIODATA);

        String [] pendidikan = {"Pilih Pendidikan","SMP", "SMA", "SMK", "Perguruan Tinggi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pendidikan);
        spinPendidikan.setAdapter(adapter);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = edtNama.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String pendidikan = spinPendidikan.getSelectedItem().toString();
                String gender = null;

                if (TextUtils.isEmpty(nama)){
                    edtNama.setError("Nama tidak boleh kosong");
                }
                else if (TextUtils.isEmpty(alamat)){
                    edtAlamat.setError("Alamat tidak boleh kosong");
                }
                else if(!rbLakilaki.isChecked() && !rbPerempuan.isChecked()){
                    Toast.makeText(RealtimeDatabaseActivity.this, "silahkan pilih gender", Toast.LENGTH_SHORT).show();
                }
                else if(pendidikan.equals("Pilih Pendidikan")){
                    Toast.makeText(RealtimeDatabaseActivity.this, "Silahkan pilih pendidikan", Toast.LENGTH_SHORT).show();
                }
                else{

                    if (rbLakilaki.isChecked()){
                        gender = rbLakilaki.getText().toString();
                    }
                    else if(rbPerempuan.isChecked()){
                        gender = rbPerempuan.getText().toString();
                    }

                    BiodataModel bd = new BiodataModel();
                    bd.setNama(nama);
                    bd.setAlamat(alamat);
                    bd.setGender(gender);
                    bd.setPendidikan(pendidikan);

                    String childId = databaseReference.push().getKey();

                    databaseReference.child(childId).setValue(bd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RealtimeDatabaseActivity.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RealtimeDatabaseActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void reloadData(){

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(TABLE_BIODATA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<BiodataModel> biodataArrayList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BiodataModel biodata = snapshot.getValue(BiodataModel.class);
                    biodata.setId(snapshot.getKey());
                    biodataArrayList.add(biodata);
                }

                BiodataAdapter adapter = new BiodataAdapter(biodataArrayList, RealtimeDatabaseActivity.this);
                adapter.notifyDataSetChanged();
                rvBiodata.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
