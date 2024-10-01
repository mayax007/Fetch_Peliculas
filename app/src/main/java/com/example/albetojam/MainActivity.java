package com.example.albetojam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView Text;
    private Button btnPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPopular = (Button) findViewById(R.id.btnPopular);
        Text = (TextView) findViewById(R.id.Text);

        btnPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MovieResponse> call = RetrofitClient.getInstance().getPopularMovies();
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            List<Movie> movies = response.body().getResults();
                            for(Movie myMovie:movies) {
                                Toast.makeText(MainActivity.this,"Movie" + myMovie.getTitle(), Toast.LENGTH_SHORT);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        // Maneja el error aquí
                    }
                });
            }
        });
    }
}