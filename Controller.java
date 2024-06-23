package org.example.pogodnik_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Label feels;

    @FXML
    private Button finder;

    @FXML
    private Label info;

    @FXML
    private Label label_pogoda;

    @FXML
    private Label max;

    @FXML
    private Label min;

    @FXML
    private Label pressure;

    @FXML
    private Label temp;

//    @FXML
//    void initialize() {
//        finder.setOnAction(event -> {
//            System.out.println("Все работает!");
//        });
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        finder.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=9f9a6520fedfd4c4f13d6294c3122ffe&units=metric");

                if(!output.isEmpty()){
                    JSONObject obj = new JSONObject(output);
                    temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                    feels.setText("Ощущается как: " + obj.getJSONObject("main").getDouble("feels_like"));
                    min.setText("Ветер (м/с): " + obj.getJSONObject("wind").getDouble("speed"));
                    final Map<String, String> weatherTranslations = new HashMap<>();
                    weatherTranslations.put("Clouds", "Облачно");
                    weatherTranslations.put("overcast clouds", "Пасмурно");
                    weatherTranslations.put("clear sky", "Ясно");
                    weatherTranslations.put("heavy intensity rain", "Сильный дождь");


                    String description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
                    String translatedDescription = weatherTranslations.getOrDefault(description, description);

                    max.setText("Погодные условия: " + translatedDescription);
                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress){
        StringBuffer content = new StringBuffer();
        try{
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line=bufferedReader.readLine())!=null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("Город не найден.");
        }
        return content.toString();
    }
}
