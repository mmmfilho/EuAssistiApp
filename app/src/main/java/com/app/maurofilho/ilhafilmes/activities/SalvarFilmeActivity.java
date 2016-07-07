package com.app.maurofilho.ilhafilmes.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.maurofilho.ilhafilmes.R;
import com.app.maurofilho.ilhafilmes.sqlite.DataBaseSQLite;
import com.facebook.drawee.view.SimpleDraweeView;
import com.omertron.omdbapi.model.OmdbVideoFull;

import java.io.File;

public class SalvarFilmeActivity extends AppCompatActivity {

    DataBaseSQLite mydb;
    OmdbVideoFull result;

    public SalvarFilmeActivity() {
        mydb = new DataBaseSQLite(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_filme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        result = (OmdbVideoFull) getIntent().getSerializableExtra("result");

        SimpleDraweeView sdview = (SimpleDraweeView) findViewById(R.id.sdview_poster);
        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView genero = (TextView) findViewById(R.id.tv_genero);
        TextView diretor = (TextView) findViewById(R.id.tv_diretor);
        TextView ano = (TextView) findViewById(R.id.tv_ano);


        try {

            Log.i("poster:", result.getActors());
            sdview.setImageURI(Uri.parse(result.getPoster()));
            title.setText(result.getTitle());
            genero.setText(result.getGenre());
            diretor.setText(result.getDirector());
            ano.setText(result.getYear());
        } catch (Exception e) {
            Log.e("DANGER ! ERROR MESSAGE!", e.getMessage());
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvar(View v) {
        try {

            if (mydb.insertFilme(result.getTitle(), result.getGenre(), result.getDirector(), result.getYear())) {
                Snackbar.make(v, "Filmes Salvo com Sucesso!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(v, "Filme Já Cadastrado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        } catch (Exception e) {
            Log.e("DANGER ! ERROR MESSAGE!", e.getMessage());
        }

    }

    public void downloadFile(String uRl, String title) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "//EuAssisti/Posters");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("//EuAssisti/PostersFiles", title + ".jpg");

        mgr.enqueue(request);


    }

}
