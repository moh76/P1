package ir.mrdota.touristplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                }catch (Exception x){

                }finally {
                    startActivity(new Intent (splash.this,main_page.class));
                    finish();
                }
            }
        }.start();
    }
}
