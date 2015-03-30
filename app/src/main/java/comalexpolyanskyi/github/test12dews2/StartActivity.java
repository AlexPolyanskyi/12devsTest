package comalexpolyanskyi.github.test12dews2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;


public class StartActivity extends Activity {
    private  boolean isSumSelected = false;
    private  boolean isMulSelected = false;
    private  boolean isDivSelected = false;
    private  boolean isDecSelected = false;
    private String summaString = null;
    private String delenie = null;
    private String umnogenie = null;
    private  String vichitanie = null;
    private String testMode = null;
    private String comfimUncorrect = null;
    private String countQuest = null;
    private String countTime = null;
    private boolean isCountCorrect = true;
    private boolean isTimeCorrect = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Тест");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("История");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Настройки");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        initString();
    }
    private void initString(){
        summaString = getString(R.string.summa);
        delenie = getString(R.string.delenie);
        umnogenie = getString(R.string.umnogenie);
        vichitanie = getString(R.string.vichitanie);
        testMode = getString(R.string.testMode);
        comfimUncorrect = getString(R.string.comfimUncorrect);
        countQuest = getString(R.string.countQuest);
        countTime = getString(R.string.countTime);
    }
    public void onSelectDec(View v){
        if(isDecSelected){
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.min);
            isDecSelected = false;
        }else{
            isDecSelected = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.minfocus);
        }
    }
    public void onSelectDiv(View v){
        if(isDivSelected){
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.div);
            isDivSelected = false;
        }else{
            isDivSelected = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.divfocus);
        }
    }
    public void onSelectMul(View v){
        if(isMulSelected){
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.mul);
            isMulSelected = false;
        }else{
            isMulSelected = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.mulfocus);
        }
    }
    public void onSelectSum(View v){
        if(isSumSelected){
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.sum);
            isSumSelected = false;
        }else{
            isSumSelected = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.sumfocus);
        }
    }
    private Intent setDataForTest(){
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(summaString, isSumSelected);
        intent.putExtra(delenie, isDivSelected);
        intent.putExtra(umnogenie, isMulSelected);
        intent.putExtra(vichitanie, isDecSelected);
        intent.putExtra(testMode, getTestMode((RadioButton) findViewById(R.id.speed)));
        intent.putExtra(comfimUncorrect, getComfUncorrect((CheckBox) findViewById(R.id.checkResult)));
        intent.putExtra(countQuest, getCountQuest());
        intent.putExtra(countTime, getTime());
        return intent;
    }
    private boolean getTestMode(RadioButton speed ){
        if(speed.isChecked()){
            return false;
        } else {
            return true;
        }
    }
    private boolean getComfUncorrect(CheckBox check){
        if(check.isChecked()){
            return false;
        } else {
            return true;
        }
    }

    private int getCountQuest(){
        int count = 0;
        isCountCorrect = true;
        EditText text = (EditText) findViewById(R.id.count);
        try {
            count = Integer.parseInt(text.getText().toString());
        }catch (Exception e){
            count = 0;
            isCountCorrect = false;
        }
        return  count;
    }

    private int getTime(){
        isTimeCorrect = true;
        EditText text = (EditText) findViewById(R.id.setTime);
        Spinner spinner = (Spinner) findViewById(R.id.changeTime);
        int time = 0;
        try {
            if(spinner.getSelectedItemPosition() == 0){
                time = Integer.parseInt(text.getText().toString());
            }else {
                time = 60 * Integer.parseInt(text.getText().toString());
            }
        }catch (Exception e){
            time = 0;
            isTimeCorrect = false;
        }
        return time;
    }


    //TODO пока пусть будет без этого
    private int getRazX(){
        EditText text = (EditText) findViewById(R.id.count);
        return Integer.parseInt(text.getText().toString());
    }

    private int getRazY(){
        EditText text = (EditText) findViewById(R.id.count);
        return Integer.parseInt(text.getText().toString());
    }

    public void onStartTest(View v){
        Intent intent =  setDataForTest();
        if( (isDecSelected || isDivSelected || isMulSelected || isSumSelected) && (isTimeCorrect) && (isCountCorrect)){
            startActivityForResult(intent, 1);
        }else if(!isTimeCorrect){
            Toast.makeText(this, "Вы не ввели время", Toast.LENGTH_LONG).show();
        }else if(!isCountCorrect){
            Toast.makeText(this, "Введите количество", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Выберите хотя бы одно арефмитическое действие!!!!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ListView list = (ListView) findViewById(R.id.listResult);
            String[] arrayNameItems = { "prim", "rez"};
            int[] arrayItems = {R.id.primer, R.id.result};
            SimpleAdapter adapter = new SimpleAdapter(this, Cash.getResult(), R.layout.list_view_item, arrayNameItems, arrayItems );
            list.setAdapter(adapter);

            return;
        }
    }
}
