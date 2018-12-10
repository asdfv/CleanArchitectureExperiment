package by.grodno.vasili.domain.model;

/**
 * Represent Note model in domain layer
 */
public final class Note {
    public String id;
    public String title;
    public String description;

    public Note(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
