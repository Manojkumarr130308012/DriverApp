package com.example.driverapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driverapp.Adapter.OrderAdaptor;
import com.example.driverapp.Adapter.ProcessingAdaptor;
import com.example.driverapp.DBHelper;
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.Model.Order;
import com.example.driverapp.Model.Processing;
import com.example.driverapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcessingFragment extends Fragment {

    RecyclerView rv;
    private ProcessingAdaptor adapter;
    private ArrayList<Processing> processlist;
    DBHelper dbHelper;
    String id,na,pa;
    ProgressDialog progressDialog;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_processing, container, false);



        Logindata(getActivity(),view);



        return view;
    }




    public void Logindata(Context context,View view) {
        rv=view.findViewById(R.id.process);
        dbHelper=new DBHelper(context);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle(""); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();

        String url = "https://transportapibackend.herokuapp.com/booking/accaggregation?Driverid="+na+"&Bookingstatus="+"Accepted"+"";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET , url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        try {

                            processlist = new ArrayList<>();
                            JSONObject obj = new JSONObject(response.toString());
                            JSONArray heroArray = obj.getJSONArray("response");
                            for (int i = 0; i < heroArray.length(); i++) {
                                JSONObject heroObject = heroArray.getJSONObject(i);
                                String id=heroObject.getString("_id");
                                String Customer=heroObject.getString("Customer");
                                String Pickuploc=heroObject.getString("Pickuploc");
                                String Droploc=heroObject.getString("Droploc");
                                String TotalKm=heroObject.getString("TotalKm");
                                String Vechicaltype=heroObject.getString("Vechicaltype");
                                String Booktime=heroObject.getString("Booktime");
                                String Bookdate=heroObject.getString("Bookdate");
                                String  Amount=heroObject.getString("Amount");
                                String Pickuploclat=heroObject.getString("Pickuploclat");
                                String Pickuploclng=heroObject.getString("Pickuploclng");
                                String Droploclat=heroObject.getString("Droploclat");
                                String Droploclng=heroObject.getString("Droploclng");
                                String Bookingstatus=heroObject.getString("Bookingstatus");
                                String Startotp=heroObject.getString("Startotp");
                                String StartotpTime =heroObject.getString("StartotpTime");
                                String StartTripTime=heroObject.getString("StartTripTime");
                                String ReachDestinationTime=heroObject.getString("ReachDestinationTime");
                                String Endtriptime =heroObject.getString("Endtriptime");
                                JSONArray heroArray1 = heroObject.getJSONArray("vechicalDetails");
                                JSONObject heroObject1 = heroArray1.getJSONObject(0);
                                String vechicalname=heroObject1.getString("Name");
                                JSONArray heroArray2 = heroObject.getJSONArray("CustomerDetails");
                                JSONObject heroObject2 = heroArray2.getJSONObject(0);
                                String customername=heroObject2.getString("username");
                                String customerphone=heroObject2.getString("phone");

                                processlist.add(new Processing(Pickuploc, Droploc,TotalKm,Bookdate,Booktime,Amount,Vechicaltype,vechicalname,customername,0,id,customerphone,StartotpTime,StartTripTime,ReachDestinationTime,Endtriptime,Startotp));
                            }

                        if (context !=null){
                            adapter = new ProcessingAdaptor(processlist, context,view);
                            LinearLayoutManager manager = new LinearLayoutManager(context);
                            rv.setHasFixedSize(true);
                            rv.setLayoutManager(manager);
                            rv.setAdapter(adapter);
                            progressDialog.dismiss();
                        }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }





}