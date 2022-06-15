package com.example.finalapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
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

public class APIActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nmFilme;
    private TextView nmTitulo;
    private TextView nmDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmFilme = findViewById(R.id.edtxtRecebe);
        nmTitulo = findViewById(R.id.txtTitulo);
        nmDados = findViewById(R.id.txtDados);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscaFilme(View view) {

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
            nmDados.setText(R.string.str_empty);
            nmTitulo.setText(R.string.loading);
        }

        else {
            if (queryString.length() == 0) {
                nmDados.setText(R.string.str_empty);
                nmTitulo.setText(R.string.no_search_term);
            } else {
                nmDados.setText(" ");
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
        return new CarregaFilmes(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {

            JSONObject jsonObject = new JSONObject(data);

            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String titulo = null;
            String descr = null;

            while (i < itemsArray.length() &&
                    (descr == null && titulo == null)) {

                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    titulo = volumeInfo.getString("title");
                    descr = volumeInfo.getString("description");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (titulo != null && descr != null) {
                nmTitulo.setText(titulo);
                nmDados.setText(descr);

            } else {

                nmTitulo.setText(R.string.no_results);
                nmDados.setText(R.string.str_empty);
            }
        } catch (Exception e) {

            nmTitulo.setText(R.string.no_results);
            nmDados.setText(R.string.str_empty);
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}