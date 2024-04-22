package org.example;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Les11_1 {
    public static void main(String[] args) {
GetDataBinance getDataBinance = new GetDataBinance();
getDataBinance.start();
ComparePrice comparePrice = new ComparePrice();
comparePrice.start();
    }
    static class GetDataBinance extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            while (true){
            ArrayList<Binance> binances1  =getData();
            setDataInJSON(binances1,"binance1.json");
            sleep(10000);
                ArrayList<Binance> binances2  =getData();
            setDataInJSON(binances2,"binance2.json");
            sleep(10000);
                System.out.println("h");
            }
        }
        private static ArrayList<Binance> getData() throws Exception {
            ArrayList<Binance> binances = new ArrayList<>();
            URL url = new URL("https://api.binance.com/api/v3/ticker/price");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String info = bufferedReader.readLine();
            JSONArray jsonArray = new JSONArray(info);
            for (Object obj: jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                double price =Double.parseDouble(jsonObject.get("price").toString());
                String token = jsonObject.get("symbol").toString();
                Binance binance = Binance.builder()
                        .price(price)
                        .token(token).build();
                binances.add(binance);
            }
            return binances;
        }
        private static void setDataInJSON(ArrayList<Binance> binances,String nameFile) throws Exception {
            FileWriter fileWriter = new FileWriter(nameFile);
            JSONArray jsonArray = new JSONArray();
            for (Binance bin: binances) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(bin.token,bin.price);
                jsonArray.put(jsonObject);
            }
            fileWriter.append(jsonArray.toString());
            fileWriter.close();
        }
    }
    @Builder
    @ToString
    static public class Binance{
        private String token;
        private double price;

    }
    static private class ComparePrice extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                sleep(11000);
                JSONParser jsonParser = new JSONParser();
                FileWriter fileWriter = new FileWriter("result.json");
                FileReader fileReader = new FileReader("binance1.json");
                FileReader fileReader1 = new FileReader("binance2.json");
                JSONArray jsonArrayRes = new JSONArray();
                try {
                    org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonParser.parse(fileReader);
                    org.json.simple.JSONArray jsonArray1 = (org.json.simple.JSONArray) jsonParser.parse(fileReader1);
                    Iterator<Object> iterator1 = jsonArray1.iterator();
                    Iterator<Object> iterator = jsonArray.iterator();
                    while (iterator.hasNext() && iterator1.hasNext()) {
                        Object o1 = iterator.next();
                        Object o2 = iterator1.next();
                        org.json.simple.JSONObject jsonObject1 = (org.json.simple.JSONObject) o1;

                        org.json.simple.JSONObject jsonObject2 = (org.json.simple.JSONObject) o2;
                        System.out.println(jsonObject1.toString());
                        String[] info1 = jsonObject1.toString().substring(1, jsonObject1.toString().length() - 1).split(":");
                        String[] info2 = jsonObject2.toString().substring(1, jsonObject2.toString().length() - 1).split(":");
                        double price = Double.parseDouble(info1[1]);
                        double price1 = Double.parseDouble(info2[1]);
                        double proc = (Math.abs(price1 - price) / (Math.min(price, price1))) * 100;
                        if (price < price1)

                            System.out.println("Рост на " + proc);
                        else if (price > price1) {
                            System.out.println("Падение на " + proc + " %");
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(info1[0], proc);
                        jsonObject.put("ChangePrice", price < price1 ? "UP" : "DOWN");
                        jsonArrayRes.put(jsonObject);

                    }
                    fileWriter.append(jsonArrayRes.toString());
                    fileWriter.close();


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        }
    }
    @Builder
    @ToString
    static private class Itog{
        private String token;
        private double change;
    }
}
