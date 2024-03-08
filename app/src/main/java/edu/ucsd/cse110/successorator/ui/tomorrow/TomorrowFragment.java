package edu.ucsd.cse110.successorator.ui.tomorrow;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.databinding.TomorrowBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateHandler;
import edu.ucsd.cse110.successorator.ui.dialog.AddTomorrowGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;

public class TomorrowFragment extends AppCompatActivity {
    private DateHandler currentDate;

    TomorrowBinding view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = TomorrowBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());
        SuccessoratorApplication app = (SuccessoratorApplication) getApplication();
        currentDate = app.getCurrentDate();
        TextView dateTextView = findViewById(R.id.date_tomorrow_text);
//        currentDate.observe(new DateDisplay(dateTextView));
        dateTextView.setText(currentDate.getTomorrowDate());
        view.placeholderTomorrowText.setText(R.string.placeholder_tomorrow_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_add_button) {
            var dialogFragment = AddTomorrowGoalDialogFragment.newInstance();
//            dialogFragment.setCurrentDate(currentDate);
            dialogFragment.show(getSupportFragmentManager(), "AddTomorrowGoalDialogFragment");
        }

        if (itemId == R.id.action_arrow_drop_down_button) {
            var dialogFragment = DropDownDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "dropdown_fragment");
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDateMock() {
        view.dateMockButton.setOnClickListener(v -> {
            currentDate.skipDay();
        });
    }
}