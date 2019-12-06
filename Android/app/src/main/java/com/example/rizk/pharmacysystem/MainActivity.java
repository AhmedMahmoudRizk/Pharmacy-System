package com.example.rizk.pharmacysystem;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayList<Drug> drugs;
    ArrayAdapter<String > adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setActions();
    }

    private void init() {
        Intent intent=getIntent();
//        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        drugs = new ArrayList<>();
        // get data from database and set drug name.
        setData();
        for (Drug i : drugs)
            list.add(i.getDrugName());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
    }

    private void setActions() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DrugActivity.class);
                intent.putExtra("drugID", drugs.get(i).getDrugID());
                intent.putExtra("drugName", drugs.get(i).getDrugName());
                intent.putExtra("classification", drugs.get(i).getClassification());
                intent.putExtra("concentration", drugs.get(i).getConcentration());
                intent.putExtra("price", drugs.get(i).getPrice());
                intent.putExtra("amount", drugs.get(i).getAmount());
                intent.putExtra("type", drugs.get(i).getType());
                startActivity(intent);
            }
        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "DrugActivity is not available",Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                    adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void setData() {
        String text = "";
        try {
            InputStream is = getAssets().open("drugs.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            String lines[] = text.split("\\r?\\n");
            for (String l : lines){
                String[] splited = l.split("\\s+");
                drugs.add(new Drug(splited[0],splited[1],splited[2],splited[3],splited[4],splited[5],splited[6]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
