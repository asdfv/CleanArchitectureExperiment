package by.grodno.vasili.presentation.feature.notedetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.databinding.ActivityDetailsBinding;
import by.grodno.vasili.presentation.feature.common.BaseActivity;
import timber.log.Timber;

public class DetailsActivity extends BaseActivity<ActivityDetailsBinding> {
    public static final String ID = "by.grodno.vasili.presentation.feature.notedetails.EXTRA.ID";
    private DetailsViewModel model;

    @Inject
    ViewModelProvider.Factory provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this, provider).get(DetailsViewModel.class);
        loadNoteAsync();
        model.noteLiveData.observe(this, noteItem -> binding.setNote(noteItem));
    }

    private void loadNoteAsync() {
        String id = getIntent().getStringExtra(ID);
        if (id == null) {
            Timber.e("Error taking extra with note ID from intent");
        } else {
            model.getNoteAsync(id);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_details;
    }
}
