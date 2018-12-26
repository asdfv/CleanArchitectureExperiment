package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.feature.common.BaseActivity;
import by.grodno.vasili.presentation.feature.note.NoteActivity;
import by.grodno.vasili.presentation.model.NoteItem;

public class NotesActivity extends BaseActivity {
    private static final int ADD_NOTE_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingButton;
    private NotesViewModel model;
    private SwipeRefreshLayout swipeContainer;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        model = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel.class);
        LiveData<List<NoteItem>> notes = model.getNotesLiveData();
        notes.observe(this, adapter::setNotes);
        findAndSetupViews();
        setupSwipeToDelete();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            boolean noteSaved = data.getBooleanExtra(NoteActivity.NOTE_SAVED, false);
            if (noteSaved) {
                updateRecyclerView();
            }
        }
    }

    private void updateRecyclerView() {
        model.reloadData();
    }

    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                String id = adapter.getItem(position).id;
                Runnable onSuccess = () -> showToast("Successfully removed id = " + id);
                model.removeNoteAsync(id, position, onSuccess);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void findAndSetupViews() {
        findAndSetupFloatingButton();
        findAndSetupRecyclerView();
        findAndSetupSwipeContainer();
    }

    private void findAndSetupFloatingButton() {
        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> startActivityForResult(new Intent(this, NoteActivity.class), ADD_NOTE_REQUEST_CODE));
    }

    private void findAndSetupSwipeContainer() {
        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(() -> model.reloadData(() -> swipeContainer.setRefreshing(false)));
    }

    private void findAndSetupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
