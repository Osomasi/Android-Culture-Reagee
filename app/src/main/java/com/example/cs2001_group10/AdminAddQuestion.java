package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminAddQuestion extends AppCompatActivity {

    private static final String TAG = "Add_Question" ;
    private EditText question, answer, answer_two, correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_question);

        question = findViewById(R.id.question_box);
        answer = findViewById(R.id.ans_one_input);
        answer_two = findViewById(R.id.ans_two_input);
        correct_answer = findViewById(R.id.correct_ans_input);
        Button button_add = findViewById(R.id.Button_Add);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Add Button Working ");
                Check();
            }
        });

    }

    private void Check() {

//        final String question = this.question.getText().toString().trim();
//        final String answer_one = this.answer.getText().toString().trim();
//        final String answer_two = this.answer_two.getText().toString().trim();
//        final String correct_answer = this.correct_answer.getText().toString().trim();

//        if (question.matches("")) {
//            Toast.makeText(this, "Question has not been entered", Toast.LENGTH_SHORT).show();
//        } else if (answer_one.matches("")) {
//            Toast.makeText(this, "Answer One not been entered", Toast.LENGTH_SHORT).show();
//        } else if (answer_two.matches("")) {
//            Toast.makeText(this, "Answer Two has not been entered", Toast.LENGTH_SHORT).show();
//        } else if (correct_answer.matches("")) {
//            Toast.makeText(this, "Correct Answer has not been entered", Toast.LENGTH_SHORT).show();
//        } else {
//            Insert_Question(question, answer_one, answer_two,correct_answer);
//        }

//        my version
          List<EditText> inputList = Arrays.asList(question,answer,answer_two,correct_answer);
          if(checkInputs(inputList)){
              Insert_Question(question.getText().toString().trim(),
                      answer.getText().toString().trim(),
                      answer_two.getText().toString().trim(),
                      correct_answer.getText().toString().trim());
          }

    }

    private boolean checkInputs(List<EditText> inputs){
        AtomicBoolean ok = new AtomicBoolean(true);
        inputs.forEach(input->{
            if ((input.getText().toString().isEmpty())) {
                input.setError("This field needs to be entered.");
                ok.set(false);
            } else {
                input.setError(null);
            }
        });
        return ok.get();
    }

    private void Insert_Question(final String question, final String answer, final String answer_two, final String correct_answer) {
        Log.d(TAG, "Insert Question: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.URL_INSERT_QUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            String Value = jsonObject.getString("value");

                            if (Value.equals("0")) {
                                Toast.makeText(AdminAddQuestion.this, "Question Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminAddQuestion.this, AdminRequest.class);
                                startActivity(intent);
                            } else if (Value.equals("1")) {
                                Toast.makeText(AdminAddQuestion.this, "Question Exist Already", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AdminAddQuestion.this, "Question Failed to add", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("question", question);
                params.put("answer", answer);
                params.put("answer_two", answer_two);
                params.put("correct_answer", correct_answer);
                params.put("table", AdminRequest.Array_Name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
}
