package suraj.dev.com.uploadimage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {

@FormUrlEncoded
@POST("upload.php")
    Call<Bean> uploadImage(@Field("title") String title, @Field("image") String image);
}
