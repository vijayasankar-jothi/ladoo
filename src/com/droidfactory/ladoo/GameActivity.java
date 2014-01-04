package com.droidfactory.ladoo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends Activity {

	ArrayList<Integer> article_images = new ArrayList<Integer>();

	String question = "My last name is Turquotte. who am I ?";
	int answer = 11;
	ViewHolder vh;
	int try_count = 0;
	String pay_text="";
	int thepuzzles[] = {R.drawable.puzzle1,R.drawable.puzzle2,R.drawable.puzzle3,R.drawable.puzzle4,R.drawable.puzzle5,R.drawable.puzzle6,R.drawable.puzzle7,R.drawable.puzzle8,R.drawable.puzzle9,R.drawable.puzzle10,R.drawable.puzzle11,R.drawable.puzzle12};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item);

		article_images.add(R.drawable.img_one);
		article_images.add(R.drawable.img_two);
		article_images.add(R.drawable.img_three);
		article_images.add(R.drawable.img_four);

		article_images.add(R.drawable.img_five);
		article_images.add(R.drawable.img_six);
		article_images.add(R.drawable.img_seven);
		article_images.add(R.drawable.image_five);

		article_images.add(R.drawable.img_one);
		article_images.add(R.drawable.img_one);
		article_images.add(R.drawable.img_one);
		article_images.add(R.drawable.img_one);

		vh = new ViewHolder();
		setViewHolder(vh);
		populateView(vh);

		this.getActionBar().setTitle("Shoot the celeb");
	}

	private void populateView(ViewHolder vh) {
		vh.imageView1.setImageDrawable(getResources().getDrawable(article_images.get(0)));

	}

	private void setViewHolder(ViewHolder vh) {
		vh.imageView1 = (ImageView) findViewById(R.id.imageView1);
		vh.imageView2 = (ImageView) findViewById(R.id.imageView2);
		vh.imageView3 = (ImageView) findViewById(R.id.imageView3);
		vh.imageView4 = (ImageView) findViewById(R.id.imageView4);
		vh.imageView5 = (ImageView) findViewById(R.id.imageView5);
		vh.imageView6 = (ImageView) findViewById(R.id.imageView6);
		vh.imageView7 = (ImageView) findViewById(R.id.imageView7);
		vh.imageView8 = (ImageView) findViewById(R.id.imageView8);
		vh.imageView9 = (ImageView) findViewById(R.id.imageView9);
		vh.imageView10 = (ImageView) findViewById(R.id.imageView10);
		vh.imageView11 = (ImageView) findViewById(R.id.imageView11);
		vh.imageView12 = (ImageView) findViewById(R.id.imageView12);
		
		vh.imageView1.setImageResource(thepuzzles[0]);
		vh.imageView2.setImageResource(thepuzzles[1]);
		vh.imageView3.setImageResource(thepuzzles[2]);
		vh.imageView4.setImageResource(thepuzzles[3]);
		vh.imageView5.setImageResource(thepuzzles[4]);
		vh.imageView6.setImageResource(thepuzzles[5]);
		vh.imageView7.setImageResource(thepuzzles[6]);
		vh.imageView8.setImageResource(thepuzzles[7]);
		vh.imageView9.setImageResource(thepuzzles[8]);
		vh.imageView10.setImageResource(thepuzzles[9]);
		vh.imageView11.setImageResource(thepuzzles[10]);
		vh.imageView12.setImageResource(thepuzzles[11]);
		
		vh.pay = (Button) findViewById(R.id.button1);

	}

	class ViewHolder {
		ImageView imageView1;
		ImageView imageView2;
		ImageView imageView3;
		ImageView imageView4;
		ImageView imageView5;
		ImageView imageView6;
		ImageView imageView7;
		ImageView imageView8;
		ImageView imageView9;
		ImageView imageView10;
		ImageView imageView11;
		ImageView imageView12;

		Button pay;
	}

	public void onClickImg(View v) {
		
		try_count++;
		String tag_str = (String) v.getTag();
		int tag = Integer.parseInt(tag_str);
		if (answer == tag) {
			if (try_count == 1) {
				vh.pay.setText("Download free");
			}else {
				vh.pay.setText("Yeah, but "+pay_text);
			}
			return;
		}

		if (try_count == 1) {
			pay_text = "Pay 0.1$";
			
		}else if (try_count == 2) {
			pay_text = "Pay 0.2$";
		} else if (try_count == 3) {
			pay_text = "Pay 0.3$";
		} else if (try_count == 4) {
			pay_text = "Pay 0.4$";
		} else if (try_count == 5) {
			pay_text = "Pay 0.5$";
		} else if (try_count == 6) {
			pay_text = "Pay 0.6$";
		} else if (try_count == 7) {
			pay_text = "Pay 0.7$";
		} else if (try_count == 8) {
			pay_text = "Pay 0.8$";
		} else {
			pay_text = "Pay 1$";
		}
		vh.pay.setText(pay_text);

	}

}
