package corporation.app.menchus.com.vfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import corporation.app.menchus.com.vfix.PackageCategoria.AdaptadorCategoria;
import corporation.app.menchus.com.vfix.PackageCategoria.ApiCategoria;
import corporation.app.menchus.com.vfix.PackageCategoria.ModeloGetCategorias;
import corporation.app.menchus.com.vfix.PackageProductos.ApiProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ConsultarProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ModeloProductos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productos extends AppCompatActivity {

    private Spinner sipnner,sp;
    private ArrayList<ListaProductos> listaProductos;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        sipnner = findViewById(R.id.idSpinner);
        recycler = findViewById(R.id.idrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));



        //AdapterProductos adapterProductos = new AdapterProductos(listaProductos);
        //recycler.setAdapter(adapterProductos);

        ArrayList<String> listaCategorias = new ArrayList();
        listaCategorias.add("Categorias");
        ConsultarProductos consultarProductos = new ConsultarProductos();
        ApiCategoria categorias = consultarProductos.getConsultaProductos().create(ApiCategoria.class);
        Call<List<ModeloGetCategorias>> modeloCategorias = categorias.getCategorias();
        AdaptadorCategoria adaptadorCategoria = new AdaptadorCategoria(modeloCategorias,listaCategorias);
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(this,R.layout.spinner_item_categoria,
                adaptadorCategoria.getCategorias());
        sipnner.setAdapter(adaptadorSpinner);
        List<ModeloGetCategorias> adaptadorCategorias = adaptadorCategoria.getCategoriasObtenidas();

        OnItemSelectedListener seleccionCategoria = new SpinnerActivity(consultarProductos.getConsultaProductos(),
                recycler,adaptadorCategoria.getCategoriasObtenidas());
        seleccionCategoria.onItemSelected(sipnner,sipnner.getSelectedView(),
                sipnner.getSelectedItemPosition(),sipnner.getSelectedItemId());

        sipnner.setOnItemSelectedListener(seleccionCategoria);

        /*Retrofit llamarACategorias = new Retrofit.Builder()
                .baseUrl("http://carlosmenchu2.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        /*ArrayList<String> arreglo = new ArrayList<>();
        arreglo.add("Seleccione");
        ApiCategoria categorias = llamarACategorias.create(ApiCategoria.class);
        Call<List<ModeloGetCategorias>> llamarCategorias =categorias.getCategorias();
        AdaptadorCategoria adaptadorCategoria = new AdaptadorCategoria(llamarCategorias,arreglo);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                R.layout.spinner_item_categoria,adaptadorCategoria.getCategorias());*/


        /*ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                R.layout.spinner_item_categoria,arreglo);*/

        /*sipnner.setAdapter(adapter);*/
        /*OnItemSelectedListener activiti = new SpinnerActivity();
        activiti.onItemSelected(sipnner,sipnner.getSelectedView(),sipnner.getSelectedItemPosition(),
                sipnner.getSelectedItemId());
        sipnner.setOnItemSelectedListener(activiti);*/
    }
    private void llenarDatos(){

        String url = "https://www.heb.com.mx/media/catalog/product/cache/20/image/d954a60aa48ef57022b0eb878e93bc1f/T/e/IMV-006_766884155.png";
        listaProductos=new ArrayList();
        listaProductos.add(new ListaProductos(url,"Samsung Galaxy S8",5));
        listaProductos.add(new ListaProductos(url,"Samsung Galaxy S7",5));
        listaProductos.add(new ListaProductos(url,"Samsung Galaxy S6",5));
        listaProductos.add(new ListaProductos(url,"Samsung Galaxy S5",5));
        listaProductos.add(new ListaProductos(url,"Iphone XS MAX PRO",5));
        listaProductos.add(new ListaProductos(url,"USB HP 5GB",5));
        listaProductos.add(new ListaProductos(url,"USB HP 8GB",5));
        listaProductos.add(new ListaProductos(url,"USB HP 16GB",5));
        listaProductos.add(new ListaProductos(url,"USB HP 32GB",5));
        listaProductos.add(new ListaProductos(url,"HP 12w",5));
        listaProductos.add(new ListaProductos(url,"DELL 5w",5));
        listaProductos.add(new ListaProductos(url,"MSI 10W",5));
    }
}
