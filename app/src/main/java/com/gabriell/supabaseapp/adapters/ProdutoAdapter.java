package com.gabriell.supabaseapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabriell.supabaseapp.R;
import com.gabriell.supabaseapp.models.ProductModel;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    public interface OnItemLongClickListener {
        void onLongClick(ProductModel produto);
    }

    private List<ProductModel> lista;
    private OnItemLongClickListener listener;

    public ProdutoAdapter(List<ProductModel> lista, OnItemLongClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void atualizarLista(List<ProductModel> novaLista) {
        this.lista = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        ProductModel produto = lista.get(position);
        holder.txtNome.setText(produto.getName());
        holder.txtPreco.setText("R$ " + String.format("%.2f", produto.getPrice()));

        // ✅ Segura o item para deletar
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(produto);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtPreco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome  = itemView.findViewById(R.id.txtNome);
            txtPreco = itemView.findViewById(R.id.txtPreco);
        }
    }
}
