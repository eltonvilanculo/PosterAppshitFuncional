package com.example.nameless.posterappshit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nameless on 5/21/2018.
 */

public class ModeloDadosRecycler {

    private  int idImagem ;
    private User usuario ;


    public ModeloDadosRecycler() {
    }

    public ModeloDadosRecycler(int idImagem, User usuario) {
        this.idImagem = idImagem;
        this.usuario = usuario;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public static  int[] getImagens (){

       int [] listaImagens = {
                R.drawable.image1



       };

    return listaImagens;
    }

    public static List<ModeloDadosRecycler> getListaDeObjectos (){
        List<ModeloDadosRecycler> listaUsuarios = new ArrayList<>();
        int [] imagens = getImagens();


        for (int i = 0; i <imagens.length ; i++) {


            ModeloDadosRecycler a = new ModeloDadosRecycler();
           // a.setIdImagem();
            //a.setUsuario();
            listaUsuarios.add(a);



        }




        return listaUsuarios;
    }
}
