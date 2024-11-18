package com.alurachallenges.conversor.principal;

import com.alurachallenges.conversor.modelos.MonedaRecord;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MonedaAPI {
    private final String apiKey = "dce259ab80af14cfc96ea64b";

    public MonedaRecord obtenerConversion(String monedaBase, String monedaTarget, double monto) {
        String direccion = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaBase
                           + "/" + monedaTarget + "/" + monto;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), MonedaRecord.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la conversión: " + e.getMessage());
        }
    }
}