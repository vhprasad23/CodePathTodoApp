package com.codepath.codepathtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editItem;
    Button btnEditSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String itemName = getIntent().getStringExtra( "todoItemName" );
        final int itemIndex = getIntent().getIntExtra( "todoItemIndex", 0 );

        editItem = ( EditText ) findViewById( R.id.editItem );


        editItem.setText( itemName );

        btnEditSave = ( Button ) findViewById( R.id.btnEditSave );
        btnEditSave.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra( "editedItemIndex", itemIndex );
                data.putExtra( "editedItemName", editItem.getText().toString() );
                setResult( RESULT_OK, data );
                finish();
            }
        } );

    }


}
