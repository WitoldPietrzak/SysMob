package com.example.smproject.Utils;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsManager {
    Context context;
    String data;
    JSONObject reader;
    volatile boolean ready;

    public StatsManager(Context context) throws JSONException {
        this.context = context;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.covid19api.com/summary";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        data = response;
                        ready=true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                data = "";
                ready=true;
            }
        });
        queue.add(stringRequest);
        queue.start();
    }

    public String getStatsGlobal() throws JSONException {
        if(data ==null)
        {
            return "No data accquired";
        }
        reader = new JSONObject(data);
        JSONObject globalSt = reader.getJSONObject("Global");
        int newConfirmed = globalSt.getInt("NewConfirmed");
        int totalConfirmed = globalSt.getInt("TotalConfirmed");
        int newDeaths = globalSt.getInt("NewDeaths");
        int totalDeaths = globalSt.getInt("TotalDeaths");
        int NewRecovered = globalSt.getInt("NewRecovered");
        int TotalRecovered = globalSt.getInt("TotalRecovered");

        return "Global:\n" +
                "New Confirmed cases: "+ String.valueOf(newConfirmed)+"\n" +
                "Total Confirmed cases: "+String.valueOf(totalConfirmed)+"\n" +
                "New Deaths: "+String.valueOf(newDeaths)+"\n" +
                "Total Deaths: "+String.valueOf(totalDeaths)+"\n" +
                "New Recovered: "+String.valueOf(NewRecovered)+"\n" +
                "Total Recovered: "+String.valueOf(TotalRecovered);
    }

    public String getStatsPoland() throws JSONException {
        if(data ==null) {
            return "No data accquired";
        }
            reader = new JSONObject(data);
            JSONObject country = null;
            JSONArray countries = reader.getJSONArray("Countries");
            for (int i =0; i < countries.length();i++)
            {
                if (countries.getJSONObject(i).getString("Country").equals("Poland"))
                {
                    country=countries.getJSONObject(i);
                    break;
                }
            }
            if (country == null)
            {
                return "No data accquired";
            }
        int newConfirmed = country.getInt("NewConfirmed");
        int totalConfirmed = country.getInt("TotalConfirmed");
        int newDeaths = country.getInt("NewDeaths");
        int totalDeaths = country.getInt("TotalDeaths");
        int NewRecovered = country.getInt("NewRecovered");
        int TotalRecovered = country.getInt("TotalRecovered");

        return "Poland:\n" +
                "New Confirmed cases: "+ String.valueOf(newConfirmed)+"\n" +
                "Total Confirmed cases: "+String.valueOf(totalConfirmed)+"\n" +
                "New Deaths: "+String.valueOf(newDeaths)+"\n" +
                "Total Deaths: "+String.valueOf(totalDeaths)+"\n" +
                "New Recovered: "+String.valueOf(NewRecovered)+"\n" +
                "Total Recovered: "+String.valueOf(TotalRecovered);

    }

}
