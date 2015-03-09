package com.example.filepersistencetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edit = (EditText)findViewById(R.id.edit);
		
		String inputText = load();
		
		if (!TextUtils.isEmpty(inputText)) {
			edit.setText(inputText);
			edit.setSelection(inputText.length());
			Toast.makeText(this, "Restoring successed", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		String inputText = edit.getText().toString();
		
		if (inputText.equals("")) {
			Log.d("MainActivity", "null input");
			return;
		}
		
		Log.d("MainActivity", "INPUT:" + inputText);
		save(inputText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void save(String inputText) {
		FileOutputStream out = null;
		BufferedWriter writer = null;
		
		try {
			
			out = openFileOutput("data", Context.MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(inputText);
			Log.d("MainActivity", "Saved");
			
		} catch (IOException e) {
			// TODO: handle exception
			Log.d("MainActivity", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e2) {
				// TODO: handle exception
				Log.d("MainActivity", e2.getMessage());
				e2.printStackTrace();
			}
		}
	}
	
	private String load() {
		FileInputStream in = null;
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();
		
		try {
//			in = openFileInput("data_MainActivity");
			in = openFileInput("data");
			reader = new BufferedReader(new InputStreamReader(in));
			
			String linesString = "";
			while ((linesString = reader.readLine()) != null) {
				content.append(linesString);
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}
}
