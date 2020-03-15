package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.text.Editable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    EditText str;
    TextView t1;
    Button btn;
    static int j=0;
    ArrayList<String> ip, label;
    final StringBuilder builder = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int count = 50;

        final StringBuilder builder = new StringBuilder();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t1 = (TextView) findViewById(R.id.result);

        ip = getIntent().getStringArrayListExtra("arlist");
        label = getIntent().getStringArrayListExtra("label");
        final RelativeLayout lm = (RelativeLayout) findViewById(R.id.activity_main);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for(int i=0;i<ip.size();i++) {
            builder.append(ip.get(i)+": \n");
           /* RelativeLayout ll = new RelativeLayout(this);
            TextView product = new TextView(this);
            product.setText(ip.get(i));
            product.layout();
            ll.addView(product);
            lm.addView(ll);*/
        }
        t1.setText(builder.toString());

    }
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String str = "";
                    ArrayList<String> resp = new ArrayList<>(ip.size());
                    String[] s = (result.get(0)).split(" ");

                    System.out.println(result.get(0).split(" "));
                    for(int i=0;i<s.length;i++){
                        System.out.println(ip.get(j)+" "+s[i] + " " + (j));
                        if((s[i].trim()).equalsIgnoreCase((ip.get(j)).trim()))
                        {
                            System.out.println("here");
                            str += ip.get(j) + ": ";
                            j = j + 1;
                        }
                        else
                            str += s[i] + "\n";
                    }

                    t1.setText(str);
                    str = "";
                }
                break;
        }
    }

}
