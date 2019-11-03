package corporation.app.menchus.com.vfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import corporation.app.menchus.com.vfix.PackageCategoria.ApiCategoria;
import corporation.app.menchus.com.vfix.PackageCategoria.ModeloGetCategorias;
import corporation.app.menchus.com.vfix.PackageConexion.RetrofitClient;
import corporation.app.menchus.com.vfix.PackageProductos.AdapterProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ApiProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ModeloProductos;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productos extends AppCompatActivity {


    private Spinner sipnner;
    private RecyclerView recycler;
    private CompositeDisposable mCompositeDisposable;
    private ArrayAdapter<String> adaptadorSpinner;
    private List<ModeloGetCategorias> categorias;
    private List<ModeloProductos> modeloProductos;
    private AdapterProductos adapterProductos;
    private ArrayList<String> listacategorias;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        sipnner = findViewById(R.id.idSpinner);
        recycler = findViewById(R.id.idrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mCompositeDisposable = new CompositeDisposable();

        retrofit = RetrofitClient.getInstance();
        ApiCategoria apiCategoria = retrofit.create(ApiCategoria.class);

        mCompositeDisposable.add(apiCategoria.getCategorias().observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse,this::handleError));



        sipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(categorias!=null) {
                    mCompositeDisposable.clear();
                    retrofit= RetrofitClient.getInstance();
                    ApiProductos apiProductos = retrofit.create(ApiProductos.class);
                    apiProductos.getProductos(String.valueOf(position+1)).observeOn(AndroidSchedulers.mainThread()).
                            subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponseP,this::handleErrorP);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            private void handleResponseP(List<ModeloProductos> modeloProductos){

                modeloProductos = new ArrayList<>(modeloProductos);
                adapterProductos= new AdapterProductos(modeloProductos);
                recycler.setAdapter(adapterProductos);
            }

            private void handleErrorP(Throwable error){
                System.out.println("Error "+error.getLocalizedMessage());
            }
        });

    }

    private void handleResponse(List<ModeloGetCategorias> categorias){

        this.categorias = new ArrayList<>(categorias);
        this.listacategorias=new ArrayList();
        for(ModeloGetCategorias modelo : this.categorias){
            //System.out.println("ID: "+modelo.getId()+ " Nombre: "+modelo.getNombre());
            listacategorias.add(modelo.getNombre());
        }
        adaptadorSpinner = new ArrayAdapter<>(this,R.layout.spinner_item_categoria,listacategorias);
        sipnner.setAdapter(adaptadorSpinner);

    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    public void estatus(View view){

        if(categorias!=null){
            System.out.println("Nombre "+ categorias.get(0).getNombre());
        }
    }





}
