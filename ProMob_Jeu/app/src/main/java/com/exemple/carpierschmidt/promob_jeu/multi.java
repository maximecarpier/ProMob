package com.exemple.carpierschmidt.promob_jeu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class multi extends AppCompatActivity {

    Button discover, wifi, send;
    ListView list;
    TextView status, msg;
    EditText write;

    WifiManager wifimanager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mchannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        init();
        wifi_button();
    }

    private void init(){
        discover = findViewById(R.id.discover);
        wifi = findViewById(R.id.onOff);
        send = findViewById(R.id.sendButton);
        list = findViewById(R.id.peerListView);
        status = findViewById(R.id.connectionStatus);
        msg = findViewById(R.id.readMsg);
        write = findViewById(R.id.writeMsg);

        wifimanager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setText("wifi " + wifimanager.isWifiEnabled());

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mchannel = mManager.initialize(this, getMainLooper(),null);

        mReceiver = new WifiDirectBroadcastReceiver(mManager, mchannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void wifi_button() {
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifimanager.isWifiEnabled()) {
                    wifimanager.setWifiEnabled(false);
                } else {
                    wifimanager.setWifiEnabled(true);
                }
            }
        });

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.discoverPeers(mchannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        status.setText("Discovery en cours");
                    }

                    @Override
                    public void onFailure(int reason) {
                        status.setText("Discovery failed");
                    }
                });
            }
        });
    }


}
