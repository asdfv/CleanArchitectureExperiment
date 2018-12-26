package by.grodno.vasili.presentation.feature.note;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.feature.common.BaseActivity;
import timber.log.Timber;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * Activity for add note functionality
 */
public class NoteActivity extends BaseActivity {
    public static final String NOTE_SAVED = "by.grodno.vasili.presentation.feature.note.EXTRA.NOTE_SAVED";
    private EditText titleInput;
    private EditText descriptionInput;
    private ImageView closeImage;
    private ImageView checkImage;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        NoteViewModel model = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel.class);
        findViews();
        setListeners(model);
    }

    private void setListeners(NoteViewModel model) {
        closeImage.setOnClickListener(view -> finish());
        checkImage.setOnClickListener(view -> {
                    String title = trim(titleInput.getText().toString());
                    String description = trim(descriptionInput.getText().toString());
                    if (validate(title, description)) {
                        Consumer<String> onSuccess = id -> {
                            showToast("Successfully saved " + id);
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(NOTE_SAVED, true);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        };
                        Consumer<Throwable> onError = error -> {
                            showToast("Saving error " + error.getLocalizedMessage());
                            Timber.e(error, "Error executing use case");
                            finish();
                        };
                        Note note = new Note(null, title, description);
                        model.saveNoteAsync(note, onSuccess, onError);
                    } else {
                        showToast("Please enter Title and Description");
                    }
                }
        );
    }

    private void findViews() {
        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.description_input);
        closeImage = findViewById(R.id.close_image);
        checkImage = findViewById(R.id.check_image);
    }

    private boolean validate(String title, String description) {
        return isNoneBlank(title, description);
    }
}
