package com.gabriell.supabaseapp.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gabriell.supabaseapp.adapters.ProdutoAdapter;
import com.gabriell.supabaseapp.databinding.ActivityMainBinding;
import com.gabriell.supabaseapp.models.ProductModel;
import com.gabriell.supabaseapp.util.SupabaseClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private SupabaseClient supabaseClient = new SupabaseClient();
    private ProdutoAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarRecyclerView();
        buscarProduto();

        binding.btnAdd.setOnClickListener(v -> inserirProduto());
    }

    private void configurarRecyclerView() {
        adapter = new ProdutoAdapter(new ArrayList<>(), produto -> {
            // ✅ Dialog de confirmação ao segurar o item
            new AlertDialog.Builder(this)
                    .setTitle("Deletar produto")
                    .setMessage("Deseja deletar \"" + produto.getName() + "\"?")
                    .setPositiveButton("Deletar", (dialog, which) -> {
                        deletarProduto(produto.getId());
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void deletarProduto(String id) {
        supabaseClient.deletarProduto(id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this,
                                "Erro ao deletar: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody != null ? responseBody.string() : "";

                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this,
                                "Produto deletado!", Toast.LENGTH_SHORT).show();
                        buscarProduto(); // ✅ recarrega a lista
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Erro " + response.code() + ": " + body,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void buscarProduto() {
        supabaseClient.getProducts(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this,
                                "Erro de conexão: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody != null ? responseBody.string() : "";

                if (!response.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this,
                                    "Erro " + response.code() + ": " + body,
                                    Toast.LENGTH_LONG).show()
                    );
                    return;
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<List<ProductModel>>(){}.getType();
                List<ProductModel> produtos = gson.fromJson(body, listType);

                runOnUiThread(() -> {
                    if (produtos == null || produtos.isEmpty()) {
                        Toast.makeText(MainActivity.this,
                                "Nenhum produto encontrado",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.atualizarLista(produtos); // ✅ atualiza o RecyclerView
                    }
                });
            }
        });
    }

    private void inserirProduto() {
        String nomeProduto = binding.edItemNome.getText().toString().trim();
        String precoStr    = binding.edItemPreco.getText().toString().trim();

        if (nomeProduto.isEmpty()) {
            binding.edItemNome.setError("Informe o nome do produto");
            return;
        }
        if (precoStr.isEmpty()) {
            binding.edItemPreco.setError("Informe o preço");
            return;
        }

        double precoProduto;
        try {
            precoProduto = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            binding.edItemPreco.setError("Preço inválido");
            return;
        }

        supabaseClient.insertnewProduct(nomeProduto, precoProduto, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this,
                                "Erro de conexão: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody != null ? responseBody.string() : "";

                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        binding.edItemNome.setText("");
                        binding.edItemPreco.setText("");
                        Toast.makeText(MainActivity.this,
                                "Produto inserido!", Toast.LENGTH_SHORT).show();
                        buscarProduto(); // ✅ recarrega a lista
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Erro " + response.code() + ": " + body,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}