package com.example.driverapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driverapp.DBHelper;
import com.example.driverapp.Fragment.OrderFragment;
import com.example.driverapp.Fragment.ProcessingFragment;
import com.example.driverapp.Fragment.ProfileFragment;
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.MainActivity;
import com.example.driverapp.Model.Processing;
import com.example.driverapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ProcessingAdaptor extends RecyclerView.Adapter<ProcessingAdaptor.ViewHolder> {


    private ArrayList<Processing> Model;
    private Context context;
    DBHelper dbHelper;
    String id,na,pa;
ProcessingFragment processingFragment;
View view;

    public ProcessingAdaptor(ArrayList<Processing> courseModalArrayList, Context context,View view) {
        this.Model = courseModalArrayList;
        this.context = context;
        this.view = view;



//        dbHelper=new DBHelper(context);
//        Cursor res = dbHelper.getAllData();
//
//        while (res.moveToNext()) {
//            id = res.getString(0);
//            na = res.getString(1);
//            pa = res.getString(2);
//        }
    }

    @NonNull
    @Override
    public ProcessingAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.process_item, parent, false);
        return new ProcessingAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessingAdaptor.ViewHolder holder, final int position) {
        Processing modal = Model.get(position);
        holder.Source.setText(modal.getPickuploc());
        holder.Destination.setText(modal.getDroploc());
        holder.totalkm.setText(""+modal.getTotalKm());
        holder.amt.setText(""+modal.getAmount());
        holder.date.setText(""+modal.getBookdate());
        holder.time.setText(""+modal.getBooktime());
        holder.vechicletype.setText(""+modal.getVechicaltype());
        holder.vechiclename.setText(""+modal.getVechicalname());
        holder.customer.setText(""+modal.getCustomername());
        holder.customerphone.setText(""+modal.getCustophone());



        Log.e("xxxxxxxxxxxxxxxxxxxxxx",""+modal.getStartotpTime());
        Log.e("xxxxxxxxxxxxxxxxxxxxxx",""+modal.getStartTripTime());
        Log.e("xxxxxxxxxxxxxxxxxxxxxx",""+modal.getReachDestinationTime());
        Log.e("xxxxxxxxxxxxxxxxxxxxxx",""+modal.getEndtriptime());

        if (modal.getDrop() == 0){
            holder.lin.setVisibility(View.GONE);
        }



        if (modal.getStartotpTime().equals("0"))
        {
            holder.otpstart.setVisibility(View.VISIBLE);
            holder.tripstart.setVisibility(View.GONE);
            holder.reachdesination.setVisibility(View.GONE);
            holder.endtrip.setVisibility(View.GONE);
        }else if(modal.getStartTripTime().equals("0")){
            holder.otpstart.setVisibility(View.GONE);
            holder.tripstart.setVisibility(View.VISIBLE);
            holder.reachdesination.setVisibility(View.GONE);
            holder.endtrip.setVisibility(View.GONE);
        }else if(modal.getReachDestinationTime().equals("0")){
            holder.otpstart.setVisibility(View.GONE);
            holder.tripstart.setVisibility(View.GONE);
            holder.reachdesination.setVisibility(View.VISIBLE);
            holder.endtrip.setVisibility(View.GONE);
        }else if(modal.getEndtriptime().equals("0")){
            holder.otpstart.setVisibility(View.GONE);
            holder.tripstart.setVisibility(View.GONE);
            holder.reachdesination.setVisibility(View.GONE);
            holder.endtrip.setVisibility(View.VISIBLE);
        }




        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modal.getStartotp().equals(""+holder.otp.getText().toString())){
                    updatetostartotp(modal.getId());
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Wrong OTP");
                    builder.setMessage("Enter Correct OTP...");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }

            }
        });
        holder.starttripbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetostarttime(modal.getId());
            }
        });
        holder.reachdesinationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetoreachtime(modal.getId());
            }
        });
        holder.endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateendtime(modal.getId());
            }
        });
        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modal.getDrop()==0){
                    holder.lin.setVisibility(View.VISIBLE);
                    modal.setDrop(1);
                }else{
                    holder.lin.setVisibility(View.GONE);
                    modal.setDrop(0);
                }
            }
        });

        String id=modal.getId();

    }

    @Override
    public int getItemCount() {

        return Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView Source, Destination,totalkm,amt,date,time,vechicletype,vechiclename,customer,customerphone;
        private ImageView dropdown;
        private EditText otp;
        private Button accept,starttripbtn,reachdesinationbtn,endbtn;
        private LinearLayout lin,otpstart,tripstart,reachdesination,endtrip;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Source = itemView.findViewById(R.id.sourceorder);
            Destination = itemView.findViewById(R.id.destinationorder);
            totalkm = itemView.findViewById(R.id.totalkm);
            amt = itemView.findViewById(R.id.amt);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            vechicletype = itemView.findViewById(R.id.vechicletype);
            vechiclename = itemView.findViewById(R.id.vechiclename);
            customer = itemView.findViewById(R.id.customer);
            dropdown = itemView.findViewById(R.id.dropdown);
            lin = itemView.findViewById(R.id.lin);
            otpstart = itemView.findViewById(R.id.otpstart);
            tripstart = itemView.findViewById(R.id.tripstart);
            customerphone = itemView.findViewById(R.id.customerphone);
            reachdesination = itemView.findViewById(R.id.reachdesination);
            endtrip = itemView.findViewById(R.id.endtrip);
            accept = itemView.findViewById(R.id.accept);
            starttripbtn = itemView.findViewById(R.id.starttripbtn);
            reachdesinationbtn = itemView.findViewById(R.id.reachdesinationbtn);
            endbtn = itemView.findViewById(R.id.endbtn);
            otp = itemView.findViewById(R.id.otp);



        }
    }

    private void  updatetostartotp(String id) {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat  simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
        String formattedDate = simpledateformat.format(c.getTime());
        try {
            object.put("StartotpTime",""+formattedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("StartotpTime",""+formattedDate);
        String url = "https://transportapibackend.herokuapp.com/booking/update?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        dialog("OTP Verified");
                        processingFragment=new ProcessingFragment();
                        processingFragment.Logindata(context,view);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void  updatetostarttime(String id) {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat  simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
        String formattedDate = simpledateformat.format(c.getTime());
        try {
            object.put("StartTripTime",""+formattedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("StartTripTime",""+formattedDate);
        String url = "https://transportapibackend.herokuapp.com/booking/update?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        dialog("Your Trip is Started");
                        processingFragment=new ProcessingFragment();
                        processingFragment.Logindata(context,view);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void  updatetoreachtime(String id) {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat  simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
        String formattedDate = simpledateformat.format(c.getTime());
        try {
            object.put("ReachDestinationTime",""+formattedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("ReachDestinationTime",""+formattedDate);
        String url = "https://transportapibackend.herokuapp.com/booking/update?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        dialog("Reached Destination");

                        processingFragment=new ProcessingFragment();
                        processingFragment.Logindata(context,view);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void  updateendtime(String id) {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat  simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
        String formattedDate = simpledateformat.format(c.getTime());
        try {
            object.put("Endtriptime",""+formattedDate);
            object.put("Bookingstatus","Closed");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("Endtriptime",""+formattedDate);
        String url = "https://transportapibackend.herokuapp.com/booking/amount_update?_id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        dialog("Trip Ended");
                        processingFragment=new ProcessingFragment();
                        processingFragment.Logindata(context,view);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    void dialog(String Text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setMessage(""+Text);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

}
