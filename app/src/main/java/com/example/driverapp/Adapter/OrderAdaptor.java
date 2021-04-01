package com.example.driverapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.Model.Order;
import com.example.driverapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class OrderAdaptor extends RecyclerView.Adapter<OrderAdaptor.ViewHolder> {


    private ArrayList<Order> Model;
    private Context context;
    DBHelper dbHelper;
    String id,na,pa;
    View view;


    public OrderAdaptor(ArrayList<Order> courseModalArrayList, Context context,View view) {
        this.Model = courseModalArrayList;
        this.context = context;
        this.view = view;



        dbHelper=new DBHelper(this.context);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
    }

    @NonNull
    @Override
    public OrderAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        return new OrderAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdaptor.ViewHolder holder, final int position) {
        Order modal = Model.get(position);
        holder.Source.setText(modal.getPickuploc());
        holder.Destination.setText(modal.getDroploc());
        holder.totalkm.setText(""+modal.getTotalKm());
        holder.amt.setText(""+modal.getAmount());
        holder.date.setText(""+modal.getBookdate());
        holder.time.setText(""+modal.getBooktime());
        holder.vechicletype.setText(""+modal.getVechicaltype());
        holder.vechiclename.setText(""+modal.getVechicalname());
        holder.customer.setText(""+modal.getCustomername());

        if (modal.getDrop() == 0){
            holder.lin.setVisibility(View.GONE);
        }

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

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Accept Order");
                builder.setMessage("Are you want to Accept the Order?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Yes(modal.getId());
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    @Override
    public int getItemCount() {

        return Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView Source, Destination,totalkm,amt,date,time,vechicletype,vechiclename,customer;
        private ImageView dropdown;
        private LinearLayout lin;
        private Button accept,cancel;


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
            accept = itemView.findViewById(R.id.accept);
            cancel = itemView.findViewById(R.id.cancel);


        }
    }

    private void Yes(String id) {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();

        String otp= new DecimalFormat("0000").format(new Random().nextInt(999999));

        try {
            object.put("Bookingstatus","Accepted");
            object.put("Startotp",""+otp);
            object.put("Driverid",""+na);
            object.put("StartotpTime","0");
            object.put("StartTripTime","0");
            object.put("ReachDestinationTime","0");
            object.put("Endtriptime","0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://transportapibackend.herokuapp.com/booking/update?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
//                        OrderFragment f2 = new OrderFragment();
//                        FragmentTransaction transaction = contextgetSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_container, f2);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                        OrderFragment orderFragment=new OrderFragment();
                        orderFragment.Logindata(context,view);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static interface CallBack {
    }
}
