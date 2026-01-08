package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.util.StringUtils.replace;

@Component
public class WeatherService {
    public static final String apiKey= "b1fe1de7cee917abdf20421017579ab9";


//    @Value("{weather.api.key}")
//    public String apiKey;

//    public static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    //http://api.weatherstack.com/current?access_key=b1fe1de7cee917abdf20421017579ab9&query=Mumbai

    @Autowired
    AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city)
    {
        String finalAPI =appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY,city).replace(PlaceHolders.API_KEY,apiKey);
        ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null,WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;

    }

//    public WeatherResponse addWeather(String city)
//    {
//        String finalAPI =API.replace("CITY",city).replace("API_KEY",apiKey);
//        HttpEntity<>
//        ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.POST,null,WeatherResponse.class);
//        WeatherResponse body = response.getBody();
//        return body;
//
//    }
}
