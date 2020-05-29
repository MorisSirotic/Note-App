package hr.sirotic.noteapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import hr.sirotic.noteapp.database.entity.Note;
import hr.sirotic.noteapp.repository.NoteRepository;


public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    private LiveData<List<Note>> allNotes;

    public NoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getNotes();

    }


    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
