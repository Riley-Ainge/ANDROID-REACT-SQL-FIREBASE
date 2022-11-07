package com.example.assignment2part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class DisplayUI extends AppCompatActivity {

    Intent intent;
    String searchResult;
    public static ArrayList<Bitmap> images;
    public static Bitmap selectedImage;
    Button singleView;
    Button doubleView;
    Button uploadBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ui);
        intent = getIntent();
        searchResult = intent.getStringExtra("searchResult");
        FirebaseApp.initializeApp(this);

        /*ImageRetrival imageRetrievalTask = new ImageRetrival(DisplayUI.this);
        imageRetrievalTask.setData(searchResult);

        Toast.makeText(DisplayUI.this, "Image loading starts", Toast.LENGTH_SHORT).show();
        Single<ArrayList<Bitmap>> searchObservable = Single.fromCallable(imageRetrievalTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<ArrayList<Bitmap>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ArrayList<Bitmap> bitmaps) {
                Toast.makeText(DisplayUI.this, "Image loading Ends", Toast.LENGTH_SHORT).show();
                images = bitmaps;
                FragmentManager fm = getSupportFragmentManager();
                SingleView frag = new SingleView();
                fm.beginTransaction().replace(R.id.DisplayFragmentView,frag).commit();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(DisplayUI.this, "Image loading error, search again", Toast.LENGTH_SHORT).show();
            }
        });*/

        singleView = findViewById(R.id.SingleViewBtn);
        doubleView = findViewById(R.id.DoubleViewBtn);
        uploadBtn = findViewById(R.id.Upload);
        singleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                SingleView frag = new SingleView();
                fm.beginTransaction().replace(R.id.DisplayFragmentView,frag).commit();
            }
        });
        doubleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DoubleView frag = new DoubleView();
                fm.beginTransaction().replace(R.id.DisplayFragmentView,frag).commit();
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.ImageVieeeew);
                selectedImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                if(selectedImage != null)
                {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, output);
                    byte[] data = output.toByteArray();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference mountainRef = storageRef.child("images/ee.jpg");
                    Log.d("EEE", data.toString());
                    UploadTask uploadTask = mountainRef.putBytes(data);
                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d("EEEE", "Upload is " + progress + "% done");
                    }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(DisplayUI.this, "Firebase Upload Error", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            TextView sizeView = findViewById(R.id.FileSize);
                            sizeView.setText("File Uploaded With Size: " + String.valueOf(taskSnapshot.getMetadata().getSizeBytes()));
                        }
                    });

                }
            }
        });
    }
}