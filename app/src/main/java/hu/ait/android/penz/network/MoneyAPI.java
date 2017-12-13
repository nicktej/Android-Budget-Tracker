package hu.ait.android.penz.network;

import hu.ait.android.penz.data.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nicktan on 11/13/17.
 */

public interface MoneyAPI {

    @GET("latest")
    Call<MoneyResult> getRates(@Query("base") String base);

}
