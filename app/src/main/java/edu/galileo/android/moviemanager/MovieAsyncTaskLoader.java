package edu.galileo.android.moviemanager;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.galileo.android.moviemanager.models.Movie;

/**
 * Created by noh on 8/7/17.
 */

public class MovieAsyncTaskLoader extends AsyncTask<URL, Integer, ArrayList<Movie>> {
    private static String TAG_MovieAsyncTaskLoader="MovieAsyncTaskLoader";
    private Context context;
    public MovieAsyncTaskLoader(Context context) {
        this.context=context;
    }
    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        Log.e(TAG_MovieAsyncTaskLoader,getJson());
        ConvertJsontoMovieList(getJson());
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

    }


    private ArrayList<Movie> ConvertJsontoMovieList(String jsonStr)  {

        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        JSONArray jsonarray = null;
        try {

            jsonarray = new JSONObject(jsonStr).getJSONArray("results");
            Log.e(TAG_MovieAsyncTaskLoader,jsonarray.get(0).toString());
        for (int i = 0; i < jsonarray.length(); i++) {

            Log.d(TAG_MovieAsyncTaskLoader,i+" iteration");
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String id = jsonobject.getString("id");
            String title = jsonobject.getString("title");
            String overview=jsonobject.getString("overview");
            String voteAverage=jsonobject.getString("vote_average");
            String voteCount=jsonobject.getString("vote_count");
            String posterPath=jsonobject.getString("poster_path");
            String backdropPath=jsonobject.getString("backdrop_path");

            Movie movie = new Movie(id,title,overview,Float.valueOf(voteAverage),Float.valueOf(voteCount),posterPath,backdropPath);
            arrayList.add(movie);

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG_MovieAsyncTaskLoader,arrayList.get(0).getOverview());
        return arrayList;

    }



    public static String getJson() {

        URL url;
        StringBuffer response = new StringBuffer();
        try {
            url = new URL("https://api.themoviedb.org/3/movie/now_playing?api_key=02d88f658f2fa6619822e37c0a6db5ac&language=en-US&page=1");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url");
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            //Here is your json in string format
            String responseJSON = response.toString();
            return responseJSON;
        }


    }



}