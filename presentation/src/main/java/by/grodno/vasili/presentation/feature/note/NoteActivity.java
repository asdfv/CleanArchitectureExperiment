package by.grodno.vasili.presentation.feature.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import by.grodno.vasili.presentation.R;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        findViewById(R.id.close_image).setOnClickListener(view -> finish());
    }
}
