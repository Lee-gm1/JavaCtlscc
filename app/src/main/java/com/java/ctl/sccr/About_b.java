
/// Copyright (C) 2010 - 2019 Paul Laughton

package com.java.ctl.sccr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class About_b extends Activity {
	private static final String LOGTAG = "About";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about_b);
		final String version = "v" + getString(R.string.version);

		TextView tv1 = (TextView)findViewById(R.id.about_tv1);
		tv1.setText(getString(R.string.about_text1, version));

		TextView tv2 = (TextView)findViewById(R.id.about_tv2);
		tv2.setText(getString(R.string.about_text2));
        
        TextView tv3 = (TextView)findViewById(R.id.about_tv3);
        tv3.setText(getString(R.string.about_text3));
         
		setupButton(R.id.about_btn_forum,    "http://rfobasic.freeforums.org/index.php?mobile=mobile");
		setupButton(R.id.about_btn_programs, "http://laughton.com/basic/programs");
		String url =			// add version to the URL
			"https://bintray.com/rfo-basic/android/RFO-BASIC/"+ version + "/view/read";
		setupButton(R.id.about_btn_github,   "https://github.com/RFO-BASIC");
		setupButton(R.id.about_btn_license,  "https://www.gnu.org/licenses/gpl-3.0-standalone.html");
		setupButton(R.id.about_btn_tele,"https://t.me/humanoidmk"); 
        setupButton(R.id.about_btn_tele1,"https://t.me/idetools");
        setupButton(R.id.about_btn_tele2,"https://t.me/+XgkV1BK2VXA5MmVh");
	}

	private void setupButton(int id, final String url) {
		Button btn = (Button)findViewById(id);
		btnSetText(btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}

	private void btnSetText(Button btn) {
		String btnText = btn.getText().toString();
		int pos = btnText.indexOf('\n');
		if (pos > 0) {
			// String has a two lines. Make the second line smaller.
			int len = btnText.length();
			Spannable span = new SpannableString(btnText);
			span.setSpan(new RelativeSizeSpan(1.0f), 0, pos, 0);
			span.setSpan(new RelativeSizeSpan(0.7f), pos, len, 0);
			btn.setText(span);
		}
	}
}
