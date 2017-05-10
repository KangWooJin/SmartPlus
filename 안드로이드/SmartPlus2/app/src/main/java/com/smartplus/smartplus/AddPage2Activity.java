package com.smartplus.smartplus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddPage2Activity extends Activity implements TextWatcher, View.OnClickListener, View.OnKeyListener {

    private InputMethodManager imm;

    View in_main_titlebar;
    ImageView iv_titlebar_back;

    TextView tv_addpage2_txt;
    int [] ids;
    String Selected;
    ImageView iv_addpage2_question;
    TextView[] tv_addpage2_arr;
    EditText et_addpage2_find;
    ListView lv_addpage2_list;
    Button bt_addpage2_next;
    LinearLayout ll_addpage2_ll;

    String[] items;




    ArrayList<String> items2;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page2);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        in_main_titlebar = findViewById(R.id.in_main_titlebar);
        iv_titlebar_back = (ImageView)in_main_titlebar.findViewById(R.id.iv_titlebar_back);

        iv_titlebar_back.setOnClickListener(this);

        tv_addpage2_txt=(TextView)findViewById(R.id.tv_addpage2_txt);
        et_addpage2_find=(EditText)findViewById(R.id.et_addpage2_find);
        lv_addpage2_list=(ListView)findViewById(R.id.lv_addpage2_list);
        bt_addpage2_next=(Button)findViewById(R.id.bt_addpage2_next);
        ll_addpage2_ll= (LinearLayout)findViewById(R.id.ll_addpage2_ll);
        iv_addpage2_question=(ImageView)findViewById(R.id.iv_addpage2_question);

        bt_addpage2_next.setOnClickListener(this);
        tv_addpage2_arr = new TextView[7];
        ids = new int[7];

        ids[0]=R.id.tv_addpage2_mintemper;
        ids[1]=R.id.tv_addpage2_maxtemper;
        ids[2]=R.id.tv_addpage2_minpower;
        ids[3]=R.id.tv_addpage2_maxpower;
        ids[4]=R.id.tv_addpage2_isreflesh;
        ids[5]=R.id.tv_addpage2_refleshtemper;
        ids[6]=R.id.tv_addpage2_refleshpower;

        for(int i = 0 ; i < 7 ; i ++)
        {
            tv_addpage2_arr[i]= (TextView)findViewById(ids[i]);

        }

        AirList tmpairList = new AirList();
        int listSize= tmpairList.airs.size();
        items = new String[listSize];

        for(int i = 0 ; i < listSize  ; i ++)
        {
            items[i] = tmpairList.airs.get(i).name;
        }

        items2 = new ArrayList<String>();

        et_addpage2_find.addTextChangedListener(this);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        lv_addpage2_list.setAdapter(adapter);
        lv_addpage2_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selected= ((TextView) view).getText().toString();
                ll_addpage2_ll.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_addpage2_ll.requestLayout();
                bt_addpage2_next.setVisibility(View.VISIBLE);
                changeInfo();
            }
        });

        et_addpage2_find.setOnKeyListener(this);

    }

    private void changeInfo() {

        tv_addpage2_txt.setText(Selected+"\n");


        AirList tmpairList = new AirList();
        int listSize= tmpairList.airs.size();

        for(int i = 0 ; i < listSize ; i ++)
        {
            if (Selected.compareTo(tmpairList.airs.get(i).name) == 0)
            {









                tv_addpage2_arr[0].setText("최소온도 : "+ tmpairList.airs.get(i).nums[0]);
                tv_addpage2_arr[1].setText("최대온도 : "+ tmpairList.airs.get(i).nums[1]);
                tv_addpage2_arr[2].setText("최소세기 : "+ tmpairList.airs.get(i).nums[2]);
                tv_addpage2_arr[3].setText("최대세기 : "+ tmpairList.airs.get(i).nums[3]);
                tv_addpage2_arr[4].setText("갱신유무 : "+ tmpairList.airs.get(i).nums[4]);
                tv_addpage2_arr[5].setText("갱신온도 : "+ tmpairList.airs.get(i).nums[5]);
                tv_addpage2_arr[6].setText("갱신파워 : "+ tmpairList.airs.get(i).nums[6]);

                for(int j = 0 ; j < 7 ; j ++)
                {
                    SC.RST_TEMPER2POWER2OPTION3[j]=tmpairList.airs.get(i).nums[j];
                }

            }

        }


        if(Selected.compareTo("CUSTOM")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.nz_sf_00));
        }
        if(Selected.compareTo("AF15HVZQ1WK")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.aair1));
        }
        if(Selected.compareTo("AF15FVVQ1WK")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.aair2));
        }
        if(Selected.compareTo("AF12FCZZ1EH")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.aair3));
        }
        if(Selected.compareTo("AF-23FVAM1EE")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.aair4));
        }
        if(Selected.compareTo("AF16J7970WFK")==0)
        {
            iv_addpage2_question.setImageDrawable(getResources().getDrawable(R.drawable.airimg));
        }


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(et_addpage2_find.getText().toString().isEmpty())
        {
            adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
            lv_addpage2_list.setAdapter(adapter);
        }
        else
        {
            items2.clear();

            for(int i = 0 ; i< items.length;i++)
            {
                if(items[i].contains(et_addpage2_find.getText().toString().toUpperCase()))
                {
                    items2.add(items[i]);
                }
            }

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items2);
            lv_addpage2_list.setAdapter(adapter);

        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.iv_titlebar_back)
        {
            onBackPressed();
            return;
        }

        if(v.getId() == R.id.bt_addpage2_next)
        {
            nextStage();
        }

    }

    void nextStage()
    {
        if(Selected.compareTo("CUSTOM")==0)
            startActivity(new Intent(getApplicationContext(),CustromActivity.class));
        else
            startActivity(new Intent(getApplicationContext(),AddPage3Activity.class));

    }


    @Override
    protected void onResume() {
        if(SC.STEP==1)
            finish();

        super.onResume();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            imm.hideSoftInputFromWindow(et_addpage2_find.getWindowToken(), 0);

            return true;
        }


        return false;
    }
}
