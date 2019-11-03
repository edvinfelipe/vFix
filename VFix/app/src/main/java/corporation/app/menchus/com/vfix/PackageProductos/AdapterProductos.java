package corporation.app.menchus.com.vfix.PackageProductos;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import corporation.app.menchus.com.vfix.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterProductos  extends RecyclerView.Adapter<AdapterProductos.ViewHolderProductos> {

    private List<ModeloProductos> listaProductos;
    private String url = "http://icris17.pythonanywhere.com";

    public AdapterProductos(List<ModeloProductos> listaProductos) {

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
                listaProductos.get(position).getImagenes().get(0).getImagen());
        System.out.println("Posicion:" +listaProductos.get(position).getImagenes().get(0).getImagen());

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

           /*Picasso.get().load("http://icris17.pythonanywhere.com/media/apple.jpg").
                   resize(50,50).
                   centerCrop().
                   into(circleImageView);*/
           Glide.with(circleImageView).load(url+imagen).into(circleImageView);
           System.out.println("Ruta: "+url+imagen);
           dato.setText(nombre);
           cantidad.setText("Existencia: "+existencia+" Unidades   ");
        }

    }

}
