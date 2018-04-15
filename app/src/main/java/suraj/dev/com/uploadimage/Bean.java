package suraj.dev.com.uploadimage;

import com.google.gson.annotations.SerializedName;

public class Bean {

@SerializedName("title")
private String Title;

@SerializedName("image")
private String Image;

@SerializedName("response")
 private String Response;

    public String getResponse() {
        return Response;
    }
}
