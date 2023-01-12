package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

public class GetGeneros extends AsyncTask<Void, Void, HashMap<String, Integer>> {

        public GetGeneros() {

        }

        @Override
        protected HashMap<String, Integer> doInBackground(Void... voids) {
            HashMap<String, Integer> generosTotales = new HashMap<String, Integer>();
            try {
                generosTotales = Service.getGenerosTotales();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                return generosTotales;
            }
        }
}
