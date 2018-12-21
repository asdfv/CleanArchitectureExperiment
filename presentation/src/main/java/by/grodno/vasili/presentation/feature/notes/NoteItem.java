package by.grodno.vasili.presentation.feature.notes;

/**
 * Model for present Note
 */
class NoteItem {
    String id;
    String title;
    String description;

    NoteItem(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
