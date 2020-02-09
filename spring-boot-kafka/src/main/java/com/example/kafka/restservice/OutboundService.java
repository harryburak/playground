package com.example.kafka.restservice;

import java.io.IOException;

import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import com.example.kafka.request.FootballRequest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class OutboundService {

	public void callRestService(FootballRequest request2) {
		OkHttpClient client= new OkHttpClient();
		Request request = new Request.Builder().url("https://api-football-v1.p.rapidapi.com/v2/teams/league/"+request2.getLeague())
				.get()
				.addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "400918a2bamshf476cc9a5fa6eb4p1501d2jsnc16acde3d7b6")
				.build();
		try {
			Response response = client.newCall(request).execute();
			System.out.println("Response --> "+response);
		} catch (IOException e) {
			System.err.println(e);
		} 
	}
}
