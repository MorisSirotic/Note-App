package hr.sirotic.noteapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;

    public Note(String title, String content) {

        this.title = title;
        this.content = content;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }
}
