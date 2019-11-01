package corporation.app.menchus.com.vfix.PackageProductos;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import corporation.app.menchus.com.vfix.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterProductos  extends RecyclerView.Adapter<AdapterProductos.ViewHolderProductos> {

    private ArrayList<String> listaDatos;
    private List<ModeloProductos> listaProductos;
    private String url = "http://carlosmenchu2.pythonanywhere.com/api";

    public AdapterProductos(List<ModeloProductos> listaProductos) {
        //this.listaDatos = listaDatos;
        this.listaProductos=listaProductos;
    }

    @Override
    public ViewHolderProductos onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listaproductos,null,false);

        return new ViewHolderProductos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductos holder, int position) {

        holder.asignarDatos(listaProductos.get(position).getNombre(),
                listaProductos.get(position).getExistencia(),
                listaProductos.get(position).getImagenes().getImagen());

    }

    @Override
    public int getItemCount() {
        return this.listaProductos.size();
    }

    public class ViewHolderProductos extends RecyclerView.ViewHolder {

        private TextView dato;
        private TextView cantidad;
        private CircleImageView circleImageView;

        public ViewHolderProductos(View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.idDato);
            cantidad= itemView.findViewById(R.id.idCantidad);
            circleImageView = itemView.findViewById(R.id.imagen);
        }

        public void asignarDatos(String nombre,int existencia, String imagen){

           Picasso.get().load(url+imagen).into(circleImageView);
           dato.setText(nombre);
           cantidad.setText("Existencia: "+String.valueOf(existencia));
        }

    }

}
