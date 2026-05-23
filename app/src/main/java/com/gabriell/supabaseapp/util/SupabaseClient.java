package com.gabriell.supabaseapp.util;

import okhttp3.*;
import java.io.IOException;
import java.net.PortUnreachableException;

public class SupabaseClient {

    private static final String SUPABASE_URL = "https://qyvlhxdvodnvnogklvxl.supabase.co";
    private static final String ANON_KEY = "sb_publishable_GxPaogInirJuioUKOLJzbQ_zlHMxTo_";

    private final OkHttpClient client = new OkHttpClient();

    //GET ALL PRODUCTS (GET)
    public void getProducts(Callback callback){
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "rest/v1/produtos?select=*")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Authorization", "Bearer " + ANON_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }

    //INSERT NEW PRODUCT(POST)
    public void insertnewProduct(String name, double price, Callback callback){

        //CRIAÇÃO DO CORPO DE REQUISIÇÃO EM FORMATO JSON
        String json = "{\"nome\":\"" + name + "\",\"preco\":\"" + price + "}";

        //CRIA O CORPO DA REQUISIÇÃO HTTP DIZENDO QUE O CONTEUDO É DO TIPO JSON
        RequestBody bory = RequestBody.create(
                json, MediaType.parse("aplication/json")
        );

        //INICIA A CONTRUÇÃO DA REQUISIÇAÕ HTTP
        Request request = new Request.Builder()
                //Define a URL de destino. SUPABASE_URL é uma constante com a URL base do seu
                // projeto no Supabase, e /rest/v1/produtos é o endpoint da tabela produtos.
                .url(SUPABASE_URL + "rest/v1/produtos")
                //Envia a chave pública do Supabase para identificar o projeto.
                .addHeader("apikey", ANON_KEY)
                //Cabeçalho de autenticação. O Bearer indica que é um token do tipo JWT, exigido pelo Supabase para autorizar a operação.
                .addHeader("Authorization", "Barear" + ANON_KEY)
                //INFORMA O SERVIDOR QUE O CORPO ENVIADO ESTÁ EM FOMATO JSON
                .addHeader("Content-Type", "application/json")
                //Cabeçalho de autenticação. O Bearer indica que é um token do tipo JWT, exigido pelo Supabase para autorizar a operação.
                .addHeader("Prefer", "return=representation")
                .post(bory).build();

        client.newCall(request).enqueue(callback);
    }
}
