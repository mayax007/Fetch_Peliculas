package com.example.albetojam.movies_api;

import com.example.albetojam.json_mapper.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/* https://api.themoviedb.org/3/movie/popular?api_key=e9d0f83dfff3be33af1ce51a9a903541
 */
public interface MoviesAPI {
    static final String Key = "e9d0f83dfff3be33af1ce51a9a903541";
    // Routers
    @GET("movie/popular?api_key="+Key)
    Call<MovieResponse> getPopularMovies();

    @GET("search/movie?api_key="+Key+"&language=es-ES&page=1")
    Call<MovieResponse> getSearchedMovies(String language, int page);
}


