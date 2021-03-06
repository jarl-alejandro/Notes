package com.jarl.tareas;

import com.jarl.tareas.data.DBHelper;
import com.jarl.tareas.data.TareasContract.TableName;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ActionBarActivity {

	private SimpleCursorAdapter adapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btnAdd = (ImageButton) findViewById(R.id.btn_add);
        final ListView lista = (ListView) findViewById(R.id.lista);
    
        //Construir el ListView
        final DBHelper helper = new DBHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        
        //int layout = android.R.layout.simple_list_item_1; --> android.R.id.text1
        int layout = R.layout.row_note;
		Cursor c = null;
		String[] from = new String[] { TableName.TITULO };
		int[] to = new int[] { R.id.title };
		int flag = SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
		
		adapter = new SimpleCursorAdapter(this, layout, c, from, to, flag);

        db.close();

		lista.setAdapter(adapter);
		
		btnAdd.setOnClickListener(new OnClickListener(){
	        	
	        @Override
	        public void onClick(View view){
	        	Intent addTareas = new Intent(MainActivity.this, AddTareas.class);
	        	startActivity(addTareas);
	        }
		});
		
		//evento del listView
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				
				String indetifador = Long.toString(id);

				//envio de id
				Intent info = new Intent(MainActivity.this, Informacion.class);
				info.putExtra("iden", indetifador);
				startActivity(info);
			}
		});
    }

	private Cursor recibir() {
		final DBHelper helper = new DBHelper(this);
		final SQLiteDatabase db = helper.getWritableDatabase();
			
		String[] columns = new String[]{ TableName._ID, TableName.TITULO };
		Cursor cursor = db.query(TableName.TABLE_NAME, columns , null, null, null, null, null);
		return cursor;
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		adapter.changeCursor(null);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		adapter.changeCursor(recibir());
	}
}


















