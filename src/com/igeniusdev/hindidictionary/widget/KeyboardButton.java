package com.igeniusdev.hindidictionary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.igeniusdev.hindidictionary.R;

public class KeyboardButton extends Button {

	public KeyboardButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public KeyboardButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyboardButton(Context context) {
		super(context);
	}

	public void setLayoutParams(int width, int height) {
		LayoutParams params = new LayoutParams(width, 60);
		this.setLayoutParams(params);
		setBackgroundResource(R.drawable.keyboard_btn);
		setPadding(1, 1, 1, 1);
	}

}
