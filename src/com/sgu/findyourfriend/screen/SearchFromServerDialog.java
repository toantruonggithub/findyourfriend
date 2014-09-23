/*
 * 	 This file is part of Find Your Friend.
 *
 *   Find Your Friend is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Find Your Friend is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Find Your Friend.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sgu.findyourfriend.screen;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sgu.findyourfriend.R;
import com.sgu.findyourfriend.adapter.CustomAdapterFriendSearch;
import com.sgu.findyourfriend.mgr.MyProfileManager;
import com.sgu.findyourfriend.model.User;
import com.sgu.findyourfriend.net.PostData;
import com.sgu.findyourfriend.utils.Utility;

public class SearchFromServerDialog extends Dialog {

	private CustomAdapterFriendSearch adapter;
	private ListView lv;
	private Context ctx;
	private ProgressBar pbLoader;
	private ImageButton imgClose;
	private Button btnSearch;
	private EditText editSearch;
	private ArrayList<User> temptData;

	public SearchFromServerDialog(Context context) {
		super(context, R.style.full_screen_dialog);
		this.ctx = context;
		temptData = new ArrayList<User>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_dialog_custom);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
		imgClose = (ImageButton) findViewById(R.id.imgClose);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		editSearch = (EditText) findViewById(R.id.editSearch);

		lv = (ListView) findViewById(R.id.lvFriendSuggest);
		adapter = new CustomAdapterFriendSearch(ctx,
				R.layout.custom_friend_search, temptData);
		lv.setAdapter(adapter);

		btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!editSearch.getText().toString().equals(""))
					(new LoadData()).execute(editSearch.getText().toString());
				else
					Utility.showMessage(getContext(), "tìm kiếm rỗng");

				InputMethodManager imm = (InputMethodManager) ctx
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
			}
		});

		imgClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

			}
		});
	}

	private class LoadData extends AsyncTask<String, Void, Void> {

		private Dialog dialog = new Dialog(getContext());
		
		@Override
		protected void onPreExecute() {

			pbLoader.setVisibility(View.VISIBLE);
			temptData.clear();
		}

		@Override
		protected Void doInBackground(String... params) {
			temptData = PostData.userGetUsersByEveryThingWithoutFriend(getContext(), MyProfileManager
							.getInstance().getMyID(), params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pbLoader.setVisibility(View.GONE);
			// adapter.setData(temptData);
			adapter = new CustomAdapterFriendSearch(ctx,
					R.layout.custom_friend_search, temptData);
			lv.setAdapter(adapter);

			if (temptData.size() == 0) {
				
				Utility.showDialog(Utility.WARNING, dialog, "Tìm kiếm rỗng", "Không có kết quả.");
			}

		}

	}

}
