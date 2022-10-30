package com.example.kukoslokos.util;

import com.example.kukoslokos.model.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Service {

    static final String API_KEY="12bb2b69299bc5534ff3f0ef888cb2c7";
    static final String URL_BASE = "https://api.themoviedb.org/3";

    private static JSONObject getRequestJSONObject(String path){
        JSONObject jsonObject = null;
        try {
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            if (con.getResponseCode()==200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine=in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                jsonObject = new JSONObject(response.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jsonObject;
        }
    }

    public static List<Pelicula> getPelisPopulares() {
        List<Pelicula> peliculas = new ArrayList<Pelicula>();
        try {
            JSONObject jsonObject = getRequestJSONObject(URL_BASE+"/movie/popular?api_key="+API_KEY+"&language=es-ES");
            peliculas=convertToPeliculaList(jsonObject.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return peliculas;
        }
    }

    private static List<Pelicula> convertToPeliculaList(JSONArray jsonArray) throws JSONException {
        List<Pelicula> peliculas = new ArrayList<Pelicula>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            peliculas.add(convertToPelicula(jsonObject));
        }
        return peliculas;
    }

    private static Pelicula convertToPelicula(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String titulo = jsonObject.getString("title");
        String argumento = jsonObject.getString("overview");
        String pathPoster = jsonObject.getString("poster_path");
        List<String> generos = new ArrayList<String>();//getCategorias((int[])jsonObject.get("genre_ids"));

        return new Pelicula(id, titulo, argumento, pathPoster, generos);
    }

    private static List<String> getCategorias(int[] categoriasIds) {
        //TODO Hacer esto funcional
        List<String> categorias = new ArrayList<String>();
        for (int i = 0; i < categoriasIds.length; i++) {

        }
        return null;
    }
}
