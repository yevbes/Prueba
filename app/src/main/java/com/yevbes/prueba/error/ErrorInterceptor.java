package com.yevbes.prueba.error;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

// OKHttp Error Interceptor
public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            throw new TypicodeException(
                    response.code(),
                    response.message()
            );
        }
        return response;
    }
}
