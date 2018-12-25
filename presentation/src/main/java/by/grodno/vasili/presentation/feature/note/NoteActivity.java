package by.grodno.vasili.presentation.feature.note;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.feature.common.BaseActivity;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.trim;

public class NoteActivity extends BaseActivity {
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
                            finish();
                        };
                        Consumer<Throwable> onError = error -> {
                            showToast("Saving error " + error.getLocalizedMessage());
                            finish();
                        };
                        Note note = new Note(null, title, description);
                        model.saveNoteAsync(note, onSuccess, onError);
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
