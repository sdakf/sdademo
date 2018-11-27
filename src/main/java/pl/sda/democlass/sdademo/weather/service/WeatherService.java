package pl.sda.democlass.sdademo.weather.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.democlass.sdademo.users.User;
import pl.sda.democlass.sdademo.users.UserContextHolder;
import pl.sda.democlass.sdademo.users.UsersService;
import pl.sda.democlass.sdademo.weather.OpenWeatherMapJ8;
import pl.sda.democlass.sdademo.weather.WeatherResult;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {
    private Gson gson = new Gson();
    private UserContextHolder userContextHolder;
    private UsersService usersService;

    private String key = "ea900b66f547fd7b23625544873a4200";

    @Autowired
    public WeatherService(UserContextHolder userContextHolder, UsersService usersService) {
        this.userContextHolder = userContextHolder;
        this.usersService = usersService;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(Java8CallAdapterFactory.create())
            .build();

    private OpenWeatherMapJ8 openWeatherMapJ8 =
            retrofit.create(OpenWeatherMapJ8.class);

    public String getWeather() {

        String city = null;
        String country = null;

        String userEmail = userContextHolder.getUserLoggedIn();
        User user = usersService.getUserByEmail(userEmail).orElse(null);
        //todo 8 - należy uzupełnić wartości city i country w zależności od tego czy użytkownik jest zalogowany czy nie
        if (user == null) {
            city = "Warszawa";
            country = "pl";
        } else {
            city = user.getUserAddress().getCity();
            country = user.getUserAddress().getCountry();
        }
        String cityWithCountry = city + "," + country.toUpperCase();
        CompletableFuture<WeatherResult> forecast = openWeatherMapJ8.currentByCity(cityWithCountry, key, "metric", "pl");

        return downloadWeather(forecast);
    }

    private String downloadWeather(CompletableFuture<WeatherResult> forecast) {
        return forecast
                .thenApplyAsync(pogoda -> gson.toJson(pogoda))
                .exceptionally(throwable -> {
                    throw new RuntimeException("Błąd");
                }).join();
    }
}