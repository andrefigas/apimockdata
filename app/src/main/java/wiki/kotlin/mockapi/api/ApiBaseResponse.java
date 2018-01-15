package wiki.kotlin.mockapi.api;

import com.google.gson.annotations.SerializedName;

public class ApiBaseResponse<Response> {

    @SerializedName("errorCode") private int mErrorCode;

    @SerializedName("message") private String mMessage;

    @SerializedName("response") private Response mResponse;

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public Response getResponse() {
        return mResponse;
    }
}
