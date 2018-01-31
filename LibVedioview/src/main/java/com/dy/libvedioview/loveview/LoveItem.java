package com.dy.libvedioview.loveview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dy.libvedioview.R;

import java.util.Random;

/**
 * Descripty:
 * Auth:  邓渊  dymh21342@163.com
 * Date: 2018/1/30.13:51
 */

public class LoveItem {
    public ImageView imageLove;
    public boolean isShow = false;//是否已经显示

    public LoveItem(ViewGroup viewGroup){
        imageLove = new ImageView(viewGroup.getContext());
        imageLove.setLayoutParams(new ViewGroup.LayoutParams(200,200));
        imageLove.setVisibility(View.GONE);
        imageLove.setPadding(30,30,30,30);
        imageLove.setBackgroundResource(R.drawable.bg_white_gray_round);
    }

    public void setRandomImage(){
        switch (new Random().nextInt(5)){
            case 0:
                imageLove.setImageResource(R.drawable.love_girl_ggx);
                break;
            case 1:
                imageLove.setImageResource(R.drawable.love_girl_lyq);
                break;
            case 2:
                imageLove.setImageResource(R.drawable.love_heart);
                break;
            case 3:
                imageLove.setImageResource(R.drawable.love_mac);
                break;
            case 4:
                imageLove.setImageResource(R.drawable.love_pig);
                break;
        }
    }

}
