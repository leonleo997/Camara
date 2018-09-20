package co.edu.icesi.yeye.camara;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PhotoDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="fotosDB";
    public final static int DATABASE_VERSION = 1;


    public static final String TABLE_FOTOS="fotos";
    public static final String TABLE_FOTOS_ID="id";
    public static final String TABLE_FOTOS_DESCRIPCION="descripcion";
    public static final String TABLE_FOTOS_NAME="nombre";
    public static final String TABLE_FOTOS_URI="ruta";
    public static final String TABLE_FOTOS_DATE="fecha";




    public PhotoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOTOS_TABLE="CREATE TABLE "+TABLE_FOTOS+"(" +
                TABLE_FOTOS_ID + " TEXT PRIMARY KEY, "+
                TABLE_FOTOS_DESCRIPCION + " TEXT, "+
                TABLE_FOTOS_NAME + " TEXT , "+
                TABLE_FOTOS_URI + " TEXT , "+
                TABLE_FOTOS_DATE + " TEXT)";
        db.execSQL(CREATE_FOTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Después de un cambio estructural
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FOTOS);
        onCreate(db);
    }

    public void createFoto(Foto registro) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_FOTOS+ " (" +
                ""+TABLE_FOTOS_ID+", "+TABLE_FOTOS_DESCRIPCION+", "+
                TABLE_FOTOS_NAME+", "+TABLE_FOTOS_URI+", "+TABLE_FOTOS_DATE+
                ") "+"VALUES ('"+registro.getId()+"','"+registro.getDescripcion()+"'," +
                "'"+registro.getNombre()+"','"+registro.getRuta()+"'," +
                "'"+registro.getFecha()+"')");
        db.close();

    }

    public ArrayList<Foto> getAllFotos() {
    ArrayList<Foto> fotos= new ArrayList<Foto>();
    SQLiteDatabase db= this.getReadableDatabase();
    Cursor cursor= db.rawQuery("SELECT * FROM "+ TABLE_FOTOS, null);

    if(cursor!=null && cursor.moveToFirst()){
        do{
            Foto f=new Foto();
            f.setId(cursor.getString(cursor.getColumnIndex(TABLE_FOTOS_ID)));
            f.setDescripcion(cursor.getString(cursor.getColumnIndex(TABLE_FOTOS_DESCRIPCION)));
            f.setFecha(cursor.getString(cursor.getColumnIndex(TABLE_FOTOS_DATE)));
            f.setNombre(cursor.getString(cursor.getColumnIndex(TABLE_FOTOS_NAME)));
            f.setRuta(cursor.getString(cursor.getColumnIndex(TABLE_FOTOS_URI)));
            fotos.add(f);
        }while(cursor.moveToNext());
    }
    db.close();
    return  fotos;
    }


}
