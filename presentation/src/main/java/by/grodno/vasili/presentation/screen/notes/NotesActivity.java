package by.grodno.vasili.presentation.screen.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collection;

import javax.inject.Inject;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.di.Dependency;
import dagger.android.support.DaggerAppCompatActivity;

public class NotesActivity extends DaggerAppCompatActivity {
    private TextView label;

    @Inject
    Dependency dependency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        NotesViewModel model = ViewModelProviders.of(this).get(NotesViewModel.class);
        LiveData<Collection<Note>> notes = model.getNotesLiveData();
        label = findViewById(R.id.label);
        Observer<Collection<Note>> observer = receivedNotes -> {
            String size = String.valueOf(receivedNotes.size());
            label.setText(dependency.getName());
        };
        notes.observe(this, observer);
    }
}
