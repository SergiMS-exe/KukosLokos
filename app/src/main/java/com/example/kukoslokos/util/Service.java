package com.example.kukoslokos.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.kukoslokos.datos.PeliculasDataSource;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service {

    static final String API_KEY="12bb2b69299bc5534ff3f0ef888cb2c7";
    static final String URL_BASE = "https://api.themoviedb.org/3";

    static String jsonString = "{\"genres\":[{\"id\":28,\"name\":\"Acción\"}," +
            "{\"id\":12,\"name\":\"Aventura\"},{\"id\":16,\"name\":\"Animación\"}," +
            "{\"id\":35,\"name\":\"Comedia\"},{\"id\":80,\"name\":\"Crimen\"}," +
            "{\"id\":99,\"name\":\"Documental\"},{\"id\":18,\"name\":\"Drama\"}," +
            "{\"id\":10751,\"name\":\"Familia\"},{\"id\":14,\"name\":\"Fantasía\"}," +
            "{\"id\":36,\"name\":\"Historia\"},{\"id\":27,\"name\":\"Terror\"}," +
            "{\"id\":10402,\"name\":\"Música\"},{\"id\":9648,\"name\":\"Misterio\"}," +
            "{\"id\":10749,\"name\":\"Romance\"},{\"id\":878,\"name\":\"Ciencia ficción\"}," +
            "{\"id\":10770,\"name\":\"Película de TV\"},{\"id\":53,\"name\":\"Suspense\"}," +
            "{\"id\":10752,\"name\":\"Bélica\"},{\"id\":37,\"name\":\"Western\"}]}";


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

    public static List<Pelicula> getPelisByCategoria(String categoria) {
        List<Pelicula> peliculas = new ArrayList<Pelicula>();
        try {
            String path = URL_BASE+"/movie/"+categoria+"?api_key="+API_KEY+"&language=es-ES";
            JSONObject jsonObject = getRequestJSONObject(path);
            peliculas=convertToPeliculaList(jsonObject.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return peliculas;
        }
    }

    public static Usuario getUserById(String id){
        Usuario usuario = new Usuario();
        try {
            String path = "https://kukos-server.vercel.app/getUserById?_id="+id;
            JSONObject jsonObject = getRequestJSONObject(path);
            usuario = convertToUsuario(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return usuario;
        }

    /**
     * Devuelve una lista de las peliculas filtradas por categoria
     * @param genero_id
     * @param peliculasAFiltrar
     * @return
     */
    public static List<Pelicula> getMoviesByGenero(int genero_id, List<Pelicula> peliculasAFiltrar) {
        List<Pelicula> filteredMovies = new ArrayList<>();
        for (Pelicula pelicula : peliculasAFiltrar) {
            if (isInCategory(pelicula.getGeneros(), genero_id)) {
                filteredMovies.add(pelicula);
            }
        }
        return filteredMovies;
    }

    /**
     * Método que devuelve true si conincide con la categoría filtrada
     * false en caso contrario
     * @param generos
     * @param genero_id
     * @return true si coincide con la categoria
     */
    private static boolean isInCategory(int[] generos, int genero_id) {
        for (int i = 0; i < generos.length; i++) {
            if(generos[i] == genero_id){
                return true;
            }
        }
        return false;
    }

    public static Pelicula getPeliById(int id){
        Pelicula pelicula = new Pelicula();
        try {
            String path = URL_BASE+"/movie/"+id+"?api_key="+API_KEY+"&language=es-ES";
            JSONObject jsonObject = getRequestJSONObject(path);
            pelicula = convertToPelicula(jsonObject);
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            return pelicula;
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

    private static Usuario convertToUsuario(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.getString("_id");
        String nombre = jsonObject.getString("nombre");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        JSONArray moviesSavedJsonArray = jsonObject.getJSONArray("moviesSaved");
        List<Integer> moviesSaved = new ArrayList<>();
        for (int i = 0; i < moviesSavedJsonArray.length(); i++) {
            moviesSaved.add(moviesSavedJsonArray.getInt(i));
        }
        int points = jsonObject.getInt("points");
        double lastRule = jsonObject.getDouble("lastRule");
        return new Usuario(id, nombre, email, password, points, moviesSaved, lastRule);
    }

    private static Pelicula convertToPelicula(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String titulo = jsonObject.getString("title");
        String argumento = jsonObject.getString("overview");
        String pathPoster = jsonObject.getString("poster_path");
        String pathBackdrop = jsonObject.getString("backdrop_path");

        //TODO: Meter los generos
        JSONArray generos_id_conv;
        try {
            generos_id_conv = jsonObject.getJSONArray("genre_ids");
        }catch(Exception e){
            generos_id_conv = jsonObject.getJSONArray("genres");
        }
        int[] generos_id = new int[generos_id_conv.length()];

        for (int i = 0; i < generos_id_conv.length(); i++){
            generos_id[i] = generos_id_conv.optInt(i);
        }

        return new Pelicula(id, titulo, argumento, pathPoster, pathBackdrop, generos_id);
    }

    private static List<String> getCategorias(int[] categoriasIds) {
        //TODO Hacer esto funcional
        List<String> categorias = new ArrayList<String>();
        for (int i = 0; i < categoriasIds.length; i++) {

        }
        return null;
    }

    public static void guardarPeli(int idUser, int idPeli, Context context) {
        PeliculasDataSource peliculasDataSource = new PeliculasDataSource(context);

        peliculasDataSource.marcarPeliculaComoFav(idPeli, idUser);
    }

    public static List<Pelicula> searchPelis(String query){
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            String path = URL_BASE+"/search/movie?api_key="+API_KEY+"&language=es-ES&query="+query+"&page=1";
            JSONObject jsonObject = getRequestJSONObject(path);
            peliculas = convertToPeliculaList(jsonObject.getJSONArray("results"));
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            return peliculas;
        }
    }

    public static void eliminarGuardada(int idUser, int idPeli, Context context) {
        PeliculasDataSource peliculasDataSource = new PeliculasDataSource(context);
        peliculasDataSource.eliminarFavPeli(idPeli, idUser);
    }

    public static HashMap<String, Integer> getGenerosTotales() throws JSONException {
        HashMap<String, Integer> genreMap = new HashMap<>();

        JSONObject json = new JSONObject(jsonString);
        JSONArray genres = json.getJSONArray("genres");

        for (int i = 0; i < genres.length(); i++) {
            JSONObject genre = genres.getJSONObject(i);
            String name = genre.getString("name");
            int id = genre.getInt("id");
            genreMap.put(name, id);
        }
        return genreMap;
    }
   /* //------------------------- Metodos para las series ----------------------------
    // /tv/{tv_id}
    // String path = URL_BASE + "/tv/" + idSerie + "?api_key="+API_KEY "&language=es-ES";
    // TODO: Hacer Serie Parcelable

    private static Serie convertToSerie(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String titulo = jsonObject.getString("title");
        String argumento = jsonObject.getString("overview");
        String pathPoster = jsonObject.getString("poster_path");
        String pathBackdrop = jsonObject.getString("backdrop_path");
        List<String> generos = new ArrayList<String>();//getCategorias((int[])jsonObject.get("genre_ids"));

        return new Serie(id, titulo, argumento, pathPoster, pathBackdrop, generos);
    }

    public static Serie getSerieById(int id){
        Serie serie = new Serie();
        try {
            String path = URL_BASE+"/movie/"+id+"?api_key="+API_KEY+"&language=es-ES";
            JSONObject jsonObject = getRequestJSONObject(path);
            serie = convertToSerie(jsonObject);
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            return serie;
        }
    }*/
}
