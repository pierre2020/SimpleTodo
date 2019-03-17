package com.example.ronald.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class EditItemsActivity extends AppCompatActivity {

    TextView etitemText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);
        etitemText=(TextView) findViewById(R.id.etItems);
        etitemText.setText(getIntent().getStringExtra(MainActivity.Item_Text));
        position=getIntent().getIntExtra(MainActivity.Item_pos,0);
        getSupportActionBar().setTitle("Edit Item");
    }
    public void onSaveItems(View v){
        Intent t=new Intent();
        t.putExtra(MainActivity.Item_Text,etitemText.getText().toString());
        t.putExtra(MainActivity.Item_pos,position);
        setResult(RESULT_OK,t);
        finish();
    }
}
