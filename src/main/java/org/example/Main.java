package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String apiToken = "cur_live_wgGs7S7NaCjddy23HZEuEdkCSW8SAN20jjT45iyU";
        System.out.println("Welcome! \n Choose your currency code (1-8) :" +
                "\n1-Iranian Rial \t2-Euro" +
                "\n3-Chinese Yuan \t4-Canadian Dollar" +
                "\n5-Japanese Yen \t6-British Pound" +
                "\n7-Bitcoin \t8-Ethereum");

        Scanner input = new Scanner(System.in);
        String currency = "";
        int num = input.nextInt();

        while (num > 8 || num < 1) {
            System.out.println("Please enter a number between 1 and 8");
            num = input.nextInt();
        }

        currency = switch (num) {
            case 1 -> "IRR";
            case 2 -> "EUR";
            case 3 -> "CNY";
            case 4 -> "CAD";
            case 5 -> "JPY";
            case 6 -> "GBP";
            case 7 -> "BTC";
            case 8 -> "ETH";
            default -> "USD";
        };

        String urlAddress = "https://api.currencyapi.com/v3/latest?apikey=" + apiToken +
                "&currencies=USD&base_currency=" + currency;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAddress))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(response.body());
            JSONObject rawData = (JSONObject) object.get("data");
            JSONObject data = (JSONObject) rawData.get("USD");
            double value = (double) data.get("value");

            System.out.println(currency + " -> USD = " + value + '$');

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}