package com.awak25.authentificationanddatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDeleteActivity extends AppCompatActivity {

    private EditText edtNama, edtAlamat;
    private RadioButton rbLakilaki, rbPerempuan;
    private Button btnUpdate, btnDelete;
    private Spinner spinPendidikan;

    private DatabaseReference databaseReference;
    public static String TABLE_BIODATA = "biodata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        edtNama = findViewById(R.id.edt_up_nama);
        edtAlamat = findViewById(R.id.edt_up_alamat);
        rbLakilaki = findViewById(R.id.rb_up_laki_laki);
        rbPerempuan = findViewById(R.id.rb_up_perempuan);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        spinPendidikan = findViewById(R.id.spin_up_materi);

        String [] pendidikan = {"Pilih Pendidikan","SMP", "SMA", "SMK", "Perguruan Tinggi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pendidikan);
        spinPendidikan.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // terima data dari adapter
        final BiodataModel getBiodata = getIntent().getParcelableExtra(TABLE_BIODATA);

        // tampilkan di semua view
        edtNama.setText(getBiodata.getNama());
        edtAlamat.setText(getBiodata.getAlamat());

        if (getBiodata.getGender().equals("Laki-laki")){
            rbLakilaki.setChecked(true);
        }else{
            rbPerempuan.setChecked(true);
        }
        for (int i = 0; i < adapter.getCount(); i++){
            if (spinPendidikan.getItemAtPosition(i).equals(getBiodata.getPendidikan())){
                spinPendidikan.setSelection(i);
            }
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(TABLE_BIODATA).child(getBiodata.getId()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(UpdateDeleteActivity.this, "berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(UpdateDeleteActivity.this, "silahkan pilih gender", Toast.LENGTH_SHORT).show();
                }
                else if(pendidikan.equals("Pilih Pendidikan")){
                    Toast.makeText(UpdateDeleteActivity.this, "Silahkan pilih pendidikan", Toast.LENGTH_SHORT).show();
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

                    databaseReference.child(TABLE_BIODATA).child(getBiodata.getId()).setValue(bd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateDeleteActivity.this, "update berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(UpdateDeleteActivity.this, "gagal", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
