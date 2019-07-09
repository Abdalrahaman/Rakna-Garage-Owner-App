package com.example.garage_owner.view.SaeedView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_owner.R;
import com.example.garage_owner.model.SaeedModel.Config;
import com.example.garage_owner.model.SaeedModel.NotificationUtils;
import com.example.garage_owner.presenter.notifyItems;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class notify_frag extends Fragment {
    private static final String TAG = notify_frag.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    List<notifyItems> itemdataList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_frag, container, false);
        recyclerView = view.findViewById(R.id.NotifyrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemdataList = new ArrayList<>();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    String time = intent.getStringExtra("time");
                    String img = intent.getStringExtra("circleimg");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    itemdataList.add(new notifyItems(img, null, message, time));
                    Log.d("itemdataList:",itemdataList.size()+"");
                    NotificationAdapter notificationAdapter  = new NotificationAdapter(getActivity(),itemdataList);
                    recyclerView.setAdapter(notificationAdapter);

                }
            }
        };

        displayFirebaseRegId();

        return view;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

    }

    @Override
    public void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getActivity());
    }
}
