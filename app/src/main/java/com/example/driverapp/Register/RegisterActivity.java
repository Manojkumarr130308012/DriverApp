package com.example.driverapp.Register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driverapp.HttpsTrustManager;
import com.example.driverapp.Login.LoginActivity;
import com.example.driverapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
StepView step;
    FloatingActionButton Previous,Next;
    Button register;
    ImageView profile,smoke,vechimg,doc1,doc2,doc3,b1,b2,b3;
    Uri filePath1,filePath2,filepath3,filepath4,filepath5,filepath6;
    private final int PICK_IMAGE_REQUEST = 1,PICK_IMAGE_REQUEST1 = 2,PICK_IMAGE_REQUEST2 = 3,
            PICK_IMAGE_REQUEST3 = 4,PICK_IMAGE_REQUEST4 = 5,PICK_IMAGE_REQUEST5= 6;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<String> arr = new ArrayList<String>() {{
        add("Personal Details");
        add("Vechile Details");
        add("Proof Documents");
    }};
    LinearLayout lin1,lin2,lin3;
    MaterialSpinner spinner1,spinner2;
    String Profile,Smoke,Vechimg,Doc1,Doc2,Doc3,Name,Mobile,Address,Email,Password,Blood,Emgcon,Adhar,Licnum,
            Licexd,Vecnum,Insnum,Insexd,Fcdate,vechiletypeid,vechiletype,Vechid,Vechname,Vechile;
    EditText etname,etmobile,etaddress,etemail,etpassword,etblood,etecont,etadhar,
            etlicno,etlicexpd,etvechnum,etinsnum,etinsedate,etfcexpd;
    private int mYear1, mMonth1, mDay1;
    private int mYear2, mMonth2, mDay2;
    private int mYear3, mMonth3, mDay3;
    List<String> Vechiletypeid = new ArrayList<>();
    List<String> Vechiletype = new ArrayList<>();
    List<String> vechid = new ArrayList<>();
    List<String> vechname = new ArrayList<>();
    List<String> vechilee = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        step=findViewById(R.id.step_view);
        profile=findViewById(R.id.profile);
        smoke=findViewById(R.id.smoke);
        vechimg=findViewById(R.id.vechimg);
        doc1=findViewById(R.id.doc1);
        doc2=findViewById(R.id.doc2);
        doc3=findViewById(R.id.doc3);
        Previous = findViewById(R.id.Previ);
        Next = findViewById(R.id.Next);
        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);
        lin3 = findViewById(R.id.lin3);
        register=findViewById(R.id.register);
        etname=findViewById(R.id.etname);
        etmobile=findViewById(R.id.etmobile);
        etaddress=findViewById(R.id.etaddress);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        etblood=findViewById(R.id.etblood);
        etecont=findViewById(R.id.etecont);
        etadhar=findViewById(R.id.etadhar);
        etlicno=findViewById(R.id.etlicno);
        etlicexpd=findViewById(R.id.etlicexpd);
        etvechnum=findViewById(R.id.etvechnum);
        etinsnum=findViewById(R.id.etinsnum);
        etinsedate=findViewById(R.id.etinsedate);
        etfcexpd=findViewById(R.id.etfcexpd);
        spinner1=findViewById(R.id.vectype);
        spinner2=findViewById(R.id.vechile);
        b1=findViewById(R.id.b1);
        b2 =findViewById(R.id.b2);
        b3=findViewById(R.id.b3);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Log.e("count",""+step.getCurrentStep());

        lin1.setVisibility(View.VISIBLE);
        lin2.setVisibility(View.GONE);
        lin3.setVisibility(View.GONE);
        Previous.setVisibility(View.GONE);
        Next.setVisibility(View.VISIBLE);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
            }
        });
        smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST1);
            }
        });
        vechimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST2);
            }
        });
        doc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST3);
            }
        });
        doc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST4);
            }
        });
        doc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST5);
            }
        });
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step.go(step.getCurrentStep() - 1, true);
                Log.e("count",""+step.getCurrentStep());


                if (step.getCurrentStep() == 0){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    Previous.setVisibility(View.GONE);
                    Next.setVisibility(View.VISIBLE);
                }
                else if (step.getCurrentStep() == 1){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    Previous.setVisibility(View.GONE);
                    Next.setVisibility(View.VISIBLE);
                } else if (step.getCurrentStep() == 2){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.VISIBLE);
                    lin3.setVisibility(View.GONE);
                    Previous.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "Sucesss", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step.go(step.getCurrentStep() + 1, true);
                Log.e("count",""+step.getCurrentStep());


                if (step.getCurrentStep() == 0){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.VISIBLE);
                    lin3.setVisibility(View.GONE);
                    Previous.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.VISIBLE);
                }
               else if (step.getCurrentStep() == 1){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.VISIBLE);
                    Previous.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.GONE);
                } else if (step.getCurrentStep() == 2){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.VISIBLE);
                    Previous.setVisibility(View.VISIBLE);
                    Next.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Sucesss", Toast.LENGTH_SHORT).show();

                }



            }
        });
        getVechiletype();

        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

         @Override
         public void onItemSelected(MaterialSpinner view, int i, long id, Object item) {
             if (i > -1) {
                 vechiletype = Vechiletype.get(i).toString();
                 getVechile(vechiletype);
             } else {
                 vechiletype = "";
             }

         }

     });
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(MaterialSpinner view, int i, long id, Object item) {
                if (i > -1) {
                    Toast.makeText(RegisterActivity.this, "ifffff", Toast.LENGTH_SHORT).show();
                    Vechid = vechid.get(i).toString();
                    Vechname = vechname.get(i).toString();
                } else {
                    Toast.makeText(RegisterActivity.this, "ELSEEE", Toast.LENGTH_SHORT).show();
                    Vechid = "";
                    Vechname = "";
                }

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == b1) {
                    final Calendar c = Calendar.getInstance();
                    mYear1 = c.get(Calendar.YEAR);
                    mMonth1 = c.get(Calendar.MONTH);
                    mDay1 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    etlicexpd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear1, mMonth1, mDay1);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == b2) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear2 = c.get(Calendar.YEAR);
                    mMonth2 = c.get(Calendar.MONTH);
                    mDay2 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    etinsedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear2, mMonth2, mDay2);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == b3) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear3 = c.get(Calendar.YEAR);
                    mMonth3 = c.get(Calendar.MONTH);
                    mDay3 = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    etfcexpd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear3, mMonth3, mDay3);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });

        step.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .animationType(StepView.ANIMATION_ALL )
                .selectedCircleColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.colorAccent))
                .steps(arr)
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .commit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=etname.getText().toString();
                Mobile=etmobile.getText().toString();
                Address=etaddress.getText().toString();
                Email=etemail.getText().toString();
                Password=etpassword.getText().toString();
                Blood=etblood.getText().toString();
                Emgcon=etecont.getText().toString();
                Adhar=etadhar.getText().toString();
                Licnum=etlicno.getText().toString();
                Licexd=etlicexpd.getText().toString();
                Vecnum=etvechnum.getText().toString();
                Insnum=etinsnum.getText().toString();
                Insexd=etinsedate.getText().toString();
                Fcdate=etfcexpd.getText().toString();
                Registerdata();

            }
        });
    }

    private void Registerdata() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        HttpsTrustManager.allowAllSSL();
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            JSONObject object = new JSONObject();
        try {
            object.put("Name",""+Name);
            object.put("Mobileno",""+Mobile);
            object.put("Address",""+Address);
            object.put("Email",""+Email);
            object.put("Password",""+Password);
            object.put("Blood_Group",""+Blood);
            object.put("Emg_Contactno",""+Emgcon);
            object.put("Aadhar_No",""+Adhar);
            object.put("Licence_No",""+Licnum);
            object.put("Dl_Exp_Date",""+Licexd);
            object.put("VechicleNum",""+Vecnum);
            object.put("insuranceNumber",""+Insnum);
            object.put("insuranceExpDate",""+Insexd);
            object.put("FcExpdate",""+Fcdate);
            object.put("Vechicle_Type",""+vechiletype);
            object.put("Vechicle",""+Vechid);
            object.put("Imageprofile",""+Profile);
            object.put("SmokeTest",""+Smoke);
            object.put("VechicleImage",""+Vechimg);
            object.put("Document1",""+Doc1);
            object.put("Document2",""+Doc2);
            object.put("Document3",""+Doc3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            String url = "https://transportapibackend.herokuapp.com/driverreg/register";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Ddddddddddddddddd",response.toString());
                        Intent i=new Intent(RegisterActivity.this, LoginActivity.class);
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
        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null)
        {
            filePath1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                profile.setImageBitmap(bitmap);
                uploadImage1();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST1
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            filePath2 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                smoke.setImageBitmap(bitmap);
                uploadImage2();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST2
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filepath3 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath3);
                vechimg.setImageBitmap(bitmap);
                uploadImage3();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST3
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filepath4 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath4);
                doc1.setImageBitmap(bitmap);
                uploadImage4();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST4
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            filepath5 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath5);
                doc2.setImageBitmap(bitmap);
                uploadImage5();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST5
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filepath6 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath6);
                doc3.setImageBitmap(bitmap);
                uploadImage6();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage1()
    {
        if (filePath1 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filePath1).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String photoStringLink = uri.toString();
                                            Log.e("urlimage", photoStringLink);
                                            Profile=photoStringLink;
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage2()
    {
        if (filePath2 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filePath2).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    Log.e("urlimage", photoStringLink);
                                    Smoke=photoStringLink;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage3()
    {
        if (filepath3 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filepath3).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    Log.e("urlimage", photoStringLink);
                                    Vechimg=photoStringLink;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage4()
    {
        if (filepath4 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filepath4).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    Log.e("urlimage", photoStringLink);
                                    Doc1=photoStringLink;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage5()
    {
        if (filepath5 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filepath5).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    Log.e("urlimage", photoStringLink);
                                    Doc2=photoStringLink;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage6()
    {
        if (filepath6 != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("sssssssss",""+ref);
            ref.putFile(filepath6).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    Log.e("urlimage", photoStringLink);
                                    Doc3=photoStringLink;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }
    public void getVechiletype() {
            Vechiletype.add("open");
            Vechiletype.add("close");
            Vechiletype.add("any");
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, Vechiletype);
        _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(_Adapter);
    }

    public void getVechile(String vechiletypeid) {
        vechid=new ArrayList<>();
        vechname=new ArrayList<>();
        String url1 ="https://transportapibackend.herokuapp.com/drivervechicle/aggregation?VechicleType="+vechiletypeid;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd",""+ response);
                            JSONArray jObject1 = response.getJSONArray("response");
                            for(int i = 0; i<jObject1.length(); i++){
                                JSONObject jsonObject = jObject1.getJSONObject((i));
                                vechid.add(jsonObject.getString("_id"));
                                vechname.add(jsonObject.getString("Name"));
                            }
                            ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, vechname);
                            _Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(_Adapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
















