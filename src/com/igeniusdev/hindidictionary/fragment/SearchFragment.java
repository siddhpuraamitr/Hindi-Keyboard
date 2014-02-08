package com.igeniusdev.hindidictionary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igeniusdev.hindidictionary.MainActivity;
import com.igeniusdev.hindidictionary.R;
import com.igeniusdev.hindidictionary.utility.Constants;
import com.igeniusdev.hindidictionary.widget.KeyboardButton;

public class SearchFragment extends Fragment implements OnTouchListener,
		OnClickListener, OnFocusChangeListener, OnItemClickListener {

	public static EditText mEt;// , mEt1; // Edit Text boxes
	private KeyboardButton mBSpace, mNum;
	private KeyboardButton mBdone, mBack, mBChange;
	private static RelativeLayout mLayout;
	private static RelativeLayout mKLayout;
	private boolean isEdit = false, isEdit1 = false;
	private String mUpper = "upper", mLower = "lower";
	private int w, mWindowWidth;
	private String sL[] = { "क", "ख", "ग", "घ", "ङ", "च", "छ", "ज", "झ",
			"ञ", "ट", "ठ", "ड", "ढ", "ण", "त", "थ", "द", "ध", "न",
			"ा", "ि", "ी", "ु", "ू", "े", "ै", "ो", "ँ", "ं", "अ", "इ" };
	private String cL[] = { "प", "फ", "ब", "भ", "म", "य", "र", "ल", "व",
			"श", "ष", "स", "ह", "व", "ह", "ब", "भ", "य", "फ", "इ",
			"आ", "ई", "उ", "ऋ", "ए", "ऐ", "ओ", "औ", "श्र", "क्ष", "ऊ",
			"ऋ" };
	private String nS[] = { "१", "२", "३", "४", "५", "६", "७", "८", "९",
			"०", "!", ")", "'", "#", "$", "%", "&", "*", "?", "/",
			"+", "-", "@", "(", "\"", "_", "=", "]", "[", "<", ">",
			"|" };
	private KeyboardButton mB[] = new KeyboardButton[32];
	// Hindi keyboard code closed----------------------------
	private SharedPreferences settings;
	boolean flagEnFl = true;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.search, container, false);

		settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
		flagEnFl = settings.getBoolean(Constants.ENG_FILIPIONO, true);
		mEt = (EditText) v.findViewById(R.id.searchText);

		mLayout = (RelativeLayout) v.findViewById(R.id.xK1);
		mKLayout = (RelativeLayout) v.findViewById(R.id.xKeyBoard);

		ImageView imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
		imgSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chooseLanguage();
			}
		});
		setKeys();
		setFrow();
		setSrow();
		setTrow();
		setForow();
		changeSmallLetters();
		changeSmallTags();
		enableHindiKeyboard();
		hideDefaultKeyboard();
		return v;
	}

	public boolean onTouch(View v, MotionEvent event) {

		if (v == mEt) {
			// System.out.println("onTouch v==mEt");
			hideDefaultKeyboard();
			enableKeyboard();
		}
		return true;
	}

	public void onClick(View v) {
		// System.out.println("ONCLICK");
		if (v == mBChange) {
			// System.out.println("mBChange");
			if (mBChange.getTag().equals(mUpper)) {
				changeSmallLetters();
				changeSmallTags();
			} else if (mBChange.getTag().equals(mLower)) {
				changeCapitalLetters();
				changeCapitalTags();
			}
		} else if (v != mBdone && v != mBack && v != mBChange && v != mNum) {
			// System.out.println("mBdone mBack mBChange mNum");
			isEdit = true;
			addText(v);
		} else if (v == mBdone) {
			// System.out.println("mBdone");
			disableKeyboard();
		} else if (v == mBack) {
			// System.out.println("mBack");
			isBack(v);
		} else if (v == mNum) {
			// System.out.println("mNum");
			String nTag = (String) mNum.getTag();
			if (nTag.equals("num")) {
				changeSyNuLetters();
				changeSyNuTags();
				mBChange.setVisibility(Button.INVISIBLE);
			}
			if (nTag.equals("ABC")) {
				changeCapitalLetters();
				changeCapitalTags();
			}
		}
	}

	public void onFocusChange(View v, boolean hasFocus) {
		// System.out.println("onFocusChange :" + mEt);
		if (v == mEt && hasFocus == true) {
			isEdit = true;
		}
	}

	private void addText(View v) {
		if (isEdit == true) {
			// System.out.println("is Edit");
			String b = "";
			b = (String) v.getTag();
			if (b != null) {
				// System.out.println("mEt Append :");
				mEt.append(b);
			}
		} else {
			// System.out.println("is Edit else part");
		}
	}

	private void isBack(View v) {
		if (isEdit == true) {
			// System.out.println("mEt.getText()");
			CharSequence cc = mEt.getText();
			if (cc != null && cc.length() > 0) {
				{
					// System.out.println("mEt.setText()");
					mEt.setText("");
					mEt.append(cc.subSequence(0, cc.length() - 1));
				}

			}
		}
	}

	private void changeSmallLetters() {
		mBChange.setVisibility(Button.VISIBLE);
		for (int i = 0; i < sL.length; i++)
			mB[i].setText(sL[i]);
		mNum.setTag("12#");
	}

	private void changeSmallTags() {
		for (int i = 0; i < sL.length; i++)
			mB[i].setTag(sL[i]);
		mBChange.setTag("lower");
		mNum.setTag("num");
	}

	private void changeCapitalLetters() {
		mBChange.setVisibility(Button.VISIBLE);
		for (int i = 0; i < cL.length; i++)
			mB[i].setText(cL[i]);
		mBChange.setTag("upper");
		mNum.setText("12#");

	}

	private void changeCapitalTags() {
		for (int i = 0; i < cL.length; i++)
			mB[i].setTag(cL[i]);
		mNum.setTag("num");

	}

	private void changeSyNuLetters() {

		for (int i = 0; i < nS.length; i++)
			mB[i].setText(nS[i]);
		mNum.setText("ABC");
	}

	private void changeSyNuTags() {
		for (int i = 0; i < nS.length; i++)
			mB[i].setTag(nS[i]);
		mNum.setTag("ABC");
	}

	// enabling customized keyboard

	private void enableKeyboard() {
		// System.out.println("enable keyboard");
		mLayout.setVisibility(RelativeLayout.VISIBLE);
		mKLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	// Disable customized keyboard
	public static void disableKeyboard() {
		mLayout.setVisibility(RelativeLayout.INVISIBLE);
		mKLayout.setVisibility(RelativeLayout.INVISIBLE);
	}

	private void hideDefaultKeyboard() {
		// mEt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEt.getWindowToken(), 0);
	}

	private void setFrow() {
		w = (mWindowWidth / 10);
		// w = w - 15;
		mB[16].setLayoutParams(w, 50);
		mB[22].setLayoutParams(w, 50);
		mB[4].setLayoutParams(w, 50);
		mB[17].setLayoutParams(w, 50);
		mB[19].setLayoutParams(w, 50);
		mB[24].setLayoutParams(w, 50);
		mB[20].setLayoutParams(w, 50);
		mB[8].setLayoutParams(w, 50);
		mB[14].setLayoutParams(w, 50);
		mB[15].setLayoutParams(w, 50);
		// mB[16].setHeight(50);
		// mB[22].setHeight(50);
		// mB[4].setHeight(50);
		// mB[17].setHeight(50);
		// mB[19].setHeight(50);
		// mB[24].setHeight(50);
		// mB[20].setHeight(50);
		// mB[8].setHeight(50);
		// mB[14].setHeight(50);
		// mB[15].setHeight(50);
	}

	private void setSrow() {
		w = (mWindowWidth / 10);
		mB[0].setLayoutParams(w, 50);
		mB[18].setLayoutParams(w, 50);
		mB[3].setLayoutParams(w, 50);
		mB[5].setLayoutParams(w, 50);
		mB[6].setLayoutParams(w, 50);
		mB[7].setLayoutParams(w, 50);
		mB[26].setLayoutParams(w, 50);
		mB[9].setLayoutParams(w, 50);
		mB[10].setLayoutParams(w, 50);
		mB[11].setLayoutParams(w, 50);
		mB[26].setLayoutParams(w, 50);

		// mB[0].setHeight(50);
		// mB[18].setHeight(50);
		// mB[3].setHeight(50);
		// mB[5].setHeight(50);
		// mB[6].setHeight(50);
		// mB[7].setHeight(50);
		// mB[9].setHeight(50);
		// mB[10].setHeight(50);
		// mB[11].setHeight(50);
		// mB[26].setHeight(50);
	}

	private void setTrow() {
		w = (mWindowWidth / 10);
		mB[25].setLayoutParams(w, 50);
		mB[23].setLayoutParams(w, 50);
		mB[2].setLayoutParams(w, 50);
		mB[21].setLayoutParams(w, 50);
		mB[1].setLayoutParams(w, 50);
		mB[13].setLayoutParams(w, 50);
		mB[12].setLayoutParams(w, 50);
		mB[27].setLayoutParams(w, 50);
		mB[28].setLayoutParams(w, 50);
		mBack.setLayoutParams(50, 50);
		mBack.setBackgroundResource(R.drawable.back_top_layout_hover);

		// mB[25].setHeight(50);
		// mB[23].setHeight(50);
		// mB[2].setHeight(50);
		// mB[21].setHeight(50);
		// mB[1].setHeight(50);
		// mB[13].setHeight(50);
		// mB[12].setHeight(50);
		// mB[27].setHeight(50);
		// mB[28].setHeight(50);
		// mBack.setHeight(50);

	}

	private void setForow() {
		w = (mWindowWidth / 10);
		mBSpace.setLayoutParams(w * 4, 50);
		// mBSpace.setHeight(50);
		mB[29].setLayoutParams(w, 50);
		// mB[29].setHeight(50);

		mB[30].setLayoutParams(w, 50);
		// mB[30].setHeight(50);

		// mB[31].setHeight(50);
		mB[31].setLayoutParams(w, 50);
		mNum.setLayoutParams(w, 50);
		mBdone.setLayoutParams(60, 50);
		mBChange.setLayoutParams(60, 50);
		mBChange.setBackgroundResource(R.drawable.change);
		mBdone.setBackgroundResource(R.drawable.hide_high);
		// mBdone.setHeight(50);

	}

	private void setKeys() {
		mWindowWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth(); // getting
		// window
		// height
		// getting ids from xml files
		// System.out.println("Width  :: " + mWindowWidth);
		mB[0] = (KeyboardButton) v.findViewById(R.id.xQ);
		mB[1] = (KeyboardButton) v.findViewById(R.id.xW);
		mB[2] = (KeyboardButton) v.findViewById(R.id.xE);
		mB[3] = (KeyboardButton) v.findViewById(R.id.xR);
		mB[4] = (KeyboardButton) v.findViewById(R.id.xT);
		mB[5] = (KeyboardButton) v.findViewById(R.id.xY);
		mB[6] = (KeyboardButton) v.findViewById(R.id.xU);
		mB[7] = (KeyboardButton) v.findViewById(R.id.xI);
		mB[8] = (KeyboardButton) v.findViewById(R.id.xO);
		mB[9] = (KeyboardButton) v.findViewById(R.id.xP);
		mB[10] = (KeyboardButton) v.findViewById(R.id.xL);
		mB[11] = (KeyboardButton) v.findViewById(R.id.xA);
		mB[12] = (KeyboardButton) v.findViewById(R.id.xS);
		mB[13] = (KeyboardButton) v.findViewById(R.id.xD);
		mB[14] = (KeyboardButton) v.findViewById(R.id.xF);
		mB[15] = (KeyboardButton) v.findViewById(R.id.xG);
		mB[16] = (KeyboardButton) v.findViewById(R.id.xH);
		mB[17] = (KeyboardButton) v.findViewById(R.id.xJ);
		mB[18] = (KeyboardButton) v.findViewById(R.id.xK);
		mB[19] = (KeyboardButton) v.findViewById(R.id.xS1);
		mB[20] = (KeyboardButton) v.findViewById(R.id.xS2);
		mB[21] = (KeyboardButton) v.findViewById(R.id.xZ);
		mB[22] = (KeyboardButton) v.findViewById(R.id.xX);
		mB[23] = (KeyboardButton) v.findViewById(R.id.xC);
		mB[24] = (KeyboardButton) v.findViewById(R.id.xV);
		mB[25] = (KeyboardButton) v.findViewById(R.id.xB);
		mB[26] = (KeyboardButton) v.findViewById(R.id.xN);
		mB[27] = (KeyboardButton) v.findViewById(R.id.xM);
		mB[28] = (KeyboardButton) v.findViewById(R.id.xS3);
		mB[29] = (KeyboardButton) v.findViewById(R.id.xS4);
		mB[30] = (KeyboardButton) v.findViewById(R.id.xS5);
		mB[31] = (KeyboardButton) v.findViewById(R.id.xS6);
		mBSpace = (KeyboardButton) v.findViewById(R.id.xSpace);
		mBdone = (KeyboardButton) v.findViewById(R.id.xDone);
		mBChange = (KeyboardButton) v.findViewById(R.id.xChange);
		mBack = (KeyboardButton) v.findViewById(R.id.xBack);
		mNum = (KeyboardButton) v.findViewById(R.id.xNum);
		for (int i = 0; i < mB.length; i++)
			mB[i].setOnClickListener(this);
		mBSpace.setOnClickListener(this);
		mBdone.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mBChange.setOnClickListener(this);
		mNum.setOnClickListener(this);

	}

	// Hindi keyboard code closed----------------------------

	public static int getResult() {
		return 28098;
	}

	@Override
	public void onDestroyView() {
		hideDefaultKeyboard();
		// if (cursor != null)
		// if (!cursor.isClosed())
		// cursor.close();
		// if (dbHelper != null)
		// dbHelper.close();
		super.onDestroyView();
	}

	public void chooseLanguage() {
		final CharSequence[] gender = { Constants.LANGUAGE_1,
				Constants.LANGUAGE_2 };
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Select Dictionary");
		int defaultValue;

		if (flagEnFl)
			defaultValue = 1;
		else
			defaultValue = 0;

		alert.setSingleChoiceItems(gender, defaultValue,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						flagEnFl = (which == 0) ? false : true;
						enableHindiKeyboard();
						settings.edit()
								.putBoolean(Constants.ENG_FILIPIONO, flagEnFl)
								.commit();
						mEt.setText("");
						dialog.dismiss();
					}
				});
		alert.show();

	}

	@Override
	public void onDestroy() {
		// System.out.println("onDestroy");
		mEt = null;
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// System.out.println("onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {

		// //System.out.println("onPause");
		// disableKeyboard();
		// cursor = dbHelper.getCursor(null, flagEnFl);
		// getActivity().startManagingCursor(cursor);
		super.onPause();
		// afterDataDownload();
	}

	@Override
	public void onStart() {
		// System.out.println("onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		// System.out.println("onStop");
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// System.out.println("oncreate");
	}

	@Override
	public void onAttach(Activity activity) {
		// System.out.println("On attach");
		super.onAttach(activity);
	}

	public void enableHindiKeyboard() {
		if (flagEnFl) {
			mEt.setInputType(InputType.TYPE_NULL);
			// Hindi keyboard code started---------------------------
			try {
				for (int i = 0; i < mB.length; i++)
					mB[i].setOnClickListener(this);

				mBSpace.setOnClickListener(this);
				mBdone.setOnClickListener(this);
				mBack.setOnClickListener(this);
				mBChange.setOnClickListener(this);
				mNum.setOnClickListener(this);
				mEt.setOnTouchListener(this);
				mEt.setOnFocusChangeListener(this);
				mEt.setOnClickListener(this);

			} catch (Exception e) {
				// System.out.println("Exception ::: ,f;lkfldaj");
				Log.w(getClass().getName(), e.toString());
			}
			// Hindi keyboard code closed-----------------------------
		} else {
			mEt.setInputType(InputType.TYPE_CLASS_TEXT);
			mEt.setOnTouchListener(null);
			mEt.setOnFocusChangeListener(null);
			mEt.setOnClickListener(null);
			disableKeyboard();
		}

	}

	public void notifyUser(String notification) {
		TextView txtNotify = (TextView) this.v.findViewById(R.id.notify);
		txtNotify.setVisibility(TextView.VISIBLE);
		txtNotify.setText(notification);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		MainActivity.rlMainHeader.setVisibility(View.GONE);
	}

}
