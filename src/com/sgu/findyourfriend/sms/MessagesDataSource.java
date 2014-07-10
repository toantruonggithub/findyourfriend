package com.sgu.findyourfriend.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sgu.findyourfriend.ProfileInfo;

public class MessagesDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { 
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_MESSAGE,
			MySQLiteHelper.COLUMN_SENDER_ID,
			MySQLiteHelper.COLUMN_RECEVIER_ID,
			MySQLiteHelper.COLUMN_SMS_DATE };

	public MessagesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Message createMessage(Message sms) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_MESSAGE, sms.getMessage());
		values.put(MySQLiteHelper.COLUMN_SENDER_ID, sms.getGcmIdSender());
		values.put(MySQLiteHelper.COLUMN_RECEVIER_ID, sms.getGcmIdReceiver());
		values.put(MySQLiteHelper.COLUMN_SMS_DATE, sms.getSmsTime().getTime());
		
		
		long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGES, null,
				values);
		
		Log.i("CREATE SMS WITH ID ", String.valueOf(insertId));
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Message mes = cursorToMessage(cursor);
		cursor.close();
		return mes;
	}

	public void deleteMessage(Message sms) {
		long id = sms.getId();
		Log.i("@@@@@@@@@@@@@@@@: ", String.valueOf(sms.getId()));
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_MESSAGES, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<Message>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Message sms = cursorToMessage(cursor);
			messages.add(sms);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return messages;
	}

	private Message cursorToMessage(Cursor cursor) {
		Message message = new Message(
				cursor.getLong(0),
				cursor.getString(1),
				cursor.getString(2).equals(ProfileInfo.gcmMyId),
				cursor.getString(2),
				cursor.getString(3),
				new Date(Long.parseLong(cursor.getString(4))));
		return message;
	}
}