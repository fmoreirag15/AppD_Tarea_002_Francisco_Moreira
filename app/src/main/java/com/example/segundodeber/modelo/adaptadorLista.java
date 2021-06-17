package com.example.segundodeber.modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.segundodeber.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class adaptadorLista extends RecyclerView.Adapter<adaptadorLista.ViewHolder> {
    private List<modelos_revista> Data;
    private LayoutInflater myinflater;
    private Context context;

    public adaptadorLista(List<modelos_revista> itemList, Context context)
    {
        this.myinflater=LayoutInflater.from(context);
        this.context=context;
        this.Data=itemList;
    }
    @Override
    public  int getItemCount()
    { return Data.size();
    }
    @Override
    public adaptadorLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=myinflater.inflate(R.layout.lista_de_elementos, null);
        return new adaptadorLista.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final  adaptadorLista.ViewHolder holder, final int position)
    {
        holder.bindData(Data.get(position));

    }
    public  void  setItems(List<modelos_revista> items){Data=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageRevista;
        TextView titulo, volumen, data, doi, anio, cover;
        ViewHolder(View itemView)
        {
            super(itemView);
            imageRevista=itemView.findViewById(R.id.imagenViewlista);
            titulo=itemView.findViewById(R.id.txtTitulo);
            volumen=itemView.findViewById(R.id.Txtvolumen);
            data=itemView.findViewById(R.id.txtdate_published);
            doi=itemView.findViewById(R.id.txtDoi);
            anio=itemView.findViewById(R.id.Txtanio);

        }
        void bindData(final  modelos_revista item)
        {
            Picasso.get().load(item.getCover()).resize(100,100).centerCrop().into(imageRevista);
            titulo.setText(item.getTitle());
            volumen.setText(item.getVolume());
            data.setText(item.getDate_published());
            doi.setText(item.getDoi());
            anio.setText(item.getYear());
        }
    }


}
