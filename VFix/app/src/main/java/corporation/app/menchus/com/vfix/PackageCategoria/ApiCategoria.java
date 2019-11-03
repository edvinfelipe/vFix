package corporation.app.menchus.com.vfix.PackageCategoria;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCategoria {

    @GET("api/categorias/")
    //Call<List<ModeloGetCategorias>> getCategorias();
    Observable<List<ModeloGetCategorias>> getCategorias();
}
