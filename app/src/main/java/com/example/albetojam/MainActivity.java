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
    private Button botonPopular;
    private Button botonBuscar;
    private Button botonDetalles;
    private EditText buscaTitulo, buscaIdioma, buscaId;
    private TextView Resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba);

        botonPopular = findViewById(R.id.btnPopular);
        botonBuscar = findViewById(R.id.btnBuscar);
        botonDetalles = findViewById(R.id.btnDetalles);

        buscaTitulo = findViewById(R.id.titleEditText);
        buscaIdioma = findViewById(R.id.languageEditText);
        buscaId = findViewById(R.id.idEditText);

        Resultados = findViewById(R.id.resultsTextView);

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
                String title = buscaTitulo.getText().toString();
                String language = buscaIdioma.getText().toString();
                int page = 1;
                if (title.isEmpty() || language.isEmpty()){
                    Toast.makeText(MainActivity.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                }
                Call<MovieResponse> call = RetrofitClient.getInstance().getSearchMovie(title, language, page);
                calling(call);
            }
        });

        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = 554;
                try {
                    id = Integer.parseInt(buscaId.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
                }
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
                    StringBuilder result = new StringBuilder();
                    for (Movie movie : response.body().getResults()) {
                        result.append(movie.getTitle()).append("\n");
                    }
                    Resultados.setText(result.toString());
                } else {
                    Toast.makeText(MainActivity.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}