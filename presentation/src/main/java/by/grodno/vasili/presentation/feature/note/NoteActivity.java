package by.grodno.vasili.presentation.feature.note;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.databinding.ActivityNoteBinding;
import by.grodno.vasili.presentation.feature.common.BaseActivity;
import timber.log.Timber;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * Activity for add note functionality
 */
public class NoteActivity extends BaseActivity<ActivityNoteBinding> {
    public static final String NOTE_SAVED = "by.grodno.vasili.presentation.feature.note.EXTRA.NOTE_SAVED";
    private NoteViewModel model;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setHandler(new NoteHandler());
        model = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_note;
    }

    /**
     * Handler class for Note view events
     */
    public class NoteHandler {

        /**
         * Finish NoteActivity
         */
        @SuppressWarnings("unused")
        public void close(View view) {
            finish();
        }

        /**
         * Create domain {@link Note} from entered by user fields and try to save it in ViewModel
         */
        @SuppressWarnings("unused")
        public void save(View view) {
            String title = trim(binding.titleInput.getText().toString());
            String description = trim(binding.descriptionInput.getText().toString());
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

        private boolean validate(String title, String description) {
            return isNoneBlank(title, description);
        }
    }
}
