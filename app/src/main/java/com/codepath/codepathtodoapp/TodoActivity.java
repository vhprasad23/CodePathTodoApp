package com.codepath.codepathtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private List<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems = ( ListView ) findViewById( R.id.lvItems );

        readItems();

        itemsAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, items );

        lvItems.setAdapter( itemsAdapter );


        etItem = ( EditText ) findViewById( R.id.etItem );
        setupListViewListener();
        setupEditViewListener();


    }

    private void setupEditViewListener()
    {
        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id )
            {
                Intent intent = new Intent( TodoActivity.this, EditItemActivity.class );
                intent.putExtra( "todoItemIndex", position );
                intent.putExtra( "todoItemName", items.get( position ) );
                startActivityForResult( intent, REQUEST_CODE );

            }

        } );
    }


    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove( position );
                itemsAdapter.notifyDataSetChanged();
                writeItems( );
                return true;
            }
        });
    }

    public void addItem(View view) {
        String itemToAdd = etItem.getText().toString();
        if( !itemToAdd.isEmpty() )
        {
            items.add( itemToAdd );
            writeItems();
            Toast.makeText( this, itemToAdd, Toast.LENGTH_SHORT ).show();
        }
        etItem.setText("");
    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File( filesDir, "todo.txt" );

        try{
            items = new ArrayList<>( FileUtils.readLines( todoFile )  );
        }
        catch ( IOException e)
        {
            items = new ArrayList<>();
        }

    }

    private void writeItems( )
    {
        File filesDir = getFilesDir();
        File todoFile = new File( filesDir, "todo.txt" );

        try
        {
            FileUtils.writeLines( todoFile, items );
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE )
        {
            String editedItemName = data.getExtras().getString( "editedItemName" );
            int editedItemIndex = data.getExtras().getInt( "editedItemIndex" );

            items.set( editedItemIndex, editedItemName );
            itemsAdapter.notifyDataSetChanged();
            writeItems( );
        }


    }




}
