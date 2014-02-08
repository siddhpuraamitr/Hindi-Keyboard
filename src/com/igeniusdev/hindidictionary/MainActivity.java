package com.igeniusdev.hindidictionary;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igeniusdev.hindidictionary.fragment.SearchFragment;

public class MainActivity extends FragmentActivity {

	public static RelativeLayout rlMainHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		SearchFragment fragment = new SearchFragment();
		fragmentTransaction.replace(R.id.flMain, fragment);
		fragmentTransaction.commit();
	}

}
