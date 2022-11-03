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

import java.util.ArrayList;
import java.util.HashMap;

public class SearchShedsActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sheds);

        searchView = (SearchView) findViewById(R.id.searchShed);
        listView = (ListView) findViewById(R.id.shedList);

        list = new ArrayList<>();
        HashMap<String, String> listMap = new HashMap<String, String>();
        listMap.put("1", "Kotahena Shed IOC");
        listMap.put("2", "Maradana Shed");
        listMap.put("3", "Modara Shed");

        list.addAll(listMap.values());

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String value = String.valueOf(id);

                Intent intent = new Intent(SearchShedsActivity.this, ShedActivity.class);
                intent.putExtra("id", "63582d930ad19da429815c4f");
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.i("search", query);

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