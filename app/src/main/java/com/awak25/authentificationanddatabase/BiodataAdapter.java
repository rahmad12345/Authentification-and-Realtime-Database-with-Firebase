package com.awak25.authentificationanddatabase;
//mport android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awak25.authentificationanddatabase.BiodataModel;
import com.awak25.authentificationanddatabase.R;

import java.util.List;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ViewHolder> {

    List<BiodataModel> biodataList;
    Context context;

    public BiodataAdapter(List<BiodataModel> biodataList, Context context) {
        this.biodataList = biodataList;
        this.context = context;
    }

    @NonNull
    @Override
    public BiodataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_biodata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiodataAdapter.ViewHolder holder, final int position) {

        holder.tvNama.setText(biodataList.get(position).getNama());
        holder.tvAlamat.setText(biodataList.get(position).getAlamat());
        holder.tvPendidikan.setText(biodataList.get(position).getPendidikan());
        holder.tvGender.setText(biodataList.get(position).getGender());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdateDeleteActivity.class);
                intent.putExtra(UpdateDeleteActivity.TABLE_BIODATA, biodataList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return biodataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNama, tvAlamat, tvGender, tvPendidikan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.item_nama);
            tvAlamat = itemView.findViewById(R.id.item_alamat);
            tvGender = itemView.findViewById(R.id.item_gender);
            tvPendidikan = itemView.findViewById(R.id.item_pendidikan);
        }
    }
}
