package ir.mrdota.touristplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ir.mrdota.touristplace.R;
import ir.mrdota.touristplace.datails;
import ir.mrdota.touristplace.models.model_main_menu;

public class adpter_main_menu_L extends ArrayAdapter<model_main_menu> {
    ArrayList<model_main_menu> list = new ArrayList<>();
    Context context;

    public adpter_main_menu_L(Context context, ArrayList<model_main_menu> list) {
        super(context, R.layout.item_main_menu, list);
        this.context = context;
        this.list = list;


    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        model_main_menu co = list.get(position);
        viewholder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_main_menu, parent, false);
            holder = new viewholder();
            holder.ln_item = (LinearLayout)convertView.findViewById(R.id.ln_item);
            holder.txt_item = (TextView) convertView.findViewById(R.id.txt_item);
            holder.img_item = (ImageView)convertView.findViewById(R.id.img_item);



            convertView.setTag(holder);

        } else {
            holder = (viewholder) convertView.getTag();

        }
        holder.fill(co);


        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    private class viewholder {
    public ImageView img_item;
    public TextView txt_item;
    LinearLayout  ln_item ;

    private void fill(model_main_menu cco) {
       ln_item.setTag(cco.getCategory());

        try {
            String imgsrc = cco.getSrc_ic_img().toString();


           // img_item.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + imgsrc));
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("img/"+imgsrc);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            img_item.setImageBitmap(bitmap);
        }
        catch (Exception ex)
        {

            try {
                AssetManager assetManager = context.getAssets();
                InputStream is = null;
                is = assetManager.open("img/n.png");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                img_item.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


//            img_item.setImageResource( R.drawable.n);
//            context.getAssets().open("")
        }
        txt_item.setText(Html.fromHtml(cco.getCategory()));
         ln_item.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

               Intent in =   new Intent(context,datails.class);
                 in.putExtra("title",ln_item.getTag()+"");
                 in.putExtra("type", "0");

                 context.startActivity(in);
             }
         });


    }


}


}