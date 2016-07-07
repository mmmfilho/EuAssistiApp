package com.app.maurofilho.ilhafilmes.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.app.maurofilho.ilhafilmes.R;
import com.app.maurofilho.ilhafilmes.model.Filme;
import com.app.maurofilho.ilhafilmes.sqlite.DataBaseSQLite;
import com.app.maurofilho.ilhafilmes.utils.SimpleRecyclerAdapterFilmes;

import java.util.ArrayList;

public class ListFilmesActivity extends AppCompatActivity {

    DataBaseSQLite mydb;
    RecyclerView recyclerView;
    SimpleRecyclerAdapterFilmes adapter;
    CardView listItem;

    public ListFilmesActivity() {
        mydb = new DataBaseSQLite(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filmes);
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

        //Carrega Lista de Filmes
        listItem = (CardView) findViewById(R.id.cardlist_filme);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_filmes);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Filme> listFilmes = new ArrayList<>();

        listFilmes = mydb.getAllFilme();

        if (adapter == null) {
            adapter = new SimpleRecyclerAdapterFilmes(getBaseContext(), listFilmes);
            recyclerView.setAdapter(adapter);
        }

        adapter.SetOnItemClickListener(new SimpleRecyclerAdapterFilmes.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
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

}
