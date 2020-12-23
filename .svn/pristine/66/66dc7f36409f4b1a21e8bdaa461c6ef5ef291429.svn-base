package com.menadinteractive.segafredo.communs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
DatePickerDialog.OnDateSetListener{

	int year, month, day;
	
	OnDateChange mListener;
	Button button;
	
	public DatePickerFragment(OnDateChange listener){
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		this.year = c.get(Calendar.YEAR);
		this.month = c.get(Calendar.MONTH);
		this.day = c.get(Calendar.DAY_OF_MONTH);
		mListener = listener;
	}

	public DatePickerFragment(int year, int month, int day, OnDateChange listener) {
		this.year = year;
		this.month = month;
		this.day = day;
		mListener = listener;
	}

	public DatePickerFragment(Calendar calendar, OnDateChange listener){
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		mListener = listener;
	}
	
	public DatePickerFragment(Button button, Calendar calendar, OnDateChange onDateChange){
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.button = button;
		mListener = onDateChange;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		
		if(mListener != null) mListener.onDateSet(year, month, day);

	}
	
	public interface OnDateChange{
		public void onDateSet(int year, int month, int day);
	}

}
