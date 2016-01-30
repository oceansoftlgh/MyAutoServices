package com.oceansoft.ghclock.androidui;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.tcpnetwork.VolleySingleton;


public class VollleySmapleActivity extends AppCompatActivity {
	ImageView imageView = null;

	String url = "http://i.imgur.com/7spzG.png";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		
		
		imageView = (ImageView) findViewById(R.id.iv_volleyimageview);
		
		loadImamgeByRequest();


		//加载网络图片
		LoadUrlImage loadUrlImage = new LoadUrlImage();
		loadUrlImage.execute((Void)null);
		
		
	}

	/**
	 * 使用ImageRequest加载图片
	 */
	private void loadImamgeByRequest() {
		//使用ImageRequest加载图片
		ImageRequest request = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						imageView.setImageBitmap(response);
					}
				}, 0, 0, Bitmap.Config.RGB_565,
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Snackbar.make(imageView.getRootView(),error.getMessage(), Snackbar.LENGTH_SHORT).show();
					}
				}
		);
		VolleySingleton.getVolleySingleton(VollleySmapleActivity.this.getApplicationContext()).addToRequestQueue(request);
	}


	public  class LoadUrlImage extends AsyncTask<Void,Void,Boolean> {
		
		LoadUrlImage(){
		}

		@Override
		protected Boolean doInBackground(Void... params) {


			return false;
		}

		@Override
		protected void onPostExecute(Boolean boolenan) {
			
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		/*if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() <= 1) {
			Intent intent = new Intent(VollleySmapleActivity.this, ExitActivity.class);
			startActivity(intent);


			*//*final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(NavigateContextMainActivity.this);
			dialogBuilder
					.withTitle("系统退出")
					.withMessage("是否确认退出？")
					.withIcon(getResources().getDrawable(R.drawable.ic_info_black_24dp))
					.isCancelableOnTouchOutside(true)
					.withDuration(500)
					
					.withEffect(Effectstype.Slideright)
					.withButton1Text("确定")
					.withButton2Text("取消")
					.setButton1Click(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
							finish();
						}
					})
					.setButton2Click(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							//Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
							dialogBuilder.dismiss();
						}
					})
					.show();*//*
		return true;
	}*/
			return super.onKeyDown(keyCode, event);
	}
}
