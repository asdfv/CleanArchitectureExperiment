package by.grodno.vasili.presentation.feature.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.model.NoteItem;

import static java.util.Collections.emptyList;

/**
 * Adapter for notes recycler view
 */
class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NoteItem> notes = emptyList();

    @Inject
    NotesAdapter() {
    }

    public void setNotes(List<NoteItem> notes) {
        this.notes = notes;
        // TODO: Heavy operation
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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