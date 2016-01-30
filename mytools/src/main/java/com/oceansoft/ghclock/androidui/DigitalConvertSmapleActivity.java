package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.dataopera.DigitalTrans;

/**
 * Created by Administrator on 2015/12/16.
 */
public class DigitalConvertSmapleActivity extends Activity implements View.OnClickListener {
	private EditText et_input;
	private EditText et_output;
	private Button btn_tentosixtennt, btn_tentobinary, btn_sixteentoten, btn_binarytoten;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_inttypes_smaple_main);

		initWidgets();
	}

	/**
	 * init
	 */
	private void initWidgets() {
		btn_binarytoten = (Button) findViewById(R.id.btn_inttypesmaple_binarytoten);
		btn_sixteentoten = (Button) findViewById(R.id.btn_inttypesmaple_sixteentoten);
		btn_tentobinary = (Button) findViewById(R.id.btn_inttypesmaple_tentobinary);
		btn_tentosixtennt = (Button) findViewById(R.id.btn_inttypesmaple_tentosix);

		btn_binarytoten.setOnClickListener(this);
		btn_sixteentoten.setOnClickListener(this);
		btn_tentobinary.setOnClickListener(this);
		btn_tentosixtennt.setOnClickListener(this);


		et_input = (EditText) findViewById(R.id.et_inttypessmaple_inputint);
		et_output = (EditText) findViewById(R.id.et_inttypessmaple_outputint);


	}


	@Override
	public void onClick(View v) {
		try {


			switch (v.getId()) {
				case R.id.btn_inttypesmaple_binarytoten:
					if (!"".equals(et_input.getText()))
						et_output.setText(DigitalTrans.binaryToAlgorism(et_input.getText().toString()) + "");
					break;
				case R.id.btn_inttypesmaple_sixteentoten:
					if (!"".equals(et_input.getText()))
						et_output.setText(DigitalTrans.hexStringToAlgorism(et_input.getText().toString())+"");
					break;
				case R.id.btn_inttypesmaple_tentobinary:
					if (!"".equals(et_input.getText()))
						et_output.setText(DigitalTrans.hexStringToBinary(DigitalTrans.algorismToHEXString(Integer.parseInt(et_input.getText().toString()))));
					break;
				case R.id.btn_inttypesmaple_tentosix:
					if (!"".equals(et_input.getText()))
						et_output.setText(DigitalTrans.algorismToHEXString(Integer.parseInt(et_input.getText().toString())));
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}


}
