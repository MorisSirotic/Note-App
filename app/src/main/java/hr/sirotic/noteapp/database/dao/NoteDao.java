package hr.sirotic.noteapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hr.sirotic.noteapp.database.entity.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getNotes();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);


}
