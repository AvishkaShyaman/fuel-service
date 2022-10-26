package com.smart.fuelservicemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
import com.smart.fuelservicemobile.API.APIInterface;
import com.smart.fuelservicemobile.Models.SearchShedRequest;
import com.smart.fuelservicemobile.Models.SearchShedResponse;
import com.smart.fuelservicemobile.Models.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchShedsActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sheds);

        searchView = (SearchView) findViewById(R.id.searchShed);
        listView = (ListView) findViewById(R.id.shedList);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

        Gson gson = new Gson();

        // search event handler
        String user = mPrefs.getString("user", "");
        User getU  = gson.fromJson(user, User.class);

        list = new ArrayList<>();
        HashMap<String, String> listMap = new HashMap<String, String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String value = String.valueOf(id);

                Intent intent = new Intent(SearchShedsActivity.this, ShedActivity.class);
                intent.putExtra("id", value);
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(listMap.containsValue(query)){

                    adapter.getFilter().filter(query);


                    SearchShedRequest shedSearchRequest = new SearchShedRequest(query, getU.getId());
                    Call<SearchShedResponse> call1 = apiInterface.searchShed(shedSearchRequest);
                    call1.enqueue(new Callback<SearchShedResponse>() {

                        @Override
                        public void onResponse(Call<SearchShedResponse> call, Response<SearchShedResponse> response) {
                            list = new ArrayList<>();
                            HashMap<String, String> listMap = new HashMap<String, String>();

                            list.addAll(listMap.values());

                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<SearchShedResponse> call, Throwable t) {

                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });
    };
};