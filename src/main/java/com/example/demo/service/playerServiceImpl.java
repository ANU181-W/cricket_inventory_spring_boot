package com.example.demo.service;

import com.example.demo.dao.doaImpl;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.PlayerStats;
import com.example.demo.model.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.GeminiRequest;
import com.example.demo.model.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class playerServiceImpl implements playerService{
    @Value("${gemini.api.key}")
    private String apiKeyGemini;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private doaImpl dbManager;
@Autowired
    public playerServiceImpl(doaImpl dbManager){
        this.dbManager=dbManager;
    }
@Override
public void savePlayer(List<Player> thePlayers) {
    for (Player player : thePlayers) {
        if (player.getPlayerStats() != null) {
            for (PlayerStats stat : player.getPlayerStats()) {
                stat.setPlayer(player); // Important: set the back-reference
            }
        }
    }

    dbManager.savePlayer(thePlayers);
}

    public List<Player> getAllPlayers(){
      List<Player> result= dbManager.getAllPlayers();

      return result;
    }
    public Player getPlayerById(int id){
     Player result=dbManager.getPlayerById(id);
     return result;
    }

    public List<Player> getPlayerList(){
     List<Player> result=dbManager.getPlayersLimited();
     return result;
    }
    public List<PlayerDto> getPlayerStats(){
     List<PlayerDto> result=dbManager.getPlayerStats();
     return result;
    }
    public List<PlayerDto> getPlayerStatsByFormat(String format){
         List<PlayerDto> result=dbManager.getPlayerStatsByFormat(format);
         return result;
    }
    public String generateDocumentary(String prompt) throws InterruptedException {
        String apiKey = apiKeyGemini;
        String url = apiUrl;
        String model = "google/gemini-2.5-flash-image";

        // Pass the dynamic prompt directly from the user
        String userPrompt = prompt;

        GeminiRequest request = new GeminiRequest(model, userPrompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("HTTP-Referer", "https://your-app.com");
        headers.set("X-Title", "Cricket AI Content");

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        int maxRetries = 5;
        int retryCount = 0;
        long retryDelayMs = 16000;

        while (true) {
            try {
                ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        GeminiResponse.class
                );

                if (response.getBody() != null &&
                        response.getBody().getChoices() != null &&
                        !response.getBody().getChoices().isEmpty()) {

                    // Return the raw HTML text
                    return response.getBody().getChoices().get(0).getMessage().getContent();
                } else {
                    return "<h2>No content received from Gemini API</h2>";
                }

            } catch (HttpClientErrorException.TooManyRequests e) {
                if (retryCount >= maxRetries) throw e;
                retryCount++;
                System.out.println("429 Too Many Requests, retrying in " + (retryDelayMs / 1000) + " seconds...");
                Thread.sleep(retryDelayMs);
            }
        }
    }




}
