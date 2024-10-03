package br.com.phfernandesmc.screenmatch;

import br.com.phfernandesmc.screenmatch.model.SeriesData;
import br.com.phfernandesmc.screenmatch.service.ConsumptionAPI;
import br.com.phfernandesmc.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumptionAPI consumptionAPI = new ConsumptionAPI();
		var json = consumptionAPI.getData("http://www.omdbapi.com/?t=gilmore+girls&apikey=49744a85");
		System.out.println(json);

		ConvertData convertor = new ConvertData();
		SeriesData data = convertor.getData(json, SeriesData.class);
		System.out.println(data);
	}
}
