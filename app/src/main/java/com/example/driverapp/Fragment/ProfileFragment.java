package com.example.driverapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driverapp.Adapter.ProcessingAdaptor;
import com.example.driverapp.DBHelper;
import com.example.driverapp.EditActivity;
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.Login.LoginActivity;
import com.example.driverapp.Model.Processing;
import com.example.driverapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
LinearLayout Personal,Vechile,Proof;
TextView Email,Blood,Emermob,Adhar,Vech,Licno,Licexp,Vechno,Insno,Insexp,Fcexp,name;
TabLayout tablayout;
TabItem Tabpersonal,Tabvechile,Tabproofs;
FloatingActionButton edit;
ImageView img1,img2,img3,img4,img5,img6;

    DBHelper dbHelper;
    String id,na,pa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


//        Logout=v.findViewById(R.id.logout);
        Personal=v.findViewById(R.id.personal);
        Vechile=v.findViewById(R.id.vechile);
        Proof=v.findViewById(R.id.proof);
//        Name=v.findViewById(R.id.name);
//        Mobile=v.findViewById(R.id.mobile);
//        Address=v.findViewById(R.id.address);
        Email=v.findViewById(R.id.email);
        Blood=v.findViewById(R.id.blood);
        Emermob=v.findViewById(R.id.emermob);
        Adhar=v.findViewById(R.id.adhar);
        Vech=v.findViewById(R.id.trans);
        Licno=v.findViewById(R.id.licno);
        Licexp=v.findViewById(R.id.licexp);
        Vechno=v.findViewById(R.id.vechnum);
        Insno=v.findViewById(R.id.insnum);
        Insexp=v.findViewById(R.id.insexp);
        Fcexp=v.findViewById(R.id.fcexp);
        tablayout=v.findViewById(R.id.tab_layout);
        Tabpersonal=v.findViewById(R.id.tab_personal);
        Tabvechile=v.findViewById(R.id.tab_vechile);
        Tabproofs=v.findViewById(R.id.tab_proof);
        img1=v.findViewById(R.id.image);
        img2=v.findViewById(R.id.img2);
        img3=v.findViewById(R.id.img3);
        img4=v.findViewById(R.id.img4);
        img5=v.findViewById(R.id.img5);
        img6=v.findViewById(R.id.img6);
        edit=v.findViewById(R.id.fab);
        name=v.findViewById(R.id.name1);
        dbHelper=new DBHelper(getContext());
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        initComponent();
        Fetchdata();
        Log.e("ggggg   ","https://transportapibackend.herokuapp.com/driverreg/fetchdata?id="+na);
        Personal.setVisibility(View.VISIBLE);
        Vechile.setVisibility(View.GONE);
        Proof.setVisibility(View.GONE);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit=new Intent(getActivity(), EditActivity.class);
                startActivity(edit);
            }
        });
//

//
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogoutApp();
//            }
//        });
//
        return v;
    }
//
    private void initComponent() {
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("PERSONAL")){
                    Personal.setVisibility(View.VISIBLE);
                    Vechile.setVisibility(View.GONE);
                    Proof.setVisibility(View.GONE);
                }else if (tab.getText().equals("VECHILE")){
                    Personal.setVisibility(View.GONE);
                    Vechile.setVisibility(View.VISIBLE);
                    Proof.setVisibility(View.GONE);
                }else if (tab.getText().equals("PROOFS")){
                    Personal.setVisibility(View.GONE);
                    Vechile.setVisibility(View.GONE);
                    Proof.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }

    public void Fetchdata() {
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();

        String url = "https://transportapibackend.herokuapp.com/driverreg/fetchdata?id="+na;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET , url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            JSONArray heroArray = obj.getJSONArray("response");
                            for (int i = 0; i ==0; i++) {
                                JSONObject heroObject = heroArray.getJSONObject(i);
//                                Name.setText(heroObject.getString("Name"));
                                name.setText(heroObject.getString("Name"));
//                                Mobile.setText(heroObject.getString("Mobileno"));
//                                Address.setText(heroObject.getString("Address"));
                                Email.setText(heroObject.getString("Email"));
                                Blood.setText(heroObject.getString("Blood_Group"));
                                Emermob.setText(heroObject.getString("Emg_Contactno"));
                                Adhar.setText(heroObject.getString("Aadhar_No"));
                                Licno.setText(heroObject.getString("Licence_No"));
                                Licexp.setText(heroObject.getString("Dl_Exp_Date"));
                                Vechno.setText(heroObject.getString("VechicleNum"));
                                Insno.setText(heroObject.getString("insuranceNumber"));
                                Insexp.setText(heroObject.getString("insuranceExpDate"));
                                Fcexp.setText(heroObject.getString("FcExpdate"));
                                Vech.setText(heroObject.getString("Vechicle"));
                                Picasso
                                        .get()
                                        .load(heroObject.getString("Imageprofile"))
                                        .into(img1);
                                Picasso
                                        .get()
                                        .load(heroObject.getString("SmokeTest"))
                                        .into(img2);
                                Picasso
                                        .get()
                                        .load(heroObject.getString("VechicleImage"))
                                        .into(img3);
                                Picasso
                                        .get()
                                        .load(heroObject.getString("Document1"))
                                        .into(img4);
                                Picasso
                                        .get()
                                        .load(heroObject.getString("Document2"))
                                        .into(img5);
                                Picasso
                                        .get()
                                        .load(heroObject.getString("Document3"))
                                        .into(img6);
//                                img2.setText(heroObject.getString("Vechicle"));
//                                img3.setText(heroObject.getString("Vechicle"));
//                                img4.setText(heroObject.getString("Vechicle"));
//                                img5.setText(heroObject.getString("Vechicle"));
//                                img6.setText(heroObject.getString("Vechicle"));
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


//    public void LogoutApp() {
//        new AlertDialog.Builder(getContext())
//                .setTitle("Logout")
//                .setMessage("Are you sure you want to logout?")
//                .setCancelable(true)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        AsyncTask.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    dbHelper.deleteRow();
//                                    Intent i=new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(i);
////                                 finish();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
//
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }



}