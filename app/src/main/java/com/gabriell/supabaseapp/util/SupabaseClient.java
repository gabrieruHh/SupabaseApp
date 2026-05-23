package com.gabriell.supabaseapp.util;

import okhttp3.*;
import java.io.IOException;
import java.net.PortUnreachableException;

public class SupabaseClient {

    private static final String SUPABASE_URL = "https://qyvlhxdvodnvnogklvxl.supabase.co";
    private static final String ANON_KEY = "sb_publishable_GxPaogInirJuioUKOLJzbQ_zlHMxTo_";

    private final OkHttpClient client = new OkHttpClient();

    //BUSCAR TODOS OS PRODUTOS (GET)
    public void getProducts(Callback callback){
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "rest/v1/produtos?select=*")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Authorization", "Bearer " + ANON_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
