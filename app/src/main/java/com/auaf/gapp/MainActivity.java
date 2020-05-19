package com.auaf.gapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    EditText etHighScore;
    Button btnGetValue;
    TextView tvHighScore;

    int value;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session=SessionManager.getInstance(MainActivity.this);


        etHighScore=findViewById(R.id.etHighScore);
        tvHighScore=findViewById(R.id.tvHighScore);
        tvHighScore.setText("Your High score is  "+ session.getHighScore() );

btnGetValue=findViewById(R.id.btnGetValue);

btnGetValue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        value=Integer.parseInt(etHighScore.getText().toString());
        session.setHighScore(value);

    }
});

    }
}
