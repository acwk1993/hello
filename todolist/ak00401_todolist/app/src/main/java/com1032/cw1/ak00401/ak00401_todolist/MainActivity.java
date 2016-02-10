package com1032.cw1.ak00401.ak00401_todolist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    // declaring variables
    private ArrayList<String> notes;
    private ArrayAdapter<String> noteAdapter;
    private ListView displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayList = (ListView) findViewById(R.id.displaylist);
        notes = new ArrayList<String>();
        readItems();
        noteAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, notes);
        displayList.setAdapter(noteAdapter);

        //sets up the remove listener method call
        setupListViewListener();
    }


    //attaching long click listener to the list view
    private void setupListViewListener() {
    displayList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // removing items from arraylist
            notes.remove(position);

            // refreshing the adapter
            noteAdapter.notifyDataSetChanged();
            writeItems();

            //returns the true consumes long click event
            return true;
        }
    });

    }


    // add notes to the method handler
    public void onAddNotes(View v) {
        EditText description = (EditText) findViewById(R.id.description);
        String itemText = description.getText().toString();
        noteAdapter.add(itemText);
        description.setText("");
        writeItems();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // methods for read and writing data to files
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            notes = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            notes = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
