/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:10
 * Last edited: 18/01/21, 18:10
 */

package it.soundmate.controller.logic.profiles;


import com.google.gson.*;
import it.soundmate.bean.AddRoomBean;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RoomRenterProfileController extends EditController {

    private static final Logger logger = LoggerFactory.getLogger(RoomRenterProfileController.class);
    private final RoomRenterDao roomRenterDao = new RoomRenterDao();
    private static final String API_KEY = "zfE7Bgu4VoHQEIkULvSXcq8GN2eDfnF1";
    private static final String DEFAULT_URL = "https://www.mapquestapi.com/geocoding/v1/address?key="+API_KEY+"&inFormat=kvp&outFormat=json&location=";
    private static final String FILTER_RESULTS_URL = "&thumbMaps=false&maxResults=1";

    public int addRoom(AddRoomBean addRoomBean, RoomRenter roomRenter) {
        try {
            return roomRenterDao.addRoom(addRoomBean, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public double[] getRenterMap(String city, String address) {
        String finalUrl = buildUrl(city, address);
        String responseJson = makeHttpRequest(finalUrl);
        if (responseJson != null) {
            JsonObject jsonObject = new JsonParser().parse(responseJson).getAsJsonObject();
            JsonArray result = jsonObject.get("results").getAsJsonArray();
            JsonObject location = result.get(0).getAsJsonObject();
            JsonArray locations = location.get("locations").getAsJsonArray();
            JsonObject firstResult = locations.get(0).getAsJsonObject();
            JsonObject latLng = firstResult.get("latLng").getAsJsonObject();
            double[] coordinates = new double[2];
            coordinates[0] = Double.parseDouble(latLng.get("lat").getAsString());
            coordinates[1] = Double.parseDouble(latLng.get("lng").getAsString());
            return coordinates;
        } else {
            throw new InputException("Error making request");
        }
    }

    private String makeHttpRequest(String finalUrl) {
        try {
            URL url = new URL(finalUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL: {}", url);
            logger.info("Response Code : {}", responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("Response: {}", response);
            return response.toString();
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException, URL: {}", finalUrl);
            logger.error("UrlException", e);
        } catch (ProtocolException e) {
            logger.error("Protocol Exception: {}", e.getMessage());
            logger.error("Protocol exception", e);
        } catch (IOException ioException) {
            logger.error("IOException catched", ioException);
        }
        return null;
    }

    private String buildUrl(String city, String address) {
        StringBuilder stringBuilder = new StringBuilder(DEFAULT_URL);
        stringBuilder.append(city).append("+");
        String[] splitAddress = address.split(" ");
        for (int i = 0; i < splitAddress.length; i++) {
            if (i == splitAddress.length - 1) stringBuilder.append(splitAddress[i]);
            stringBuilder.append(splitAddress[i]).append("+");
        }
        stringBuilder.append(FILTER_RESULTS_URL);
        return stringBuilder.toString();
    }
}
