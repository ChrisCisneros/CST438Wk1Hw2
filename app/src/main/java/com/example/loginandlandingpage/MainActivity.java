package com.example.loginandlandingpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View view) {
        TextView text = findViewById(R.id.textView);
        TextView user = findViewById(R.id.username);
        TextView pass = findViewById(R.id.password);
        text.setText(user.getText() + " " + pass.getText());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>>call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts)
                {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId()+ "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    text.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });


    }
}