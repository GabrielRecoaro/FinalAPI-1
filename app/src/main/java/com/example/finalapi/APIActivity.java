package com.example.finalapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>  {

    private EditText nmFilme;
    private TextView nmTitulo;
    private TextView nmElenco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);
        nmFilme = findViewById(R.id.livroInput);
        nmTitulo = findViewById(R.id.tituloText);
        nmElenco = findViewById(R.id.autorText);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscaElenco(View view) {

        String queryString = nmFilme.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            nmElenco.setText(R.string.str_empty);
            nmTitulo.setText(R.string.loading);
        }

        else {
            if (queryString.length() == 0) {
                nmElenco.setText(R.string.str_empty);
                nmTitulo.setText(R.string.no_search_term);
            } else {
                nmElenco.setText(" ");
                nmTitulo.setText(R.string.no_network);
            }
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new CarregaStudio(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {

            JSONObject jsonObject = new JSONObject(data);

            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String titulo = null;
            String autor = null;

            while (i < itemsArray.length() &&
                    (autor == null && titulo == null)) {

                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    titulo = volumeInfo.getString("title");
                    autor = volumeInfo.getString("authors");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (titulo != null && autor != null) {
                nmTitulo.setText(titulo);
                nmElenco.setText(autor);

            } else {

                nmTitulo.setText(R.string.no_results);
                nmElenco.setText(R.string.str_empty);
            }
        } catch (Exception e) {

            nmTitulo.setText(R.string.no_results);
            nmElenco.setText(R.string.str_empty);
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);
    }*/

    public void TelaMenu(View view){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}