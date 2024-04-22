package org.example;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Les11_2 {
    static int h=0, m=0 , s=0;//сделали
    static boolean state = true;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {
        TestGroup testGroup = new TestGroup();
        testGroup.start();
        TimesAll times = new TimesAll();//сделали
        times.start();
    }
    static class TestGroup extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            FileWriter fileWriter = new FileWriter("answer.json");
            int count = 0;
            org.json.JSONArray jsonArray = new org.json.JSONArray();
            for (InfoJSON elem: getData()) {
                org.json.JSONObject jsonObject = new org.json.JSONObject();
                System.out.println(elem.question);
                for (int i = 0; i <elem.arrayAnswer.length ; i++) {
                    System.out.println(i+" ответ "+elem.arrayAnswer[i]);
                }
                int yourAnswer = scanner.nextInt();
                int ans =  yourAnswer==elem.correctAnswer?1:0;

                jsonObject.put(String.valueOf(count),ans);
                jsonArray.put(jsonObject);
                count++;
            }
            fileWriter.append(jsonArray.toString());
            fileWriter.close();
            state = false;

        }
    }
    static class TimesAll extends Thread {// сделали
        @Override
        public void run() {

            while (true){
                if (!state){
                    System.out.println(h+" часов "+m+" минут "+s+" секунд");
                    break;
                }
                if (m==40)
                {
                    System.out.println("Время закончилось");break;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                s++;
                if (s==60){
                    s=0;
                    m++;
                }
                if (m==60){
                    m = 0;
                    h++;
                }
//
            }
        }
    }
    public static ArrayList<InfoJSON> getData() throws Exception {// сделали
        ArrayList<InfoJSON> infoJSONS = new ArrayList<>();
        FileReader fileReader = new FileReader("test.json");
        JSONParser jsonParser = new JSONParser();
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonParser.parse(fileReader);
        for (Object obj:jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String[] arr = jsonObject.get("answer").toString().substring(1,jsonObject.get("answer").toString().length()-1)
                    .split(",");
            int correctAnswer = Integer.parseInt(jsonObject.get("correctAnswer").toString());
            InfoJSON infoJSON = InfoJSON.builder()
                    .question(jsonObject.get("question")
                            .toString()).arrayAnswer(arr)
                    .correctAnswer(correctAnswer).build();
            infoJSONS.add(infoJSON);
        }
        return infoJSONS;
    }
    @Builder// сделали
    @ToString
    static class InfoJSON{
        private String question;
        private String[] arrayAnswer;
        private int correctAnswer;

    }

}
