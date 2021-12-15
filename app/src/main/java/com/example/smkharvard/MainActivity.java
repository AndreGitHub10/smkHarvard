package com.example.smkharvard;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    EditText nama_lengkap, nrp, program_studi, alamat, kontak, id_mhs;
    ProgressBar progressBar;
    ListView listView, viewHeader;
    Button simpan_btn, batal_btn, popup;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int current_id;

    List<Mahasiswa> mahasiswaList;
    boolean isUpdating = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        listView=(ListView) findViewById(R.id.listViewMahasiswa);
        mahasiswaList = new ArrayList<>();
        popup = findViewById(R.id.popup);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        popup.setOnClickListener(view -> {
            createNewContactDialog();
        });
        readMahasiswa();
    }
    private void createMahasiswa() {
        String isi_nama_lengkap = nama_lengkap.getText().toString().trim();
        String isi_nrp = nrp.getText().toString().trim();
        String isi_program_studi = program_studi.getText().toString().trim();
        String isi_alamat = alamat.getText().toString().trim();
        String isi_kontak = kontak.getText().toString().trim();
        if (TextUtils.isEmpty(isi_nama_lengkap) || TextUtils.isEmpty(isi_nrp) || TextUtils.isEmpty(isi_program_studi) || TextUtils.isEmpty(isi_alamat) || TextUtils.isEmpty(isi_kontak)) {
            if (TextUtils.isEmpty(isi_nama_lengkap)) {
                nama_lengkap.setError("Nama harus diisi");
                nama_lengkap.requestFocus();
            }
            if (TextUtils.isEmpty(isi_nrp)) {
                nrp.setError("NRP harus diisi");
                nrp.requestFocus();
            }
            if (TextUtils.isEmpty(isi_program_studi)) {
                program_studi.setError("Prodi harus diisi");
                program_studi.requestFocus();
            }
            if (TextUtils.isEmpty(isi_alamat)) {
                alamat.setError("Alamat harus diisi");
                alamat.requestFocus();
            }
            if (TextUtils.isEmpty(isi_kontak)) {
                kontak.setError("Kontak harus diisi");
                kontak.requestFocus();
            }
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("nama", isi_nama_lengkap);
        params.put("nrp", isi_nrp);
        params.put("prodi", isi_program_studi);
        params.put("alamat", isi_alamat);
        params.put("kontak", isi_kontak);
        //Memangil create Mahasiswa API
        PerformNetworkRequest request = new
                PerformNetworkRequest(ApiMahasiswa.URL_C_MHS, params,
                CODE_POST_REQUEST);
        request.execute();

    }

    private class PerformNetworkRequest extends AsyncTask<Void,Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        PerformNetworkRequest(String url, HashMap<String, String> params, int
                requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    refreshMahasiswaList(object.getJSONArray("mahasiswa"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);
            return null;
        }
    }

    private void readMahasiswa() {
        PerformNetworkRequest request = new
                PerformNetworkRequest(ApiMahasiswa.URL_R_MHS, null, CODE_GET_REQUEST);
        request.execute();
    }

    public void refreshMahasiswaList(JSONArray mahasiswa) throws JSONException{
        mahasiswaList.clear();
        for (int i = 0; i < mahasiswa.length(); i++) {

            JSONObject obj = mahasiswa.getJSONObject(i);
            mahasiswaList.add(new Mahasiswa(
                    obj.getInt("id"),
                    obj.getString("nama"),
                    obj.getString("nrp"),
                    obj.getString("prodi"),
                    obj.getString("alamat"),
                    obj.getString("kontak")
            ));
        }
        MahasiswaAdapter adapter = new MahasiswaAdapter(mahasiswaList);
        listView.setAdapter(adapter);
    }

    private void updateMahasiswa(){
        String isi_id_mhs = id_mhs.getText().toString();
        String isi_nama_lengkap = nama_lengkap.getText().toString().trim();
        String isi_nrp = nrp.getText().toString().trim();
        String isi_program_studi = program_studi.getText().toString().trim();
        String isi_alamat = alamat.getText().toString().trim();
        String isi_kontak = kontak.getText().toString().trim();
        if (TextUtils.isEmpty(isi_nama_lengkap) || TextUtils.isEmpty(isi_nrp) || TextUtils.isEmpty(isi_program_studi) || TextUtils.isEmpty(isi_alamat) || TextUtils.isEmpty(isi_kontak)) {
            if (TextUtils.isEmpty(isi_nama_lengkap)) {
                nama_lengkap.setError("Nama harus diisi");
                nama_lengkap.requestFocus();
            }
            if (TextUtils.isEmpty(isi_nrp)) {
                nrp.setError("NRP harus diisi");
                nrp.requestFocus();
            }
            if (TextUtils.isEmpty(isi_program_studi)) {
                program_studi.setError("Prodi harus diisi");
                program_studi.requestFocus();
            }
            if (TextUtils.isEmpty(isi_alamat)) {
                alamat.setError("Alamat harus diisi");
                alamat.requestFocus();
            }
            if (TextUtils.isEmpty(isi_kontak)) {
                kontak.setError("Kontak harus diisi");
                kontak.requestFocus();
            }
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id", isi_id_mhs);
        params.put("nama", isi_nama_lengkap);
        params.put("nrp", isi_nrp);
        params.put("prodi", isi_program_studi);
        params.put("alamat", isi_alamat);
        params.put("kontak", isi_kontak);
        PerformNetworkRequest request = new PerformNetworkRequest(ApiMahasiswa.URL_U_MHS,
                params, CODE_POST_REQUEST);
        request.execute();
        isUpdating = false;
    }

    private void deleteMahasiswa(int id) {
        PerformNetworkRequest request = new
                PerformNetworkRequest(ApiMahasiswa.URL_D_MHS + id, null,
                CODE_GET_REQUEST);
        request.execute();
    }


    //INNER CLASS Mahasiswa Adapter//
    public class MahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
        List<Mahasiswa> mahasiswaList;
        public MahasiswaAdapter(List<Mahasiswa> mahasiswaList) {
            super(MainActivity.this, R.layout.layout_mahasiswa_list,
                    mahasiswaList);
            this.mahasiswaList=mahasiswaList;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_mahasiswa_list,
                    null, true);
            TextView textViewNama = listViewItem.findViewById(R.id.textViewNama);
            ImageButton buttonUpdate =
                    listViewItem.findViewById(R.id.buttonUpdate);
            ImageButton buttonDelete =
                    listViewItem.findViewById(R.id.buttonDelete);
            final Mahasiswa mahasiswa = mahasiswaList.get(position);
            textViewNama.setText(mahasiswa.getNama());
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating=true;
                    current_id=position;
                    createNewContactDialog();
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Delete " + mahasiswa.getNama()).setMessage("Are you sure you want to delete it?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteMahasiswa(mahasiswa.getId());
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            });
            return listViewItem;
        }
    }

    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_form_mahasiswa, null);
        nama_lengkap = contactPopupView.findViewById(R.id.nama_lengkap);
        nrp = contactPopupView.findViewById(R.id.nrp);
        program_studi = contactPopupView.findViewById(R.id.program_studi);
        alamat = contactPopupView.findViewById(R.id.alamat);
        kontak = contactPopupView.findViewById(R.id.kontak);
        id_mhs = contactPopupView.findViewById(R.id.id_mhs);

        simpan_btn = contactPopupView.findViewById(R.id.simpan_btn);
        batal_btn = contactPopupView.findViewById(R.id.batal_btn);

        if (isUpdating) {
            final Mahasiswa mahasiswa = mahasiswaList.get(current_id);
            id_mhs.setText(String.valueOf(mahasiswa.getId()));
            nama_lengkap.setText(String.valueOf(mahasiswa.getNama()));
            nrp.setText(String.valueOf(mahasiswa.getNrp()));
            program_studi.setText(String.valueOf(mahasiswa.getProdi()));
            alamat.setText(String.valueOf(mahasiswa.getAlamat()));
            kontak.setText(String.valueOf(mahasiswa.getKontak()));
            simpan_btn.setText("Update");
        } else {
            nama_lengkap.setText("");
            nrp.setText("");
            program_studi.setText("");
            alamat.setText("");
            kontak.setText("");
        }

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //proses simpan
                if (!isUpdating) {
                    createMahasiswa();
                } else {
                    updateMahasiswa();
                    simpan_btn.setText("Simpan");
                }
            }
        });

        batal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //batalkan
                dialog.dismiss();
                isUpdating = false;
                simpan_btn.setText("Simpan");
            }
        });
    }
}

