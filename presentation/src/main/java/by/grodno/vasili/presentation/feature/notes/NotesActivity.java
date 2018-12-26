package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
    private RecyclerView recyclerView;
    private FloatingActionButton floatingButton;
    private NotesViewModel model;

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
        findAndSetupRecyclerView(adapter);
        findAndSetupFloatingButton();
        setupSwipeToDelete();
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


    private void findAndSetupFloatingButton() {
        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> startActivity(new Intent(this, NoteActivity.class)));
    }

    private void findAndSetupRecyclerView(NotesAdapter adapter) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
