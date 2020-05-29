package hr.sirotic.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.sirotic.noteapp.R;
import hr.sirotic.noteapp.database.entity.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private OnNoteClickListener listener;
    private List<Note> notes = new ArrayList<>();

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    public interface OnNoteClickListener {
        void onNoteClicked(int position);
    }

    public void setNoteListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;


        public NoteHolder(@NonNull View itemView, OnNoteClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            content = itemView.findViewById(R.id.noteContent);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onNoteClicked(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(int position) {
            title.setText(notes.get(position).getTitle());
            content.setText(notes.get(position).getContent());

        }
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new NoteHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

}