package com.example.SOOJOOB

import com.example.SOOJOOB.retrofit.BadgeService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

// RetrofitApi 객체는 비용이 높기 때문에 여러 객체가 만들어지면 자원낭비 및 통신에 혼선이 올 수 있기 때문에
// object 로 싱글턴으로 만들어준다.
object RetrofitAPI {
    private const val BASE_URL = "http://i7d210.p.ssafy.io:8080/"

    fun okHttpClient(interceptor : AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    fun retrofit(): Retrofit  {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(AppInterceptor())) // 로그캣에서 패킷 내용을 모니터링 할 수 있음 (인터셉터)
            .build()
    }

    val signUpService: SignUpService by lazy {
        retrofit().create(SignUpService::class.java)
    }

    val loginService: LoginService by lazy {
        retrofit().create(LoginService::class.java)
    }

    val badgeService: BadgeService by lazy {
        retrofit().create(BadgeService::class.java)
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = App.prefs.getString("X-AUTH-TOKEN", "")
            val newRequest = request().newBuilder()
                .addHeader("X-AUTH-TOKEN", accessToken)
                .build()
            proceed(newRequest)
        }
    }
    val ploggingService: PloggingService by lazy {
        retrofit().create(PloggingService::class.java)
    }

}