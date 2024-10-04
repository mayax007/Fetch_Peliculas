package com.example.albetojam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.albetojam.json_mapper.Movie;
import com.example.albetojam.json_mapper.MovieResponse;
import com.example.albetojam.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button botonPopular;
    private Button botonBuscar;
    private Button botonDetalles;
    private EditText Buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonPopular = findViewById(R.id.btnPopular);
        botonBuscar = findViewById(R.id.btnBuscar);
        botonDetalles = findViewById(R.id.btnDetalles);
        Buscador = findViewById(R.id.searchBar);

        botonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MovieResponse> call = RetrofitClient.getInstance().getPopularMovies();
                calling(call);
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Buscador.getText().toString();
                Call<MovieResponse> call = RetrofitClient.getInstance().getSearchMovie(title);
                calling(call);
            }
        });

        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(Buscador.getText().toString());
                Call<MovieResponse> call = RetrofitClient.getInstance().getMovieDetails(id);
                calling(call);
            }
        });
    }
    protected void calling(Call<MovieResponse> call){
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
                    for(Movie myMovie:movies) {
                        Toast.makeText(MainActivity.this,"Movie" + myMovie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Maneja el error aquí
            }
        });
    }
}