package edu.ucsd.cse110.successorator.ui.recurring;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.RecurringBinding;
import edu.ucsd.cse110.successorator.ui.dialog.AddGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.dialog.DropDownDialogFragment;

public class RecurringFragment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecurringBinding view = RecurringBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());
        view.placeholderRecurringText.setText(R.string.placeholder_recurring_text);
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
            var dialogFragment = AddGoalDialogFragment.newInstance();
//            dialogFragment.setCurrentDate(currentDate);
            dialogFragment.show(getSupportFragmentManager(), "AddGoalDialogFragment");
        }

        if (itemId == R.id.action_arrow_drop_down_button) {
            var dialogFragment = DropDownDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "dropdown_fragment");
        }

        return super.onOptionsItemSelected(item);
    }
}
