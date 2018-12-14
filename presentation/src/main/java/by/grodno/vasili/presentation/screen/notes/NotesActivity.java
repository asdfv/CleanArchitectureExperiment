package by.grodno.vasili.presentation.screen.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Collection;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.R;

public class NotesActivity extends AppCompatActivity {
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        NotesViewModel model = ViewModelProviders.of(this).get(NotesViewModel.class);
        LiveData<Collection<Note>> notes = model.getNotesLiveData();
        label = findViewById(R.id.label);
        notes.observe(this, receivedNotes -> label.setText(receivedNotes.size()));
    }
}
