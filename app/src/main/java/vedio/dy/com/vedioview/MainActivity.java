package vedio.dy.com.vedioview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dy.libvedioview.loveview.LoveHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LoveHelper loveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loveHelper  = new LoveHelper((ViewGroup) findViewById(R.id.root));

        findViewById(R.id.btn_add_heart).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_heart){
            loveHelper.showLoveView(v);
        }
    }
}
