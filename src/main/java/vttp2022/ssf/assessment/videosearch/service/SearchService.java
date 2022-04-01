package vttp2022.ssf.assessment.videosearch.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.assessment.videosearch.models.Game;

@Service
public class SearchService {

    private static final String URL = "https://api.rawg.io/api/games";

    // export RAWG_API_KEY=12c0f38ee89a4e18b5fffae90f05c7b9
    @Value("${rawg.api.key}")
    private String apiKey;

    private boolean hasKey;



    @PostConstruct
    private void init() {
        hasKey = null != apiKey;
        System.out.println(">>>> API key set: " + hasKey);
    }

    RequestEntity<Void> req = RequestEntity
            .get(URL)
            .accept(MediaType.APPLICATION_JSON)
            .build();

    public List<Game> search (String searchString, Integer count){
        String gameSearch = UriComponentsBuilder.fromUriString(URL)
            .queryParam("search", searchString)
            .queryParam("page_size", count)
            .queryParam("key", apiKey)
            .toUriString();

        RequestEntity req = RequestEntity
                .get(gameSearch)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        List<Game> gameList = new ArrayList<>();
        
        try {
        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject obj = reader.readObject();

        Game game = new Game();
        game.setName(obj.getString("name"));
        //game.setRating(obj.getString("rating"));
        game.setBackgroundImage(obj.getString("background_image"));

        gameList.add(game);
        } catch (Exception ex) {
            System.err.printf("Exception:", ex.getMessage());
            //return gameList.emmpty();

        }
        
        return gameList;
        
    }

}
