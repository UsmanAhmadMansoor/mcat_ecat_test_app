package com.tikrosoft.mcattestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PapersActivity extends AppCompatActivity {

    static PapersActivity papersActivity;

    RecyclerView recyclerView;
    //LinearLayoutManager layoutManager;
    GridLayoutManager layoutManager;
    PaperRecyclerViewAdapter mAdapter;

    JSONArray jsonArray;
    JSONObject jsonObject;

    List<PaperData> list = new ArrayList<PaperData>();

    myDBAdapter helper;

    RequestURL requestURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        helper = new myDBAdapter(this);

        papersActivity = PapersActivity.this;

        requestURL = new RequestURL();

        Intent it = getIntent();
        String val = it.getStringExtra("click");
        if(val != null){

            if(val.equals("0")){
                toolbar.setTitle("ECAT Papers");
                toolbar.setTitleTextColor(Color.WHITE);
                setSupportActionBar(toolbar);
                //LoadPapersFromServer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadPapers.php","1");
                LoadPapersFromServer(requestURL.loadPaperURL,"1");
                helper.saveCatogary("0");
            }else {
                toolbar.setTitle("MCAT Papers");
                toolbar.setTitleTextColor(Color.WHITE);
                setSupportActionBar(toolbar);
                //LoadPapersFromServer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadPapers.php","2");
                LoadPapersFromServer(requestURL.loadPaperURL,"2");
                helper.saveCatogary("1");
            }

        }else{

            if(helper.getCatogary().equals("0")){
                toolbar.setTitle("ECAT Papers");
                toolbar.setTitleTextColor(Color.WHITE);
                setSupportActionBar(toolbar);
                //LoadPapersFromServer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadPapers.php","1");
                LoadPapersFromServer(requestURL.loadPaperURL,"1");
                //helper.saveCatogary("0");
            }else {
                toolbar.setTitle("MCAT Papers");
                toolbar.setTitleTextColor(Color.WHITE);
                setSupportActionBar(toolbar);
                //LoadPapersFromServer("http://192.168.10.25/mCAT_ECAT_TestPreparation/loadPapers.php","2");
                LoadPapersFromServer(requestURL.loadPaperURL,"2");
                //helper.saveCatogary("1");
            }
        }


    }

    private void LoadPapersFromServer(String url, final String cat_id){

        RequestQueue requestQueue = Volley.newRequestQueue(PapersActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        PaperData listData = new PaperData();
                        listData.paper_id = jsonObject.getString("paper_id");
                        //listData.paper_name = jsonObject.getString("paper_name");
                        list.add(listData);
                    }
                    //layoutManager = new LinearLayoutManager(PapersActivity.this);
                    layoutManager = new GridLayoutManager(PapersActivity.this,3);
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter = new PaperRecyclerViewAdapter(PapersActivity.this,list);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PapersActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("cat_id",cat_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static PapersActivity getInstance(){
        return papersActivity;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent it = new Intent(PapersActivity.this,MainActivity.class);
        startActivity(it);
    }
}

