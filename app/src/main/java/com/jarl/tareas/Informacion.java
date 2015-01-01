package com.jarl.tareas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jarl.tareas.data.DBHelper;
import com.jarl.tareas.data.TareasContract.TableName;

public class Informacion extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);
				
        final TextView titleTexto = (TextView) findViewById(R.id.title_texto);
        final TextView contentTexto = (TextView) findViewById(R.id.content_texto);

		final ImageButton delete = (ImageButton) findViewById(R.id.delete);
		
		Intent intent = getIntent();
		final String memberID = intent.getStringExtra("iden");

		//Convierto de String a Long ._ID
		//long member_id = Long.parseLong(memberID);
		
		//Nuevo codigo...
		final DBHelper helper = new DBHelper(this);
		final SQLiteDatabase db = helper.getWritableDatabase();
		
		String[] columns = new String[]{ TableName._ID, TableName.TITULO, TableName.CONTENIDO };
		
		Cursor cursor = db.query(TableName.TABLE_NAME, columns, TableName._ID + "=" + memberID , null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();

			String n = cursor.getString(1);
			String ct = cursor.getString(2);

            titleTexto.setText(n);
            contentTexto.setText(ct);
		}
		//db.close();
		
		delete.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View view){
				db.delete(TableName.TABLE_NAME, TableName._ID + "=" + memberID, null);
				Intent home = new Intent(Informacion.this, MainActivity.class);
				startActivity(home);
				db.close();
			}
		});
	}
}


















