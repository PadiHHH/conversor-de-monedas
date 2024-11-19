package com.alurachallenges.conversor.principal;

import com.alurachallenges.conversor.modelos.MonedaRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

            // Verificar el código de respuesta HTTP
            if (response.statusCode() != 200) {
                Gson gson = new Gson();
                JsonObject errorJson = gson.fromJson(response.body(), JsonObject.class);
                String errorType = errorJson.get("error-type").getAsString();

                switch (errorType) {
                    case "unsupported-code" -> {
                        throw new IllegalArgumentException(
                                String.format("El código de moneda '%s' o '%s' no es soportado por la API.",
                                        monedaBase, monedaTarget)
                        );
                    }
                    case "malformed-request" -> {
                        throw new IllegalArgumentException("La solicitud es incorrecta. Verifica los códigos de moneda.");
                    }
                    case "invalid-key" -> {
                        throw new RuntimeException("Error de autenticación con la API.");
                    }
                    case "inactive-account" -> {
                        throw new RuntimeException("La cuenta de la API está inactiva.");
                    }
                    case "quota-reached" -> {
                        throw new RuntimeException("Se ha alcanzado el límite de consultas a la API.");
                    }
                    default -> {
                        throw new RuntimeException("Error desconocido al consultar el tipo de cambio.");
                    }
                }
            }

            // Si la respuesta es exitosa, procesar el resultado
            Gson gson = new Gson();
            return gson.fromJson(response.body(), MonedaRecord.class);

        } catch (IOException e) {
            throw new RuntimeException("Error de conexión al servicio de conversión: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La operación fue interrumpida: " + e.getMessage());
        }
    }
}