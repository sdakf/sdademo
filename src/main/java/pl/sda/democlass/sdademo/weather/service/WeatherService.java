package pl.sda.democlass.sdademo.weather.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.sda.democlass.sdademo.users.User;
import pl.sda.democlass.sdademo.users.UserContextHolder;
import pl.sda.democlass.sdademo.users.UsersDao;
import pl.sda.democlass.sdademo.weather.OpenWeatherMapJ8;
import pl.sda.democlass.sdademo.weather.WeatherResult;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {
    private static Gson gson = new Gson();
    private UserContextHolder userContextHolder;
    private UsersDao usersDao;

    private String key = "ea900b66f547fd7b23625544873a4200";

    @Autowired
    public WeatherService(UserContextHolder userContextHolder,UsersDao usersDao) {
        this.userContextHolder = userContextHolder;
        this.usersDao = usersDao;
    }

    public String getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        OpenWeatherMapJ8 openWeatherMapJ8 =
                retrofit.create(OpenWeatherMapJ8.class);

        Optional<User> userByUsername = usersDao.getUserByUsername(userContextHolder.getUserLoggedIn());
        CompletableFuture<WeatherResult> forecast1 = openWeatherMapJ8.currentByCity(
                userByUsername.map(e -> e.getUserAddress().getCity() + "," + e.getUserAddress().getCountry().toUpperCase()).orElse(""),
                key,
                "metric",
                "pl"
        );

        return downloadWeather(forecast1);
    }

    private String downloadWeather(CompletableFuture<WeatherResult> forecast) {
        return forecast
                .thenApplyAsync(pogoda -> gson.toJson(pogoda))
                .exceptionally(throwable -> {
                    throw new RuntimeException("Błąd");
                }).join();

    }
}