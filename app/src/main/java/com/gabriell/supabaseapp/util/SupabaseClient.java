package com.gabriell.supabaseapp.util;

import android.os.Build;

import okhttp3.*;
import java.io.IOException;
import java.net.PortUnreachableException;

public class SupabaseClient {

    private static final String SUPABASE_URL = "https://qyvlhxdvodnvnogklvxl.supabase.co";
    private static final String ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF5dmxoeGR2b2Rudm5vZ2tsdnhsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg2MjQwNjIsImV4cCI6MjA5NDIwMDA2Mn0.QvbQ6RUbxHVtIfaBKre_f1Jp5F_ieieNRRX4WNR6irw";

    private final OkHttpClient client = new OkHttpClient();

    // GET ALL PRODUCTS
    public void getProducts(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/produtos?select=*")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Authorization", "Bearer " + ANON_KEY) // ✅ espaço
                .build();

        client.newCall(request).enqueue(callback);
    }

    // INSERT NEW PRODUCT
    public void insertnewProduct(String name, double price, Callback callback) {
        String json = "{\"nome\":\"" + name + "\",\"preco\":" + price + "}";

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/produtos") // ✅ barra adicionada
                .addHeader("apikey", ANON_KEY)
                .addHeader("Authorization", "Bearer " + ANON_KEY) // ✅ espaço
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // DELETE PRODUCT
    public void deletarProduto(String id, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/produtos?id=eq." + id)
                .addHeader("apikey", ANON_KEY)
                .addHeader("Authorization", "Bearer " + ANON_KEY) // ✅ espaço
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }
}
