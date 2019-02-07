package com.example.hpuser.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemToDo;
    private Button btn;
    private ListView toDoList;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemToDo = findViewById(R.id.item_edit);
        btn = findViewById(R.id.add_item);
        toDoList = findViewById(R.id.items_list);

        items = FileHelper.readData(this);

        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txt, items);
        toDoList.setAdapter(adapter);

        btn.setOnClickListener(this);
        toDoList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_item:
            String item = itemToDo.getText().toString();
            adapter.add(item);
            itemToDo.setText("");
            FileHelper.writeData(items, this);
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //show update box

        showUpdateBox(items.get(i),i);

    }

    public void showUpdateBox (String oldItem, final int index){
        Toast.makeText(this, "item clicked", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Update Box");
        dialog.setContentView(R.layout.update_box);
        TextView txtUpdate= (TextView)dialog.findViewById(R.id.txt_update);
        txtUpdate.setText("Update text");
        txtUpdate.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText= (EditText) dialog.findViewById(R.id.input);
        editText.setText(oldItem);
        Button bt = (Button) dialog.findViewById(R.id.edit_btn);
       bt.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View view) {
              items.set(index, editText.getText().toString());
              adapter.notifyDataSetChanged();
               dialog.dismiss();
            }

        });
    dialog.show();
    }


    public void deleteMe(View view){
        Button bt=(Button)view;

        final int position = toDoList.getPositionForView((View) view.getParent());
        items.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Item deleted "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }
}