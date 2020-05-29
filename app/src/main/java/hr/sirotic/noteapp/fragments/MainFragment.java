package hr.sirotic.noteapp.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hr.sirotic.noteapp.MainActivity;
import hr.sirotic.noteapp.R;
import hr.sirotic.noteapp.adapter.NoteAdapter;
import hr.sirotic.noteapp.database.entity.Note;
import hr.sirotic.noteapp.databinding.FragmentMainBinding;
import hr.sirotic.noteapp.viewmodel.NoteViewModel;

import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_CONTENT_KEY;
import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_ID_KEY;
import static hr.sirotic.noteapp.constants.NotesConstants.NOTE_TITLE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements NoteAdapter.OnNoteClickListener, AddNoteFragment.OnNoteListener {
    private NoteAdapter adapter;
    private FragmentMainBinding binding;
    private NoteViewModel noteViewModel;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        binding.recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new NoteAdapter();

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        adapter.setNoteListener(this);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {

            adapter.setNotes(notes);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Boolean safetyCheckEnabled = sharedPreferences.getBoolean("deleteDialog", false);
                if (safetyCheckEnabled.equals(true)) {
                    new AlertDialog.Builder(getActivity())
                            .setCancelable(false)
                            .setTitle("Are you sure you want to delete this note?")
                            .setPositiveButton("Delete", (dialog, which) ->
                                    noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()))
                            ).setNegativeButton("Restore", (dialog, which) -> {
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }).show();

                } else {
                    noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(binding.recyclerView);

        binding.btnAdd.setOnClickListener(v -> {
                    ((MainActivity) getActivity()).startNewFragment(new AddNoteFragment(this));
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btnSettings) {
            ((MainActivity) getActivity()).startNewFragment(new SettingsFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNoteClicked(int position) {


        Note note = adapter.getNoteAt(position);

        Bundle bundle = new Bundle();
        bundle.putString(NOTE_TITLE_KEY, note.getTitle());
        bundle.putString(NOTE_CONTENT_KEY, note.getContent());
        bundle.putInt(NOTE_ID_KEY, note.getId());

        AddNoteFragment fragInfo = new AddNoteFragment(this);
        fragInfo.setArguments(bundle);
        ((MainActivity) getActivity()).startNewFragment(fragInfo);
    }

    @Override
    public void onNoteAdded(Note note) {
        noteViewModel.insert(note);
    }

    @Override
    public void onNoteUpdated(Note note) {
        noteViewModel.update(note);
    }


}
