package corporation.app.menchus.com.vfix.PackageProductos;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultarProductos {

    private Retrofit consultaProductos;
    private List<ModeloProductos> productos;

    public  ConsultarProductos(){
    }

    public Retrofit getConsultaProductos(){

        return consultaProductos = new Retrofit.Builder()
                .baseUrl("http://carlosmenchu2.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
