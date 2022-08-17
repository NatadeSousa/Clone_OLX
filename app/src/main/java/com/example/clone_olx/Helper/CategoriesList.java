package com.example.clone_olx.Helper;

import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesList {

    public static List<Categories> getList(boolean all){

        List<Categories> categoriesList = new ArrayList<>();

        if(all) categoriesList.add(new Categories(R.drawable.ic_todas_as_categorias, "Todas as Categorias"));
        categoriesList.add(new Categories(R.drawable.ic_autos_e_pecas, "Autos e Peças"));
        categoriesList.add(new Categories(R.drawable.ic_imoveis, "Imóveis"));
        categoriesList.add(new Categories(R.drawable.ic_eletronico_e_celulares, "Eletrônicos e Celulares"));
        categoriesList.add(new Categories(R.drawable.ic_para_a_sua_casa, "Para a sua casa"));
        categoriesList.add(new Categories(R.drawable.ic_moda_e_beleza, "Moda e Beleza"));
        categoriesList.add(new Categories(R.drawable.ic_esporte_e_lazer, "Esporte e Lazer"));
        categoriesList.add(new Categories(R.drawable.ic_musica_e_hobbies, "Música e Hobbies"));
        categoriesList.add(new Categories(R.drawable.ic_artigos_infantis, "Artigos Infantis"));
        categoriesList.add(new Categories(R.drawable.ic_animais_de_estimacao, "Animais de Estimação"));
        categoriesList.add(new Categories(R.drawable.ic_agro_e_industria, "Agro e Indústria"));
        categoriesList.add(new Categories(R.drawable.ic_comercio_e_escritorio, "Comércio e Escritório"));
        categoriesList.add(new Categories(R.drawable.ic_book, "Livros"));
        categoriesList.add(new Categories(R.drawable.ic_servicos, "Serviços"));
        categoriesList.add(new Categories(R.drawable.ic_vagas_de_emprego, "Vagas de Emprego"));

        return categoriesList;
    }

}
