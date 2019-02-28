package com.voshodnerd.dynamiclist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.voshodnerd.dynamiclist.model.ImageModel;
import com.voshodnerd.dynamiclist.rest.ApiUtils;
import com.voshodnerd.dynamiclist.rest.SOService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    Button mbutton;
    ArrayList<ImageModel> myDataset;
    EditText meditText;
    SOService mService;



    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getSOService();

        mAdapter = new ImageAdapter(this, new ArrayList<ImageModel>(0), new ImageAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });



        mbutton= (Button)findViewById(R.id.buttonAdd);
        meditText= (EditText) findViewById(R.id.my_edit_text);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)
        myDataset= new ArrayList<>();

        myDataset.add(new ImageModel(4,4,"photo1","nono","none"));
        myDataset.add(new ImageModel(4,4,"photo2","nono","none"));
        myDataset.add(new ImageModel(4,4,"photo3","nono","none"));



        //mAdapter = new ImageAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        loadAnswers();

    }

    public void loadAnswers() {
        // RxJava Implementation

        mService.getImageModelList().enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {

                if(response.isSuccessful()) {

                    mAdapter.updateImageModelList(response.body());
                    Log.d("AnswersPresenter", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                showErrorMessage();
                Log.d("AnswersPresenter", "error loading from API");

            }
        });

    }

    public void showErrorMessage() {
        Toast.makeText(this, "Error loading posts", Toast.LENGTH_SHORT).show();
    }



}
