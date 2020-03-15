package com.example.hackathon;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;
    ArrayList<String> arlist, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        arlist = new ArrayList<>( );
        name = new ArrayList<>( );
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        EditText mEdit;
        mEdit = findViewById(R.id.editText);
        final Editable x = mEdit.getText();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect(x.toString()).get();
                    Elements links = doc.select("input");

                    Elements labels = doc.select("label");
                    for (Element link : links) {
                        if(!link.attr("type").equals("hidden") && !link.attr("name").equals("website")) {
                            for (Element l: labels) {
                                if (link.attr("id").equals(l.attr("for"))) {
                                    arlist.add((l.wholeText()).toLowerCase());
                                    name.add(link.attr("name").toLowerCase());
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent transfer = new Intent(MainActivity.this, MainActivity2.class);
                        transfer.putStringArrayListExtra("arlist", arlist);
                        transfer.putStringArrayListExtra("label", name);
                        startActivity(transfer);
                    }
                });
            }
        }).start();
    }
}