package hr.sirotic.noteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import hr.sirotic.noteapp.database.dao.NoteDao;
import hr.sirotic.noteapp.database.entity.Note;

@Database(entities = {Note.class}, exportSchema = false, version = 7)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    private static String DB_NAME = "note_db";

    public abstract NoteDao NoteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
