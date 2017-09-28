package com.developer.aashish.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

//public class MainActivity extends AppCompatActivity {
//EditText  edit;
//    Button b1,b2,b3;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        b1=(Button) findViewById(R.id.add);
//        b2=(Button) findViewById(R.id.show);
//        b3=(Button) findViewById(R.id.del);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//    }
//}
public class MainActivity extends Activity  implements View.OnClickListener, AdapterView.OnItemClickListener
//        implements View.OnClickListener, AdapterView.OnItemClickListener
{

    EditText mText;
    Button mAdd, del;
    ListView mList;

    DataBasehelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // del = (Button) findViewById(R.id.del);
        mText = (EditText) findViewById(R.id.ed1);
        mAdd = (Button) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(this);

        mHelper = new DataBasehelper(this);

//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String rowId = mText.getText().toString();
//                mDb.delete(DataBasehelper.TABLE_NAME, "_id = ?", new String[]{rowId});
//                mCursor.requery();
//                mAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mDb = mHelper.getWritableDatabase();
        String[] columns = new String[]{"_id", DataBasehelper.COL_NAME};
        mCursor = mDb.query(DataBasehelper.TABLE_NAME, columns, null, null, null, null, null, null);
        String[] headers = new String[]{DataBasehelper.COL_NAME};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                mCursor, headers, new int[]{android.R.id.text1});
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDb.close();
        mCursor.close();
    }
//    @Override
//    public void onClick(View v) {
//        ContentValues cv = new ContentValues(2);
//        cv.put(DataBasehelper.COL_NAME, mText.getText().toString());
////        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        cv.put(MyDbHelper.COL_DATE, dateFormat.format(new Date())); //Insert 'now' as the date
//        mDb.insert(DataBasehelper.TABLE_NAME, null, cv);
//        mCursor.requery();
//        mAdapter.notifyDataSetChanged();
//        mText.setText(null);
//    }
@Override
public void onClick(View v) {
    ContentValues cv = new ContentValues(2);
    cv.put(DataBasehelper.COL_NAME, mText.getText().toString());
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    cv.put(MyDbHelper.COL_DATE, dateFormat.format(new Date())); //Insert 'now' as the date
    mDb.insert(DataBasehelper.TABLE_NAME, null, cv);
    mCursor.requery();
    mAdapter.notifyDataSetChanged();
    mText.setText(null);
}

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        mCursor.moveToPosition(position);
        String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
        mDb.delete(DataBasehelper.TABLE_NAME, "_id = ?", new String[]{rowId});
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
    }
}

