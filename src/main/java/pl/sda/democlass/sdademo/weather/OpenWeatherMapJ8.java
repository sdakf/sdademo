package pl.sda.democlass.sdademo.weather;

import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface OpenWeatherMapJ8 {
    @GET("data/2.5/weather")
    CompletableFuture<WeatherResult> currentByCity(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String language
    );

    @GET("data/2.5/weather")
    CompletableFuture<WeatherResult> currentByCoords(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String language
    );
}
