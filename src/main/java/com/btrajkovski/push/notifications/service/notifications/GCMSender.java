package com.btrajkovski.push.notifications.service.notifications;

import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bojan on 13.5.16.
 */
@Service
public class GCMSender implements SenderService {

    @Override
    public void sendToAll(List<Device> devices, Notification notification) {
        if (devices.size() == 0) {
            return;
        }

        List<String> devicesArray = new ArrayList<>(devices.size());

        for (Device device : devices) {
            devicesArray.add(device.getDeviceToken());
        }

        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("registration_ids", devicesArray);
        jsonRequest.put("title", notification.getTitle());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", notification.getTitle());
        hashMap.put("message", notification.getContent());
//        hashMap.putAll(notification.getCustomFields());
        jsonRequest.put("data", hashMap);

        System.out.println(jsonRequest + " " + notification);

        sendGCMRequest(jsonRequest, notification);
    }

    private String sendGCMRequest(JSONObject payload, Notification notification) {
        // 1. URL
        try {
            URL url = new URL("https://gcm-http.googleapis.com/gcm/send");

            // 2. Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 3. Specify POST method
            conn.setRequestMethod("POST");

            // 4. Set the headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + notification.getApplication().getGCMKey());
            conn.setDoOutput(true);

            // 5. Add JSON data into POST request body
            //`5.1 Use Jackson object mapper to convert Contnet object into JSON
            ObjectMapper mapper = new ObjectMapper();

            // 5.2 Get connection output stream
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            // 5.3 Copy Content "JSON" into
            payload.writeJSONString(outputStreamWriter);

            // 5.4 Send the request
            outputStreamWriter.flush();

            // 5.5 close
            outputStreamWriter.close();

            // 6. Get the response
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 7. Print result
            System.out.println(response.toString());
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }
}
