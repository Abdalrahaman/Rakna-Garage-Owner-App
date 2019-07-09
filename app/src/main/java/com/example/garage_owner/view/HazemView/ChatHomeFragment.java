package com.example.garage_owner.view.HazemView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.garage_owner.R;
import com.example.garage_owner.view.Islam.StartActivityChat;


public class ChatHomeFragment extends Fragment {

    Button btn_lgn_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_home, container, false);

        btn_lgn_chat = view.findViewById(R.id.btn_loginChat_Rakna);
        btn_lgn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartActivityChat.class);
                startActivity(intent);
            }
        });

        return view ;
    }

}
