package by.grodno.vasili.presentation.model;

/**
 * Model for present Note
 */
public class NoteItem {
    public String id;
    public String title;
    public String description;
    public String created;

    NoteItem(String id, String title, String description, String created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created = created;
    }
}
