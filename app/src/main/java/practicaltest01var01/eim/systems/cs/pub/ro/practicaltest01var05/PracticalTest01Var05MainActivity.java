package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var05;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {
    Button add, compute;
    EditText input;
    TextView rez;
    String SUM;
    String lastTerms;
    Intent service;
    Receiver receiver;
    IntentFilter startedServiceIntentFilter;
    final public static String ACTION_SUM = "practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var05.act";

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("Sum");
            String date = intent.getStringExtra("Data");
            Toast.makeText(context, data + " " + date, Toast.LENGTH_LONG).show();
        }
    }
    class Listener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.add:
                    try {
                        String data = null;
                        int number = Integer.parseInt(input.getText().toString());
                        if (rez.getText().toString() != null && !rez.getText().toString().isEmpty()){
                            data = rez.getText().toString() + " + " + input.getText().toString();
                        }
                        else{
                            data = input.getText().toString();
                        }
                        rez.setText(data);

                    }catch (NumberFormatException e){

                    }
            }
        }
    }

    class Change implements View.OnClickListener{

        Context Context;

        public  Change(Context context){
            Context = context;
        }

        @Override
        public void onClick(View view) {
//            if (lastTerms != null && !lastTerms.isEmpty() && SUM.equals(rez.getText().toString())){
//                Log.d("BHEEEEEI", lastTerms);
//                Toast.makeText(Context, SUM, Toast.LENGTH_LONG).show();
//            }else {

                Intent intent = new Intent(Context, PracticalTest01Var05SecondaryActivity.class);
                intent.putExtra("sum", rez.getText().toString());
                startActivity(intent);
//            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);
        add = findViewById(R.id.add);
        compute = findViewById(R.id.compute);
        input = findViewById(R.id.input);
        rez = findViewById(R.id.rez);

        add.setOnClickListener(new Listener());
        compute.setOnClickListener(new Change(this));

        //raspuns
        Intent intentFromParent = getIntent();
        String data = intentFromParent.getStringExtra("respond");
        if (data != null && !data.isEmpty()){
            SUM = data;
            lastTerms = rez.getText().toString();
            rez.setText(data);
            Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        }

        if (data != null && !data.isEmpty() && Integer.parseInt(data) > 10){
            service = new Intent(this, PracticalTest01Var05Service.class);
            service.putExtra("VAL", rez.getText().toString());
            startService(service);
        }

        // broadcast receiver
        receiver = new Receiver();

        // TODO: exercise 8b - create an instance of an IntentFilter
        // with all available actions contained within the broadcast intents sent by the service
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(ACTION_SUM);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("rez", rez.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String data = null;

        if (savedInstanceState.getString("rez") != null) {
            rez.setText(savedInstanceState.getString("rez"));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (service != null)
            stopService(service);
        super.onDestroy();
    }
}
