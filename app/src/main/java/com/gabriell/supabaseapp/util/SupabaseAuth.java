package com.gabriell.supabaseapp.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SupabaseAuth {
    private static final String SUPABASE_URL = "https://qyvlhxdvodnvnogklvxl.supabase.co";
    private static final String ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF5dmxoeGR2b2Rudm5vZ2tsdnhsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg2MjQwNjIsImV4cCI6MjA5NDIwMDA2Mn0.QvbQ6RUbxHVtIfaBKre_f1Jp5F_ieieNRRX4WNR6irw";
    private static OkHttpClient client = new OkHttpClient();

    public void cadastrar(String email, String senha, Callback callback){
        String json =  "{\"email\":\"" + email +   "\",\"password\":\"" + senha + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "auth/v1/singup")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // LOGIN
    public void login(String email, String senha, Callback callback) {
        String json = "{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/auth/v1/token?grant_type=password")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // RECUPERAR SENHA
    public void recuperarSenha(String email, Callback callback) {
        String json = "{\"email\":\"" + email + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/auth/v1/recover")
                .addHeader("apikey", ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


}
