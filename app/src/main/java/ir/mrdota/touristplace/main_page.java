package ir.mrdota.touristplace;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mrdota.touristplace.DB.opendb;

import ir.mrdota.touristplace.adapter.adapter_main_menu;
import ir.mrdota.touristplace.adapter.adpter_main_menu_L;
 import ir.mrdota.touristplace.models.model_main_menu;

public class main_page extends AppCompatActivity {


    ArrayList<model_main_menu> list = new ArrayList<>();
    ListView lv ;
    ImageView cancel ,search_ic,filter_ic;
    LinearLayout ln_radio ;
    RadioGroup radiogp ;
    RadioButton radiobtn;
    EditText srch;
    ArrayAdapter<model_main_menu> adpt;
    RecyclerView rec_search  ;
    adapter_main_menu adptrec;
    ImageView fav ;
    boolean search =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        lv = findViewById(R.id.lv);
        srch = findViewById(R.id.srch);
        cancel=findViewById(R.id.cancel);
        search_ic =findViewById(R.id.search_ic);
        filter_ic =findViewById(R.id.filter);
        ln_radio =findViewById(R.id.ln_radio);
        radiogp=findViewById(R.id.radiogp);
        rec_search=findViewById(R.id.rec_search);
        rec_search.setLayoutManager(new LinearLayoutManager(this));
        fav=findViewById(R.id.fav);



        fill_list(0,null);







        search_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            out_search();

            }
        });
        srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                search();

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        radiogp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                search();
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_page.this,datails.class).putExtra("type","1"));
            }
        });
    }
void fill_list(int type,String search){
    list =  new opendb(this).maindet(type+0,search,null,this);
    if (list.size()==0)
        Toast.makeText(this, getResources().getString(R.string.Err_not_found), Toast.LENGTH_SHORT).show();

        if (type==2 || type==0) {
            adpt = new adpter_main_menu_L(this, list);
            lv.setAdapter(adpt);
        }
        else if (type==3 ||type==4) {
            adptrec = new adapter_main_menu(list, this,true ,false);
            rec_search.setAdapter(adptrec);
        }

}
void search(){
    search = true ;
    list = new ArrayList<>();

    cancel.setImageResource(R.drawable.ic_clear_red_24dp);
    search_ic.setImageResource(R.drawable.ic_search_red_24dp);
    filter_ic.setImageResource(R.drawable.ic_filter_list_red_24dp);

    ln_radio.setVisibility(View.VISIBLE);
    int id = radiogp.getCheckedRadioButtonId();
    radiobtn=findViewById(id);
    String B = radiobtn.getTag().toString().trim();

    int type = 2;
    if (B.contains("category")) {
        type =2 ;
    }
    else if (B.contains("title")) {
        type=3;
    }
    else if (B.contains("text")) {
        type=4;
    }

    String A = srch.getText().toString().trim();

    if (!A.equals(""))
        fill_list(type+0,A+"");
    if (type ==2) {
        lv.setVisibility(View.VISIBLE);
        rec_search.setVisibility(View.GONE);
        lv.setAdapter(adpt);
        adpt.notifyDataSetChanged();

    }else if (type==3||type==4){
        try {

            lv.setVisibility(View.GONE);
            rec_search.setVisibility(View.VISIBLE);
            rec_search.setAdapter(adptrec );
            adptrec.notifyDataSetChanged();
        }catch (Exception e){

        }
    }
}
void out_search(){
    hideKeyboard(main_page.this);
    srch.setText("");
    srch.clearFocus();
    fill_list(0,null);


    cancel.setImageResource(R.drawable.ic_clear_black_24dp);
    search_ic.setImageResource(R.drawable.ic_search_black_24dp);
    filter_ic.setImageResource(R.drawable.ic_filter_list_black_24dp);

    ln_radio.setVisibility(View.GONE);
    lv.setVisibility(View.VISIBLE);
    rec_search.setVisibility(View.GONE);
    search = false;
}

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (search)
            out_search();
        else
            finish();
    }
}
