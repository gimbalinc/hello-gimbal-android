package com.gimbal.hello_gimbal_android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;


public class MainActivity extends ActionBarActivity {

    private PlaceManager placeManager;
    private PlaceEventListener placeEventListener;
    private ArrayAdapter<String> listAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        listAdapter.add("Setting Gimbal API Key");
        listAdapter.notifyDataSetChanged();
        Gimbal.setApiKey(this.getApplication(), "YOUR_API_KEY_HERE");

        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                super.onVisitStart(visit);
                listAdapter.add(String.format("Start Visit for %s", visit.getPlace().getName()));
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onVisitEnd(Visit visit) {
                super.onVisitEnd(visit);
                listAdapter.add(String.format("End Visit for %s", visit.getPlace().getName()));
                listAdapter.notifyDataSetChanged();
            }
        };

        placeManager = PlaceManager.getInstance();
        placeManager.addListener(placeEventListener);
        placeManager.startMonitoring();

        CommunicationManager.getInstance().startReceivingCommunications();
    }

}
