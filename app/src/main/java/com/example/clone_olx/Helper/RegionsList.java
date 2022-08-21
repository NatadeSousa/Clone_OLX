package com.example.clone_olx.Helper;

import java.util.ArrayList;
import java.util.List;

public class RegionsList {

    public static List<String> getRegions(String uf){

        List<String> regionsList = new ArrayList<>();

        regionsList.add("Todas as Regiões");
        switch (uf) {
            case "AC":
                regionsList.add("DDD 68 - Acre");
                break;
            case "AL":
                regionsList.add("DDD 82 - Alagoas");
                break;
            case "AP":
                regionsList.add("DDD 96 - Amapá");
                break;
            case "AM":
                regionsList.add("DDD 92 - Amazonas");
                regionsList.add("DDD 97 - Leste do Amazonas");
                break;
            case "BA":
                regionsList.add("DDD 71 - Salvador");
                regionsList.add("DDD 73 - Sul da Bahia");
                regionsList.add("DDD 74 - Juazeiro, Jacobina e região");
                regionsList.add("DDD 75 - F. de Santana, Alagoinhas e região");
                regionsList.add("DDD 77 - V da Conquista, Barreiras e região");
                break;
            case "CE":
                regionsList.add("DDD 85 - Fortaleza e região");
                regionsList.add("DDD 88 - Juazeiro do Norte, Sobral e região");
                break;
            case "DF":
                regionsList.add("DDD 61 - Distrito Federal e região");
                break;
            case "ES":
                regionsList.add("DDD 27 - Norte do Espírito Santo");
                regionsList.add("DDD 28 - Sul do Espírito Santo");
                break;
            case "GO":
                regionsList.add("DDD 62 - Grande Goiânia e Anápolis");
                regionsList.add("DDD 64 - Rio Verde, Caldas Novas e região");
                break;
            case "MA":
                regionsList.add("DDD 98 - Região de São Luíz");
                regionsList.add("DDD 99 - Imperatriz, Caxias e região");
                break;
            case "MT":
                regionsList.add("DDD 65 - Cuiabá e região");
                regionsList.add("DDD 66 - Rondonópolis, Sinop e região");
                break;
            case "MS":
                regionsList.add("DDD 67 - Mato Grosso do Sul");
                break;
            case "MG":
                regionsList.add("DDD 31 - Belo Horizonte e região");
                regionsList.add("DDD 32 - Juiz de Fora e região");
                regionsList.add("DDD 33 - Gov. Valadares, T. Otoni e região");
                regionsList.add("DDD 34 - Uberlândia, Uberaba e região");
                regionsList.add("DDD 35 - Poços de Caldas e Varginha");
                regionsList.add("DDD 37 - Divinópolis e região");
                regionsList.add("DDD 38 - Mtes Claros, Diamantina e região");
                break;
            case "PA":
                regionsList.add("DDD 91 - Região de Belém");
                regionsList.add("DDD 93 - Região de Santarém");
                regionsList.add("DDD 94 - Região de Marabá");
                break;
            case "PB":
                regionsList.add("DDD 83 - Paraíba");
                break;
            case "PR":
                regionsList.add("DDD 41 - Curitiba e região");
                regionsList.add("DDD 42 - Pta Grossa, Guarapuava e região");
                regionsList.add("DDD 43 - Londrina e região");
                regionsList.add("DDD 44 - Maringá e região");
                regionsList.add("DDD 45 - Foz do Iguaçu, Cascavel e região");
                regionsList.add("DDD 46 - F. Beltrão e Pato Branco e região");
                break;
            case "PE":
                regionsList.add("DDD 81 - Grande Recife");
                regionsList.add("DDD 87 - Petrolina, Garanhuns e região");
                break;
            case "PI":
                regionsList.add("DDD 86 - Teresina, Parnaíba e região");
                regionsList.add("DDD 89 - Picos, Floriano e região");
                break;
            case "RJ":
                regionsList.add("DDD 21 - Rio de Janeiro e região");
                regionsList.add("DDD 22 - Norte do Estado e Região dos Lagos");
                regionsList.add("DDD 24 - Serra, Angra dos Reis e região");
                break;
            case "RN":
                regionsList.add("DDD 84 - Rio Grande do Norte");
                break;
            case "RS":
                regionsList.add("DDD 51 - Porto Alegre e região");
                regionsList.add("DDD 53 - Pelotas, Bagé, Rio Gde e região");
                regionsList.add("DDD 54 - Caxias do Sul e região");
                regionsList.add("DDD 55 - Sta Maria, Cruz Alta e região");
                break;
            case "RO":
                regionsList.add("DDD 69 - Rondônia");
                break;
            case "RR":
                regionsList.add("DDD 96 - Roraima");
                break;
            case "SC":
                regionsList.add("DDD 47 - Norte de Santa Catarina");
                regionsList.add("DDD 48 - Florianópolis e região");
                regionsList.add("DDD 49 - Oeste de Santa Catarina");
                break;
            case "SP":
                regionsList.add("DDD 11 - São Paulo e região");
                regionsList.add("DDD 12 - V. do Paraíba e Litoral Norte");
                regionsList.add("DDD 13 - Baixada Santista e Litoral Sul");
                regionsList.add("DDD 14 - Bauru, Marília e região");
                regionsList.add("DDD 15 - Sorocaba e região");
                regionsList.add("DDD 16 - Ribeirão Preto e região");
                regionsList.add("DDD 17 - S. José do Rio Preto e região");
                regionsList.add("DDD 18 - Presidente Prudente e região");
                regionsList.add("DDD 19 - Grande Campinas");
                break;
            case "SE":
                regionsList.add("DDD 79 - Sergipe");
                break;
            case "TO":
                regionsList.add("DDD 63 - Tocantins");
                break;
        }

        return regionsList;
    }

}
