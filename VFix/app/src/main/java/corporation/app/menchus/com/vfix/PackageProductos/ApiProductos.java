package corporation.app.menchus.com.vfix.PackageProductos;

import retrofit2.Call;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiProductos {

    @GET("api/productos/")
    Call<List<ModeloProductos>> getProductos(@Query("idCategoria")String idCategoria);
}
