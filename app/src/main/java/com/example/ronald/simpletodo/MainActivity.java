package com.example.ronald.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.FileUriExposedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static int cod=20;
    public final static String Item_Text="itemText";
    public final static String Item_pos="itemposition";
    ArrayList<String> items;
    ArrayAdapter<String> itemAdap;
    ListView lvitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items=new ArrayList<>();
        readItems();
        itemAdap=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvitems=(ListView) findViewById(R.id.lvitems);
        lvitems.setAdapter(itemAdap);

        setupListViewListener();
    }
    public void onAddItems(View v){
       EditText txtItems=(EditText) findViewById(R.id.txtItems);
        String itemsText=txtItems.getText().toString();
        itemAdap.add(itemsText);
        txtItems.setText("");
        Toast.makeText(getApplicationContext(),"Article ajoute a la liste",Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener(){
        Log.i("MainActivity","configuration du listener en mode liste");
        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MainActivity","article retiré de la liste"+i);
                items.remove(i);
                itemAdap.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent p =new Intent(MainActivity.this,EditItemsActivity.class);
                p.putExtra(Item_Text,items.get(i));
                p.putExtra(Item_pos,i);
                startActivityForResult(p,cod);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==cod){
            String updateEtidView = data.getExtras().getString(Item_Text);
            int posi = data.getExtras().getInt(Item_pos);
            items.set(posi,updateEtidView);
            itemAdap.notifyDataSetChanged();
            writeItems();
            Toast.makeText(getApplicationContext(),"Article modifier avec succes",Toast.LENGTH_SHORT).show();

        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"todo.txt");
    }
    private void readItems(){

         try{
             items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
         }  catch (IOException e){ Log.e("MainActivity","Error reading",e);

                                items=new ArrayList<>();
                                 }

    }

    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error reading",e);
        }
    }
}