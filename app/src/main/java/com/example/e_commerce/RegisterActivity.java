package com.example.e_commerce;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, user, phone, pass;
    Button btnRegister;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.100.23/ecommerce/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        requestQueue = Volley.newRequestQueue(this);
        iniUI();
        btnRegister.setOnClickListener(this);
    }

    private void iniUI() {

        email = findViewById(R.id.email);
        user = findViewById(R.id.user);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);

        btnRegister = findViewById(R.id.btnRegister);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnRegister){
            String username = user.getText().toString().trim();
            String correo = email.getText().toString().trim();
            String telefono = phone.getText().toString().trim();
            String contrasena = pass.getText().toString().trim();

            createUser(username, correo, telefono, contrasena);

        }
    }

    public void completedRegister() {
        Intent i = new Intent(this, CompletedRegisterActivity.class );
        startActivity(i);
    }

    private void createUser( final String username, final String correo, final String telefono, final String contrasena) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, "Registro correcto", Toast.LENGTH_SHORT).show();
                        completedRegister();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Ha ocurrido un error, por favor inténtelo de nuevo más tarde" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", correo);
                params.put("telefono", telefono);
                params.put("contrasena", contrasena);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}