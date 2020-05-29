package hr.sirotic.noteapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hr.sirotic.noteapp.MainActivity;
import hr.sirotic.noteapp.R;
import hr.sirotic.noteapp.database.entity.Note;
import hr.sirotic.noteapp.databinding.FragmentAddNoteBinding;

import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_CONTENT_KEY;
import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_ID_KEY;
import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_TITLE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends Fragment {
    private FragmentAddNoteBinding binding;
    private OnNoteListener listener;

    public AddNoteFragment(OnNoteListener listener) {
        // Required empty public constructor
        this.listener = listener;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null && !bundle.isEmpty()) {
            String title = bundle.getString(NOTE_TITLE_KEY);
            String content = bundle.getString(NOTE_CONTENT_KEY);
            binding.etTitle.setText(title);
            binding.etContent.setText(content);
        }
        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btnSaveNote) {
            Bundle bundle = this.getArguments();

            String title = binding.etTitle.getText().toString();
            String content = binding.etContent.getText().toString();
            Note note = new Note(title, content);

            if (bundle == null) {
                listener.onNoteAdded(note);
            } else {
                int id = bundle.getInt(NOTE_ID_KEY);
                note.setId(id);
                listener.onNoteUpdated(note);
            }
        }
        ((MainActivity) getActivity()).startNewFragment(new MainFragment());

        return true;
    }


    public interface OnNoteListener {
        void onNoteAdded(Note note);

        void onNoteUpdated(Note note);
    }
}
