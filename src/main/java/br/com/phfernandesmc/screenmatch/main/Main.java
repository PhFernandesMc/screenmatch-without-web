package br.com.phfernandesmc.screenmatch.main;

import br.com.phfernandesmc.screenmatch.model.SeasonData;
import br.com.phfernandesmc.screenmatch.model.SeriesData;
import br.com.phfernandesmc.screenmatch.service.ConsumptionAPI;
import br.com.phfernandesmc.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//"http://www.omdbapi.com/?t=gilmore+girls&apikey=49744a85"

public class Main {
    private final Scanner read = new Scanner(System.in);
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=49744a85";
    private final ConsumptionAPI consumptionAPI = new ConsumptionAPI();
    private final ConvertData convertor = new ConvertData();

    public void showMenu() {
        System.out.println("Type a series name to search");
        var seriesName = read.nextLine();
        var json = consumptionAPI.getData(this.ADDRESS + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + this.API_KEY);

        SeriesData seriesData = convertor.getData(json, SeriesData.class);
        System.out.println(seriesData);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            json = consumptionAPI.getData("http://www.omdbapi.com/?t=" + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + "&season=" + i + "&apikey=49744a85");
            SeasonData seasonData = convertor.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }

        seasons.forEach(season -> season.episodes().forEach(episode -> System.out.println(episode.title())));
    }
}
