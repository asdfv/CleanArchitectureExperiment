package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.feature.note.NoteActivity;
import dagger.android.support.DaggerAppCompatActivity;

import static java.util.Collections.emptyList;

public class NotesActivity extends DaggerAppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingButton;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        NotesViewModel model = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel.class);
        NotesAdapter adapter = new NotesAdapter();
        recyclerView = findAndSetupRecyclerView(adapter);
        LiveData<List<NoteItem>> notes = model.getNotesLiveData();
        notes.observe(this, adapter::setNotes);

        findAndSetupFloatingButton();
    }

    private void findAndSetupFloatingButton() {
        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> startActivity(new Intent(this, NoteActivity.class)));
    }

    private RecyclerView findAndSetupRecyclerView(NotesAdapter adapter) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }
}

/**
 * Adapter for notes recycler view
 */
class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NoteItem> notes = emptyList();

    public void setNotes(List<NoteItem> notes) {
        this.notes = notes;
        // TODO: Heavy operation
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteItem item = notes.get(position);
        holder.idLabel.setText(item.id);
        holder.titleLabel.setText(item.title);
        holder.descriptionLabel.setText(item.description);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView idLabel;
        TextView titleLabel;
        TextView descriptionLabel;

        ViewHolder(View itemView) {
            super(itemView);
            idLabel = itemView.findViewById(R.id.id_label);
            titleLabel = itemView.findViewById(R.id.title_label);
            descriptionLabel = itemView.findViewById(R.id.description_label);
        }
    }
}
