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
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgu.findyourfriend.R;
import com.sgu.findyourfriend.mgr.FriendManager;
import com.sgu.findyourfriend.model.Friend;

public class CustomAdapterFriendStatus extends ArrayAdapter<Friend> {
	private int LayoutResID;
	private Context ctx;
	private List<Friend> DataList = new ArrayList<Friend>();

	public CustomAdapterFriendStatus(Context context, int resource,
			List<Friend> objects) {
		super(context, resource, objects);
		ctx = context;
		LayoutResID = resource;
		DataList = objects;
	}

	@Override
	public Friend getItem(int position) {
		return DataList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
		convertView = inflater.inflate(LayoutResID, null);
		TextView name = (TextView) convertView.findViewById(R.id.txtName);
		TextView phoneNumber = (TextView) convertView
				.findViewById(R.id.txtPhoneNumber);
		ImageView imgProfile = (ImageView) convertView
				.findViewById(R.id.imgProfile);
		ImageView imgStatusBar = (ImageView) convertView
				.findViewById(R.id.imgStatusBar);
		TextView txtStatusText = (TextView) convertView
				.findViewById(R.id.txtStatusText);
		ImageView imgShare = (ImageView) convertView
				.findViewById(R.id.imgShare);

		Friend fr = DataList.get(position);

		name.setText(fr.getUserInfo().getName());

		if (null != fr.getNumberLogin() && fr.getNumberLogin().size() > 0)
			phoneNumber.setText(fr.getNumberLogin().get(0));
		else
			phoneNumber.setText("chưa có");

		imgProfile.setImageDrawable(FriendManager.getInstance().hmImageP.get(fr
				.getUserInfo().getId()));

		// set img
		if (fr.getAcceptState() == Friend.SHARE_RELATIONSHIP) {
			// share image
			imgShare.setVisibility(View.VISIBLE);
		} else {
			// don't share
			imgShare.setVisibility(View.GONE);
		}

		if (fr.isAvailable()) {
			imgStatusBar.setBackgroundColor(ctx.getResources().getColor(
					R.color.online));
			txtStatusText.setText("trực tuyến");
		} else {
			imgStatusBar.setBackgroundColor(ctx.getResources().getColor(
					R.color.offine));
			txtStatusText.setText("ẩn");
		}
		return convertView;
	}
}
