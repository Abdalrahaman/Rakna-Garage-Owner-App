package com.example.garage_owner.view.HazemView;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.garage_owner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaknaClientFragment extends Fragment {


    public RaknaClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rakna_client, container, false);
        return view ;
    }

}
