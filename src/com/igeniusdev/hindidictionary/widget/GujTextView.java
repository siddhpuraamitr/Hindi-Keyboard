package com.igeniusdev.hindidictionary.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class GujTextView extends TextView {
	Context context;
	String ttfName;
	String TAG = getClass().getName();

	public GujTextView(Context context, AttributeSet atribSet) {
		super(context, atribSet);
		this.context = context;
		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"fonts/RaghuGujaratiSans.ttf");
		setTypeface(font);
	}

	public void setTypeface(Typeface tf) {
		super.setTypeface(tf);
	}

}
