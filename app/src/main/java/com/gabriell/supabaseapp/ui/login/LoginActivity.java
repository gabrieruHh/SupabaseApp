package com.gabriell.supabaseapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gabriell.supabaseapp.databinding.ActivityLoginBinding;
import com.gabriell.supabaseapp.ui.cadastro.CadastroActivity;
import com.gabriell.supabaseapp.ui.main.MainActivity;
import com.gabriell.supabaseapp.ui.recuperarsenha.RecuperarSenhaActivity;
import com.gabriell.supabaseapp.util.SupabaseAuth;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SupabaseAuth auth = new SupabaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> fazerLogin());

        binding.txtEsqueceuSenha.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarSenhaActivity.class))
        );

        binding.txtIrCadastro.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class))
        );
    }

    private void fazerLogin() {
        String email = binding.edEmail.getText().toString().trim();
        String senha = binding.edSenha.getText().toString().trim();

        // Validações
        if (email.isEmpty()) {
            binding.layoutEmail.setError("Informe o e-mail");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.setError("E-mail inválido");
            return;
        }
        binding.layoutEmail.setError(null);

        if (senha.isEmpty()) {
            binding.layoutSenha.setError("Informe a senha");
            return;
        }
        if (senha.length() < 6) {
            binding.layoutSenha.setError("Senha deve ter pelo menos 6 caracteres");
            return;
        }
        binding.layoutSenha.setError(null);

        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Entrando...");

        auth.login(email, senha, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText("Entrar");
                    Toast.makeText(LoginActivity.this,
                            "Erro de conexão: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody != null ? responseBody.string() : "";

                runOnUiThread(() -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText("Entrar");

                    if (response.isSuccessful()) {
                        // Pega o token de acesso
                        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                        String token = json.get("access_token").getAsString();

                        Toast.makeText(LoginActivity.this,
                                "Login realizado!", Toast.LENGTH_SHORT).show();

                        // Vai para a MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "E-mail ou senha incorretos",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}