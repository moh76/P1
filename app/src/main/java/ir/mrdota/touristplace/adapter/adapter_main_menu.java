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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ir.mrdota.touristplace.DB.opendb;
import ir.mrdota.touristplace.R;
import ir.mrdota.touristplace.models.model_main_menu;

public class adapter_main_menu extends RecyclerView.Adapter<adapter_main_menu.MyViewHolder> {

    Context context;
    ArrayList<model_main_menu> list;
    boolean search ;
    boolean fav;


    public adapter_main_menu(ArrayList<model_main_menu> list, Context context,boolean search,boolean fav ) {
        this.context = context;
        this.search=search;
        this.list = list;
        this.fav=fav ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_details, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final model_main_menu model = this.list.get(position);


          int fav =  model.getFav();


        try {




            if (search) {
                holder.txtfrom.setText("from " + model.getCategory());
                holder.title.setText(Html.fromHtml(model.getTitle()));
                holder.text.setText(Html.fromHtml(model.getText()));
            } else if (this.fav) {
                holder.txtfrom.setText("from " + model.getCategory());
                holder.title.setText(model.getTitle());
                holder.text.setText(model.getText());
            }
            else {
                holder.txtfrom.setVisibility(View.GONE);
                holder.title.setText(model.getTitle());
                holder.text.setText(model.getText());

            }
            if (fav==0){

                holder.fav.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

            }else if (fav==1){

                holder.fav.setImageResource(R.drawable.ic_bookmark_black_24dp);

            }
            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int fav =  model.getFav();
                    int _id = model.get_id();

                    if (fav==0){

                        new opendb(context).update_fav(1,_id);
                        holder.fav.setImageResource(R.drawable.ic_bookmark_black_24dp);
                        model.setFav(1);




                    }else if (fav==1){
                        new opendb(context).update_fav(0,_id);
                        holder.fav.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                        model.setFav(0);
                        list = new opendb(context).maindet(5,null,null,context);
                        notifyDataSetChanged();
                        if (list.size()==0)
                            Toast.makeText(context, context.getResources().getString(R.string.empty_list), Toast.LENGTH_SHORT).show();
                    }


                }
            });

//            holder.img.setTag(model.getSrc_image());
//            String imgsrc = holder.img.getTag().toString();
//            holder.img.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + model.getSrc_image()));
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("img/"+model.getSrc_image());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.img.setImageBitmap(bitmap);

            holder.shr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent Share=new Intent(Intent.ACTION_SEND);
                    Share.setType("text/plan");
                    Share.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name + ": " + model.getTitle().toString());
                    Share.putExtra(Intent.EXTRA_TEXT,model.getTitle().toString() + " \n\nfrom "+model.getCategory() +"\n\n"+model.getText().toString());
                    context.startActivity(Intent.createChooser(Share,context.getResources().getString(R.string.shr_wh)));
                }
            });
        }
        catch (Exception e0){
         }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder{


        TextView txtfrom ;
        TextView title ;
        TextView text ;
        ImageView img ;
        ImageView fav;
        ImageView shr ;
        public MyViewHolder(View v ){
            super(v);
            if(v != null) {

                txtfrom=v.findViewById(R.id.txtfrom);
                title = (TextView) v.findViewById(R.id.title);
                text = (TextView) v.findViewById(R.id.txt);
                img = (ImageView)v.findViewById(R.id.img);
                fav= v.findViewById(R.id.fav);
                shr=v.findViewById(R.id.shr);

            }

        }


    }
}
