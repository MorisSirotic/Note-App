package hr.sirotic.noteapp.repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

import hr.sirotic.noteapp.database.NoteDatabase;
import hr.sirotic.noteapp.database.dao.NoteDao;
import hr.sirotic.noteapp.database.entity.Note;

public class NoteRepository {

    private NoteDao dao;
    private LiveData<List<Note>> notes;

    public NoteRepository(Application context) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(context);
        dao = noteDatabase.NoteDao();
        notes = dao.getNotes();
    }

    public void insert(Note note) {
        new InsertTask(this).execute(note);
    }


    public LiveData<List<Note>> getNotes() {
        return notes;
    }


    public void update(Note note) {
        new UpdateTask(this).execute(note);
    }


    public void delete(Note note) {
        new DeleteTask(this).execute(note);
    }

    public static class InsertTask extends AsyncTask<Note, Void, Void> {
        private WeakReference<NoteRepository> weakReference;

        InsertTask(NoteRepository noteRepository) {
            weakReference = new WeakReference<>(noteRepository);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            weakReference.get().dao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateTask extends AsyncTask<Note, Void, Void> {

        private WeakReference<NoteRepository> weakReference;

        UpdateTask(NoteRepository noteRepository) {
            weakReference = new WeakReference<>(noteRepository);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            weakReference.get().dao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<Note, Void, Void> {
        private WeakReference<NoteRepository> weakReference;

        DeleteTask(NoteRepository noteRepository) {
            weakReference = new WeakReference<>(noteRepository);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            weakReference.get().dao.delete(notes[0]);
            return null;
        }
    }

}
