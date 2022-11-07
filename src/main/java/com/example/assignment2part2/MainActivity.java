package com.example.assignment2part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button searchImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        searchImage = findViewById(R.id.SearchBtn);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchImage();
            }
        });
    }

    public void searchImage(){
        Toast.makeText(MainActivity.this, "Searching starts", Toast.LENGTH_SHORT).show();
        Search searchTask = new Search(MainActivity.this);
        TextView searchTerm = (TextView)findViewById(R.id.SearchTerm);
        searchTask.setSearchkey(searchTerm.getText().toString());
        Single<String> searchObservable = Single.fromCallable(searchTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this, "Searching Ends", Toast.LENGTH_SHORT).show();
                loadImageActivity(s);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Searching Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void loadImageActivity(String s)
    {
        Intent intent = new Intent(MainActivity.this, DisplayUI.class);
        intent.putExtra("searchResult", s);
        startActivity(intent);
    }
}