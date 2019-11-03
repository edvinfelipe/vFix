package corporation.app.menchus.com.vfix.PackageProductos;

import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiProductos {

    @GET("api/productos/categoria/{id}/")
    Observable<List<ModeloProductos>> getProductos(@Path("id") String idCategoria);
}
