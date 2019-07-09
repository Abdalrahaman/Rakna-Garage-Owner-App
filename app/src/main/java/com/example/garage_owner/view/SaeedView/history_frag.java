package com.example.garage_owner.view.SaeedView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garage_owner.R;
import com.example.garage_owner.presenter.item2data;
import com.example.garage_owner.view.HazemView.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class history_frag extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.history_frag_saeed, container, false);
        recyclerView = view.findViewById(R.id.myhistoryrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        creating adapter object and set it to recyclerView
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), MainActivity.item2dataList);
        recyclerView.setAdapter(historyAdapter);
        return view;
    }


}
