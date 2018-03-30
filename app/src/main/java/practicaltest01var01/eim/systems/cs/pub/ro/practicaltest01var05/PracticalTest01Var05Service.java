package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var05;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PracticalTest01Var05Service extends Service {
    final public static String ACTION_SUM = "practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var05.act";



    class MyRunnable extends Thread {
        String Data;
        android.content.Context Context;

        MyRunnable(String data, Context context){
            Data = data;
            Context = context;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    Log.d("Heeey", Data);


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent1 = new Intent();
                    intent1.setAction(ACTION_SUM);
                    intent1.putExtra("Data", dateFormat.format(date));
                    intent1.putExtra("Sum", Data);
                    Context.sendBroadcast(intent1);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(Context, "Helloooo", Toast.LENGTH_LONG).show();
            }

        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyTAG", "OnStartCommand");
        super.onStartCommand(intent, flags, startId);
        String data = intent.getStringExtra("VAL");

        MyRunnable rn = new MyRunnable(data, this);
        rn.start();
        return START_REDELIVER_INTENT;
    }

//    @Override
//    public void onDestroy() {
//        Log.d("MyTAG", "OnDestroy");
//        super.onDestroy();
//    }

}

