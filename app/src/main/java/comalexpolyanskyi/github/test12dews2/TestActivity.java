package comalexpolyanskyi.github.test12dews2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Алексей on 30.03.2015.
 */
public class TestActivity extends Activity {
    private int maxI = 120;
    private int x = 0;
    private int y = 0;
    private String countTime = null;
    private ArrayList<HashMap<String, String>> result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Intent intent = getIntent();
        countTime = getString(R.string.countTime);
        maxI = intent.getIntExtra(countTime, 0);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(maxI);
        result =  new ArrayList<HashMap<String, String>>();
        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i <= maxI; i++){
                    try {
                        progressBar.setProgress(i);
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                }
                Intent i = new Intent();
                Cash.putResult(result);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        t.start();
        getRandomValue();
    }
    private void getRandomValue(){
        Random rand = new Random();
        x = rand.nextInt(100);
        y = rand.nextInt(100);
        TextView textX = (TextView) findViewById(R.id.Xchislo);
        TextView textY = (TextView) findViewById(R.id.Ychislo);
        textX.setText(x+"");
        textY.setText(y+"");
    }
    public void onClickOk(View v){
        EditText editText = (EditText) findViewById(R.id.editText);
        int otvet = 0;
        String rezOtveta = "";
        try {
            if((x+y) == Integer.parseInt(editText.getText().toString())){
                otvet = Integer.parseInt(editText.getText().toString());
                Toast.makeText(this, "Верно", Toast.LENGTH_LONG).show();
                rezOtveta = "Верно";
            }else{
                otvet = Integer.parseInt(editText.getText().toString());
                Toast.makeText(this, "Неверно", Toast.LENGTH_LONG).show();
                rezOtveta = "Неверно";
            }
        }catch (Exception e){
            Toast.makeText(this, "Неверно", Toast.LENGTH_LONG).show();
            rezOtveta = "Неверно";
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("prim", x + " + " + y + " = " + otvet);
        map.put("rez", rezOtveta);
        result.add(map);
        getRandomValue();
        editText.setText("");
    }
}
