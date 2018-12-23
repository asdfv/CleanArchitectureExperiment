package by.grodno.vasili.presentation.feature.note;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import dagger.android.support.DaggerAppCompatActivity;

public class NoteActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        NoteViewModel model = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel.class);
        // TODO: fake using
        model.saveNoteAsync();

        findViewById(R.id.close_image).setOnClickListener(view -> finish());
    }
}
