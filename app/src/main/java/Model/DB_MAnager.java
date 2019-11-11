package Model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DB_MAnager {







        private my_helper dbHelper;

        private Context context;

        private SQLiteDatabase database;

        public DB_MAnager(Context c) {
            context = c;
        }

        public DB_MAnager open() throws SQLException {
            dbHelper = new my_helper(context);
            database =dbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            dbHelper.close();
        }

        public void insert(String name, String desc,String english,String hindi,String tamil,String telugu) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(my_helper.SUBJECT, name);
            contentValue.put(my_helper.DESC, desc);
            contentValue.put(my_helper.HINDI,hindi);
            contentValue.put(my_helper.ENGLISH,english);
            contentValue.put(my_helper.TAMIL,tamil);
            contentValue.put(my_helper.TELUGU,telugu);
            database.insert(my_helper.TABLE_NAME, null, contentValue);
        }

        public Cursor fetch() {
            String[] columns = new String[] { my_helper._ID, my_helper.SUBJECT, my_helper.DESC,my_helper.HINDI,my_helper.ENGLISH,my_helper.TAMIL,my_helper.TELUGU };
            Cursor cursor = database.query(my_helper.TABLE_NAME, columns, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }

        public int update(long _id, String name, String desc) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(my_helper.SUBJECT, name);
            contentValues.put(my_helper.DESC, desc);
            int i = database.update(my_helper.TABLE_NAME, contentValues, my_helper._ID + " = " + _id, null);
            return i;
        }

        public void delete(long _id) {
            database.delete(my_helper.TABLE_NAME, my_helper._ID + "=" + _id, null);
        }
}
