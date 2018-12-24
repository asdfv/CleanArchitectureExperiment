package by.grodno.vasili.presentation.feature.note;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.feature.common.BaseActivity;

public class NoteActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        NoteViewModel model = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel.class);
        // TODO: fake using
        Consumer<String> action = s -> showToast("Successfully saved " + s);
        model.saveNoteAsync(action);

        findViewById(R.id.close_image).setOnClickListener(view -> finish());
    }
}
