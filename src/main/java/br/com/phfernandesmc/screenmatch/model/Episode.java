package br.com.phfernandesmc.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {
    private int season;
    private String title;
    private int episode;
    private double rate;
    private LocalDate released;

    public Episode(int seasonNum, EpisodeData episodeData) {
        this.season = seasonNum;
        this.title = episodeData.title();
        this.episode = episodeData.episode();
        try {
            this.rate = Double.parseDouble(episodeData.rate());
        } catch (NumberFormatException ex) {
            this.rate = 0.0;
        }
        try {
            this.released = LocalDate.parse(episodeData.released());
        } catch (DateTimeException ex) {
            this.released = null;
        }
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                        ", title='" + title + '\'' +
                        ", episode=" + episode +
                        ", rate=" + rate +
                        ", released=" + released;
    }
}
