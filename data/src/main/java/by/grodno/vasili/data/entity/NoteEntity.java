package by.grodno.vasili.data.entity;

import com.google.firebase.database.Exclude;

/**
 * The entity represent Note model in data layer
 */
public final class NoteEntity {
    @Exclude
    public String id;
    public String title;
    public String description;

    @SuppressWarnings("unused")
    public NoteEntity() {
    }

    public NoteEntity(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
