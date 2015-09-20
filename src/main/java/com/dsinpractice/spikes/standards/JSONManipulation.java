package com.dsinpractice.spikes.standards;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONManipulation {

    public static void main(String[] args) throws ParseException {
        String sample = "{\"id\":\"smaato_e2f4b304-bf03-4878-83f5-c17157135831\",\"source\":{\"bidrequest_device_dpidmd5\":\"aa9354b6be1f756bc5b1cf6f842b7428\",\"bidrequest_device_os\":\"Android\"}}";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(sample);
        jsonObject.put("mykey", "12345");
        JSONObject source = (JSONObject) jsonObject.get("source");
        source.put("adnear_id", 123456);
        System.out.println(source.toJSONString());
    }
}
