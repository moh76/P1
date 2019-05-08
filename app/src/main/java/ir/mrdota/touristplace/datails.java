package ir.mrdota.touristplace;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mrdota.touristplace.DB.opendb;
import ir.mrdota.touristplace.adapter.adapter_main_menu;
import ir.mrdota.touristplace.models.model_main_menu;

public class datails extends AppCompatActivity {

    RecyclerView rec ;

    ArrayList<model_main_menu> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datails);
        Bundle b = getIntent().getExtras() ;
        rec = findViewById(R.id.rec);
        String type =b.get("type").toString() ;
        rec.setLayoutManager(new LinearLayoutManager(this));





        if (type.equals("0")){/*****             from category adapter            ****////
            list = new opendb(this).maindet(1,null,b.get("title")+"",this);
            rec.setAdapter(new adapter_main_menu(list , this,false ,false));
            if (list.size()==0)
                Toast.makeText(this, getResources().getString(R.string.Err_not_found), Toast.LENGTH_SHORT).show();
        }




        else if(type.equals("1")){/*****             from main page fav           ****////
            list = new opendb(this).maindet(5,null,null,this);
            rec.setAdapter(new adapter_main_menu(list , this,false,true ));
            if (list.size()==0)
                Toast.makeText(this, getResources().getString(R.string.empty_list), Toast.LENGTH_SHORT).show();
        }






    }
}
