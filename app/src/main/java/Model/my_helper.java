package Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class my_helper extends SQLiteOpenHelper {
    Context c;
    public static final String TABLE_NAME = "Hymns";

    // Table columns
    public static final String _ID = "_id";
    public static final String HINDI = "hindi";
    public static final String ENGLISH = "english";
    public static final String TAMIL = "tamil";
    public static final String TELUGU = "telugu";
    public static final String SUBJECT = "subject";
    public static final String DESC = "description";
    static final String DB_NAME = "My_DataBAse.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + HINDI + " TEXT NOT NULL, " + TAMIL + " TEXT NOT NULL, " + TELUGU + " TEXT NOT NULL, "  + ENGLISH + " TEXT NOT NULL, "+ DESC + " TEXT);";

    my_helper(Context c)
    {

        super(c,"My_Hymn",null,1);
        this.c=c;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
