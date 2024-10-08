package br.com.phfernandesmc.screenmatch.main;

import br.com.phfernandesmc.screenmatch.model.Episode;
import br.com.phfernandesmc.screenmatch.model.EpisodeData;
import br.com.phfernandesmc.screenmatch.model.SeasonData;
import br.com.phfernandesmc.screenmatch.model.SeriesData;
import br.com.phfernandesmc.screenmatch.service.ConsumptionAPI;
import br.com.phfernandesmc.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
//"http://www.omdbapi.com/?t=gilmore+girls&apikey=49744a85"

public class Main {
    private final Scanner read = new Scanner(System.in);
    private final ConsumptionAPI consumptionAPI = new ConsumptionAPI();
    private final ConvertData convertor = new ConvertData();

    public void showMenu() {
        System.out.println("Type a series name to search");
        var seriesName = read.nextLine();
        String ADDRESS = "http://www.omdbapi.com/?t=";
        String API_KEY = "&apikey=49744a85";
        var json = consumptionAPI.getData(ADDRESS + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + API_KEY);

        SeriesData seriesData = convertor.getData(json, SeriesData.class);
        System.out.println(seriesData);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            json = consumptionAPI.getData("http://www.omdbapi.com/?t=" + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + "&season=" + i + "&apikey=49744a85");
            SeasonData seasonData = convertor.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }

//        List<EpisodeData> episodeDataList = seasons
//                .stream()
//                .flatMap(s -> s.episodes()
//                        .stream()).collect(Collectors.toList());

//        System.out.println("\n Top 10 episodes");
//        episodeDataList
//                .stream()
//                .filter(e -> !e.rate().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("first filter(N/A)"))
//                .sorted(Comparator
//                        .comparing(EpisodeData::rate)
//                        .reversed())
//                .peek(e -> System.out.println("re-organizing"))
//                .limit(10)
//                .map(e -> e.title().toUpperCase())
//                .forEach(System.out::println);

        List<Episode> episodes = seasons
                .stream()
                .flatMap(s -> s.episodes()
                        .stream()
                        .map(d -> new Episode(s.id(), d)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Please any title that you would like to see");
        var searchTitle = read.nextLine();

        Optional<Episode> episodeSearched = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(searchTitle.toUpperCase()))
                .findFirst();


        if (episodeSearched.isPresent()) {
            System.out.println("Episode found");
            System.out.println(episodeSearched);
        } else {
            System.out.println("Episode not found");
        }

//        System.out.println("From what year would you like to see the episodes");
//        var year = read.nextInt();
//        read.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes
//                .stream()
//                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeason() +
//                                " Episode: " + e.getTitle() +
//                                " Released: " + e.getReleased().format(formatter)
//
        Map<Integer, Double> seasonsRate = episodes.stream()
                .filter(e -> e.getRate() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRate)));

        System.out.println(seasonsRate);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRate() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRate));

        System.out.println(est.getAverage());
    }
}
