package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var05;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {
    TextView rezText;
    Button returnBtn;

    class Respond implements View.OnClickListener{
        android.content.Context Context;
        String Result;
        public Respond (Context context, String result){
            Context = context;
            Result = result;
        }

        @Override
        public void onClick(View view) {
            if(((Button)view).getText().toString().equals("Return")){
                Intent intent = new Intent(Context, PracticalTest01Var05MainActivity.class);
                intent.putExtra("respond", Result);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);
        rezText = findViewById(R.id.rezInput);
        returnBtn = findViewById(R.id.returnBtn);

        Intent intentFromParent = getIntent();
        String data = intentFromParent.getStringExtra("sum");
        Log.d("REZ", data);

        String[] numbers = data.split(" \\+ ");

        int sum = 0;
        for (String t : numbers){
            sum += Integer.parseInt(t);
        }
        data = data + " = " + String.valueOf(sum);
        rezText.setText(data);

        returnBtn.setOnClickListener(new Respond(this, String.valueOf(sum)));


    }
}
