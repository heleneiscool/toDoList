package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private EditText itemET;
    private Button btn;
    private RecyclerView selectedList;
    private RecyclerView itemsList;
    private int i=0;

    private ArrayList<String> selected;
    private ArrayAdapter<LauncherActivity.ListItem> adapterSelected;
    private ArrayList<String> items;
    private ArrayAdapter<LauncherActivity.ListItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);
        selectedList = findViewById(R.id.selected_list);
        selected = FileHelper.readData(this,1);
        adapterSelected = new ArrayAdapter<LauncherActivity.ListItem>(this,R.id.selected_list,selected);
        selectedList.setAdapter(adapterSelected);

        itemsList = findViewById(R.id.items_list);
        items = FileHelper.readData(this,0);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        adapter = new ArrayAdapter<LauncherActivity.ListItem>(this,R.id.items_list,items);

        itemsList.setAdapter(adapter);

        btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);
        selectedList.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                String itemEntered = itemET.getText().toString();
                adapter.add(itemEntered);
                itemET.setText("");

                FileHelper.writeData(items, this);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (i<4) {
            String item = items.get(position);

            items.remove(position);
            adapter.notifyDataSetChanged();
            FileHelper.writeData(items, this);

            selected.add(item);
            adapterSelected.notifyDataSetChanged();
            //FileHelper.writeData(items, this);

            FileHelper.readData(this,0);
            Toast.makeText(this, "Item Selected", Toast.LENGTH_SHORT).show();
            i++;
        }

        else{
            Toast.makeText(this, "Focus on completing max of 4 items at a time", Toast.LENGTH_LONG).show();
        }
        if(i==4){
            findViewById(R.id.items_list).setVisibility(view.INVISIBLE);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selected.remove(position);
        adapterSelected.notifyDataSetChanged();
        FileHelper.writeData(selected, this);
        Toast.makeText(this, "Item completed", Toast.LENGTH_SHORT).show();
        i--;
        findViewById(R.id.items_list).setVisibility(view.VISIBLE);
        return false;
    }

    //ItemTouchHelper helper = new ItemTouchHelper;


}
