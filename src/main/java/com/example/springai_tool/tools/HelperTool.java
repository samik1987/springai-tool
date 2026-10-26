package com.example.springai_tool.tools;

import com.example.springai_tool.services.ChatService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Component
public class HelperTool {

    @Autowired
    public final RestClient restClient;

    @Value("${WEATHER_API_KEY}")
    private String weather_api_key;


    public HelperTool(RestClient restClient) {
        this.restClient = restClient;
    }


    // ** Informational Tool
    @Tool(name = "WeatherTool", description = "Get weather information of given location or area")
    public String GetTodayWeather(@ToolParam(description="The city which I want weather information") String city,
                                  @ToolParam(description="uri to get specific weather report as per user request") String uri)
    {


        System.out.println("** Weather Tool Called by LLM =");

        String response ="";

        try {

            response = restClient.get()
                    .uri(
                            uriBuilder -> uriBuilder.path(uri)
                                    .queryParam("key", weather_api_key)
                                    .queryParam("q", city)
                                    .build()
                    )
                    .retrieve()
                    .body(String.class);
        }
        catch (Exception ex)
        {
            System.out.println("**Exception =" + ex.getMessage());
        }


        System.out.println(uri +" -- Weather API Response =" + response);
        return response;

        //return "Today is Raining...Yesterday was Hot,Sunny and tomorrow will Cold breeze";
    }

    @Tool(description = "to get Today's Date related information")
    public String GetTodayDate()
    {
        System.out.println("** Today Date Tool Called by LLM");
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    // ** Action Tool

    @Tool(description = "to set the Alarm for given time")
    public void SetAlarm(@ToolParam(description="Time format is ISO-8601") String time)
    {
        System.out.println("** Set Alarm Tool Called by LLM");

        System.out.println("# Alarm set on -" + LocalDateTime.parse(time,DateTimeFormatter.ISO_DATE_TIME));

    }


}
