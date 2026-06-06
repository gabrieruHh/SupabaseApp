package com.gabriell.supabaseapp.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gabriell.supabaseapp.databinding.ActivityMainBinding;
import com.gabriell.supabaseapp.models.ProductModel;
import com.gabriell.supabaseapp.util.SupabaseClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private SupabaseClient supabaseClient = new SupabaseClient();
    private TextView textView;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buscarProduto();

    }

    private void buscarProduto() {
        supabaseClient.getProducts(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        binding.txtProducts.setText("Erro: " + e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String bory = response.body().string();

                // ✅ Adicione isso para depurar
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> binding.txtProducts.setText("Erro HTTP: " + response.code() + "\n" + bory));
                    return;
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<List<ProductModel>>(){}.getType();
                List<ProductModel> productModels = gson.fromJson(bory, listType);

                // ✅ Verificar se a lista veio vazia
                if (productModels == null || productModels.isEmpty()) {
                    runOnUiThread(() -> binding.txtProducts.setText("Nenhum produto encontrado."));
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (ProductModel p : productModels) {
                    sb.append(p.toString()).append("\n");
                }
                runOnUiThread(() -> binding.txtProducts.setText(sb.toString()));
            }
        });
    }
}