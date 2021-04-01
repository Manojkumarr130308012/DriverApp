package com.example.driverapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driverapp.DBHelper;
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.MainActivity;
import com.example.driverapp.R;
import com.example.driverapp.Register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Button Login,register;
    EditText etName,etPassword;
    DBHelper dbHelper;
    String Name,Password;
    String message, Storeuser, Storemob;
String status;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etName=findViewById(R.id.et_name);
        etPassword=findViewById(R.id.et_password);
        register=findViewById(R.id.register);
        dbHelper = new DBHelper(this);

        Login=findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = etName.getText().toString().trim();
                Password = etPassword.getText().toString().trim();
                if (validateLogin(Name, Password)) {
                    Log.e("ffffffffffffffff", "" + Name);
                    Log.e("ffffffffffffffff", "" + Password);
                    Logindata();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
             startActivity(i);
            }
        });
    }
    public boolean validateLogin(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void Logindata() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        JSONObject object = new JSONObject();
        try {
            object.put("Mobileno",""+Name);
            object.put("Password",""+Password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://transportapibackend.herokuapp.com/driverreg/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
//                        JSONObject jsonobj = new JSONObject(response);
//                    JSONObject jObject = response.getJSONObject("response");
                        try {
                            status= (String) response.get("status");
                            message = (String) response.get("msg");
                            if(status.equals("1")){
                                Storeuser =  (String) response.get("token");
                                JSONObject jObject = response.getJSONObject("user");
                                Storemob = (String) jObject.get("_id");
                                dbHelper.insertData(Storemob, Storeuser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    Log.e("ddddddddd", "" + message);
                    Log.e("ddddddddd", "" + Storeuser);
                    Log.e("ddddddddd", "" + Storemob);
//                    dataParsed = dataParsed + singleParsed + "\n";
                        Intent i=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


//    public class fetchData extends AsyncTask<Void, Void, Void> {
//        String data = "";
//        String dataParsed = "";
//        String singleParsed = "";
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            if (Name == null || Password == null) {
//
//                Toast.makeText(LoginActivity.this, " Fill the Fields", Toast.LENGTH_SHORT).show();
//
//            } else {
//
//                try {
//
//                    URL url = new URL("https://transportapibackend.herokuapp.com/driverreg/login?Mobileno=" + Name + "&Password=" + Password);
//
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line = "";
//                    while (line != null) {
//                        line = bufferedReader.readLine();
//                        data = data + line;
//                    }
//
//                    Log.e("dddddddddddddd", "" + data);
//                    JSONObject jsonobj = new JSONObject(data);
////                    JSONObject jObject = jsonobj.getJSONObject("data");
////                    message = (String) jsonobj.get("message");
//                    singleParsed = (String) jsonobj.get("_id");
//                    Storeuser = (String) jsonobj.get("Mobileno");
//                    Storemob = (String) jsonobj.get("Password");
//
//                    Log.e("ddddddddd", "" + singleParsed);
//                    Log.e("ddddddddd", "" + Storeuser);
//                    Log.e("ddddddddd", "" + message);
//                    dataParsed = dataParsed + singleParsed + "\n";
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (!singleParsed.equals("")) {
//                dbHelper.insertData(Storeuser, singleParsed);
//                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                i.putExtra("user", "" + Storeuser);
//                startActivity(i);
//
//            } else {
//                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
}