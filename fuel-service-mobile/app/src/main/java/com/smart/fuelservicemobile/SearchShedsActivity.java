package com.smart.fuelservicemobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.smart.fuelservicemobile.API.APIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchShedsActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    JSONObject apiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sheds);

        searchView = (SearchView) findViewById(R.id.searchShed);
        listView = (ListView) findViewById(R.id.shedList);

        list = new ArrayList<>();
        HashMap<String, String> listMap = new HashMap<String, String>();
//        listMap.put("1", "Kotahena Shed IOC");
//        listMap.put("2", "Maradana Shed");
//        listMap.put("3", "Modara Shed");

//        list.addAll(listMap.values());

//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String value = String.valueOf(id);
                String SelectedItem;

                Log.i("value", value);


                try {

                    JSONArray data = apiResponse.getJSONArray("data");

                    for (int i = 0 ; i < data.length(); i++) {

                        if (i == Integer.parseInt(value)) {
                            JSONObject obj = data.getJSONObject(i);
                            SelectedItem = obj.getString("id");

                            Intent intent = new Intent(SearchShedsActivity.this, ShedActivity.class);
                            intent.putExtra("id", SelectedItem);
                            startActivity(intent);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.i("search", query);
                list.clear();



                    RequestQueue queue = Volley.newRequestQueue(SearchShedsActivity.this);

                    APIClient apiRequest = new APIClient(SearchShedsActivity.this, Request.Method.GET, "sheds/search/" + query, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                list.clear();

                                apiResponse = response;

                                JSONArray data = response.getJSONArray("data");

                                for (int i = 0 ; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    String id = obj.getString("id");
                                    String location = obj.getString("location");

                                    listMap.put(id, location);

                                }

                                list.addAll(listMap.values());
                                adapter = new ArrayAdapter(SearchShedsActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        list);

                                listView.setAdapter(adapter);



                            } catch (Exception e) {
                                Toast.makeText(SearchShedsActivity.this, "search shed Failed", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SearchShedsActivity.this, "search shed Failed", Toast.LENGTH_LONG).show();
                        }
                    });

                    queue.add(apiRequest.request());


                if(listMap.containsValue(query)){

                    adapter.getFilter().filter(query);
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