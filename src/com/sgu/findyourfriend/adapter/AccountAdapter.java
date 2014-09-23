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
package com.sgu.findyourfriend.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sgu.findyourfriend.R;
import com.sgu.findyourfriend.mgr.MyProfileManager;
import com.sgu.findyourfriend.net.PostData;
import com.sgu.findyourfriend.utils.Utility;

public class AccountAdapter extends ArrayAdapter<String> {

	private Context ctx;
	private ArrayList<String> data;
	private int rowLayout;

	public AccountAdapter(Context ctx, int rowLayout, ArrayList<String> list) {
		super(ctx, rowLayout, list);
		this.ctx = ctx;
		this.rowLayout = rowLayout;
		this.data = list;
	}

	@Override
	public String getItem(int position) {
		return data.get(position);
	}

	public ArrayList<String> getData() {
		return data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(rowLayout, null);

			holder = new ViewHolder();

			holder.txtPhoneNumber = (TextView) view
					.findViewById(R.id.txtPhoneNumber);
			holder.btnRemove = (Button) view.findViewById(R.id.btnRemove);
			holder.btnRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					(new AsyncTask<Void, Void, Boolean>() {

						@Override
						protected Boolean doInBackground(Void... params) {
							return PostData.accountDeleteCurrentAccount(
									getContext(), MyProfileManager
											.getInstance().getMyID(),
									getItem(position));
						}

						@Override
						protected void onPostExecute(Boolean result) {
							if (result) {
								Utility.showMessage(getContext(),
										"Đã xóa số điện thoại "
												+ getItem(position));
								MyProfileManager.getInstance()
										.removeMyPhoneNumber(getItem(position));
								data.remove(position);

								notifyDataSetChanged();
							} else {
								Utility.showMessage(getContext(),
										"Xóa thất bại");
							}
						}

					}).execute();

				}
			});

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.txtPhoneNumber.setText(getItem(position));
		return view;
	}

	class ViewHolder {
		public TextView txtPhoneNumber;
		public Button btnRemove;
	}

}
