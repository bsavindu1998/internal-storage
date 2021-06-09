package com.example.externalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "sample.txt";
    EditText textArea;
    Button loadBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textArea = findViewById(R.id.edit_text);
        loadBtn = findViewById(R.id.btnLoad);
        saveBtn = findViewById(R.id.btnSave);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = openFileInput(FILE_NAME);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String text;

                    while ((text = bufferedReader.readLine()) != null) {
                        sb.append(text).append("\n");
                    }

                    textArea.setText(sb.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textArea.getText().toString();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fileOutputStream.write(text.getBytes());

                    textArea.getText().clear();
                    Toast.makeText(MainActivity.this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}