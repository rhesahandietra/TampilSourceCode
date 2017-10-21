package com.handietron.tampilsourcecode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

        TextView myText;
        EditText editText;
        Spinner spinn;
        ArrayAdapter<CharSequence> list_sp;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (TextView) findViewById(R.id.text2);
        editText = (EditText) findViewById(R.id.text1);
        spinn = (Spinner) findViewById(R.id.spin);

        list_sp = ArrayAdapter.createFromResource(this,R.array.list_spin,android.R.layout.simple_spinner_item);
        list_sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinn.setAdapter(list_sp);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
        }
        });

        if (getSupportLoaderManager().getLoader(0) != null) {
        getSupportLoaderManager().initLoader(0, null, this);
        }
        }

public void getsource(View view) {
        String getSpin, getUrl, link;
        getSpin = spinn.getSelectedItem().toString(); //Mengambil spin untuk menjadi string
        getUrl = editText.getText().toString(); //Mengambil edittext untuk menjadi string

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


        if(!getUrl.isEmpty()){ //Kondisi EditText tidak kosong
        if(getUrl.contains(".") && !(getUrl.contains(" "))){ //cek input url
        if(checkConnection()){ //cek koneksi internet
        myText.setText("Loading....");

        link = getSpin + getUrl;

        Bundle bundle = new Bundle();
        bundle.putString("link_pros", link);
        getSupportLoaderManager().restartLoader(0, bundle, this);
        }
        else{
        Toast.makeText(this,"Cek Koneksi Anda",Toast.LENGTH_SHORT).show();
        myText.setText("Cek Koneksi Anda");
        myText.setTextSize(30);
        }
        }
        else{
        Toast.makeText(this,"URL Tidak Ditemukan",Toast.LENGTH_SHORT).show();
        myText.setText("URL Tidak Ditemukan");
        myText.setTextSize(30);
        }
        }
        else{
        Toast.makeText(this,"Input URL",Toast.LENGTH_SHORT).show();
        myText.setText("URL Kosong, Tolong Input URL");
        myText.setTextSize(30);
        }
        }

//Cek Koneksi Internet
public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
        }

@Override
public Loader<String> onCreateLoader(int id, Bundle args) {
        return new Connnect(this, args.getString("link_pros"));
}

@Override
public void onLoadFinished(Loader<String> loader, String data) {
        myText.setText(data);
        }

@Override
public void onLoaderReset(Loader<String> loader) {

        }
}
