package com.app.maurofilho.ilhafilmes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.app.maurofilho.ilhafilmes.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.tools.OmdbBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
    }

    public void pesquisar(View v) {
        EditText pesquisa = (EditText) findViewById(R.id.edit_pesquisa);
        Log.i("pesquisa por:", pesquisa.getText().toString());
        OmdbApi omdb = new OmdbApi();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle("Star Wars").build());

            OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle(pesquisa.getText().toString()).build());
            if(result.isResponse()== true){
                Log.i("Titulo",result.getTitle());
                Log.i("Gênero",result.getGenre());
                Log.i("Box",result.toString());
                Log.i("poster",result.getPoster());

                Intent i = new Intent(getApplicationContext(), SalvarFilmeActivity.class);
                i.putExtra("result",result);
                startActivity(i);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //define o titulo
                builder.setTitle("Info");
                // define a mensagem
                builder.setMessage("Não encontramos nenhum filme com este nome!");
                //icone
                builder.setIcon(android.R.mipmap.sym_def_app_icon);
                //botõa OK
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                //Exibir
                builder.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Info");
            // define a mensagem
            builder.setMessage("Não encontramos nenhum filme com este nome!");
            //icone
            builder.setIcon(android.R.mipmap.sym_def_app_icon);
            //botõa OK
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            //Exibir
            builder.show();

        }
    }

    public void verfilmes(View view){
        startActivity(new Intent(getApplicationContext(), ListFilmesActivity.class));
    }
}
