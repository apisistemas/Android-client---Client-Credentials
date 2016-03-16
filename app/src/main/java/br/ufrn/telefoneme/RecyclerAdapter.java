package br.ufrn.telefoneme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Vinicius on 09/12/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<TelefoneDTO> dataSource;
    private static Activity activity;

    public RecyclerAdapter(List<TelefoneDTO> dataArgs, Activity a){
        dataSource = dataArgs;
        activity = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.numero.setText(dataSource.get(position).getNumero());
        holder.descricao.setText(dataSource.get(position).getDescricao());
        holder.localizacao.setText(dataSource.get(position).getLocalizacao());
        holder.setor.setText(dataSource.get(position).getSetor());
    }

    @Override
    public int getItemCount() {
        if(dataSource.isEmpty()) return 0;
        else return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView localizacao;
        public TextView setor;
        public TextView numero;
        public TextView descricao;
        ImageButton dial;
        ImageButton add;

        public ViewHolder(View view){
            super(view);
            localizacao = (TextView) view.findViewById(R.id.unidade);
            setor = (TextView) view.findViewById(R.id.setor);
            numero = (TextView) view.findViewById(R.id.number);
            descricao = (TextView) view.findViewById(R.id.descricao);
            dial = (ImageButton) view.findViewById(R.id.call);
            add = (ImageButton) view.findViewById(R.id.add);

            dial.setOnClickListener(new ImageButton.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + numero.getText()));
                    activity.startActivity(intent);
                }
            });

            add.setOnClickListener(new ImageButton.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                    intent.putExtra(ContactsContract.Intents.Insert.NAME, descricao.getText());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, numero.getText());

                    int PICK_CONTACT = 100;
                    activity.startActivityForResult(intent, PICK_CONTACT);
                }
            });
        }

    }

}
