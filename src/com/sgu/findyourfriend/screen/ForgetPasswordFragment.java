package com.sgu.findyourfriend.screen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sgu.findyourfriend.R;
import com.sgu.findyourfriend.net.PostData;
import com.sgu.findyourfriend.utils.Utility;

public class ForgetPasswordFragment extends BaseFragment {

	private EditText phone,email;
	private Button find;
	private Context ctx;
	private ProgressDialogCustom progress;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_forget_password, container,
				false);
		
		
		
		phone=(EditText)rootView.findViewById(R.id.EditText_ForgetPassword_Phone);
		email=(EditText)rootView.findViewById(R.id.EditText_ForgetPassword_Email);
		find=(Button)rootView.findViewById(R.id.Button_ForgetPassword_FindYourPass);
		find.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
				
				boolean isEmpty = false;
				
				if (phone.getText().toString().trim().length() > 0) {
					phone.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.edit_text));
				} else {
					phone.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.edit_text_wrong));
					isEmpty = true;
				}

				if (Utility.isValidEmailAddress(email.getText().toString()
						.trim())) {
					email.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.edit_text));
				} else {
					email.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.edit_text_wrong));
					isEmpty = true;
				}

				if (isEmpty) {
					Utility.showDialog(Utility.ERROR, dialog, "Thiếu thông tin", 
							"Nhập các thông tin yêu cầu.", "Đóng", new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									dialog.dismiss();
								}
							});
					return;
				}
				
				// TODO Auto-generated method stub
				(new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected void onPreExecute() {
						progress = new ProgressDialogCustom(ctx);
						progress.show();
					}
					
					@Override
					protected Boolean doInBackground(Void... params) {
						return PostData.userForgetPassword(getActivity(), phone.getText().toString().trim(),
								email.getText().toString().trim());
					}
					
					@Override
					protected void onPostExecute(Boolean result) {
						progress.dismiss();
						if (result) { 
							
							Utility.showDialog(Utility.CONFIRM, dialog, "Thông báo", 
									"Hệ thống sẽ gửi email đến bạn trong giây lát.", "Đóng", new OnClickListener() {
										
										@Override
										public void onClick(View arg0) {
											dialog.dismiss();
										}
									});
							replaceFragment(new LoginFragment(), false);
						} else {
							
							Utility.showDialog(Utility.ERROR, dialog, "Lỗi", 
									"Email hoặc số điện thoại không đúng.", "Đóng", new OnClickListener() {
										
										@Override
										public void onClick(View arg0) {
											dialog.dismiss();
										}
									});
						}
					}
					
				}).execute();
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.ctx = activity;
	}
	
}
