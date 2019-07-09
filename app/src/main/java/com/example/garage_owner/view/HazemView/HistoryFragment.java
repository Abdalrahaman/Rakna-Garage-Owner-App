package com.example.garage_owner.view.HazemView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.garage_owner.R;
import com.facebook.login.LoginManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


Button logout;
String testId;
    public HistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logout = (Button)getActivity().findViewById(R.id.logout);
        testId = MainActivity.id;
//        testId = "1";
        Log.d("fragmentid", ""+testId);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testId .equals("2") ) {////facebook logout
                    LoginManager.getInstance().logOut();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().finish();
                    startActivity(intent);


                }
                else if (testId.equals("1")){//login logout
                    LoginActivity.editor.putString(getString(R.string.checkbox), "False");
                    LoginActivity.editor.commit();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
//
                }


            }
        });
    }
}
