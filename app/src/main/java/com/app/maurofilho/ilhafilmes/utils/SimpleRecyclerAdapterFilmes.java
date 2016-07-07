package com.app.maurofilho.ilhafilmes.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.maurofilho.ilhafilmes.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.app.maurofilho.ilhafilmes.model.Filme;

public class SimpleRecyclerAdapterFilmes extends RecyclerView.Adapter<SimpleRecyclerAdapterFilmes.VersionViewHolder> {


    List<Filme> filmeList;
    public static List<String> titleList = new ArrayList<String>();
    public static List<String> genreList = new ArrayList<String>();
    public static List<String> directorList = new ArrayList<String>();
    public static List<String> yearList = new ArrayList<String>();
    Context context;
    OnItemClickListener clickListener;


    public SimpleRecyclerAdapterFilmes(Context context, List<Filme> filmeList) {
        this.filmeList = filmeList;
        setLojaList(filmeList);
        this.context = context;
    }

    public void setLojaList(List<Filme> listFilme) {
        for (Filme filme : listFilme) {
            titleList.add(filme.getTitle());
            genreList.add(filme.getGenre());
            directorList.add(filme.getDirector());
            yearList.add(filme.getYear());
        }
    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item_filme, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        File fileRaiz = Environment.getExternalStorageDirectory();
        try {
            File imgFile = new File(fileRaiz.getPath() + "/EuAssisti/PostersFiles/" + titleList.get(i) + ".jpg");
            if (imgFile.exists()) {
                versionViewHolder.simpleDraweeView.setImageURI(Uri.fromFile(imgFile));
            }else{
                Log.i("ERRO DE IMAGEM","Imagem não encontrada!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        versionViewHolder.title.setText(titleList.get(i));
        versionViewHolder.year.setText(yearList.get(i));
        versionViewHolder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }


        });

    }

    @Override
    public int getItemCount() {
        return filmeList == null ? 0 : filmeList.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        SimpleDraweeView simpleDraweeView;
        TextView title;
        //TextView genre;
        //TextView director;
        TextView year;
        Button button;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_filme);
            title = (TextView) itemView.findViewById(R.id.listitem_title);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.listitem_sdview_poster);
            //genre =  (TextView) itemView.findViewById(R.id.listitem_title);
            //director =  (TextView) itemView.findViewById(R.id.listitem_title);
            year =  (TextView) itemView.findViewById(R.id.listitem_year);
            button = (Button) itemView.findViewById(R.id.button_excluir);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


}

