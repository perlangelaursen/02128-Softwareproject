package dtugroup.matchorskip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by perlangelaursen on 15/06/15.
 */
public class FinishDialogFragment extends DialogFragment {
    public interface FinishDialogListener {
        void onDialogPositiveClick();
        int getCurrentScore();
        void onDialogNegativeClick();
        int getHighestScore();
        void onDialogNeutralClick();
        void saveHighscore(int score, String name);
    }

    private FinishDialogListener finishDialogListener;
    private String name;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the FinishDialogListener so we can send events to the host
            finishDialogListener = (FinishDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement FinishDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.gameover));
        builder.setCancelable(false);
        if (finishDialogListener.getCurrentScore() > finishDialogListener.getHighestScore()) {
            builder.setMessage(getString(R.string.congratulations) + "\n\n" +
                    getString(R.string.youscored) + finishDialogListener.getCurrentScore()
                    + "\n\n" + getString(R.string.typeName));

            final EditText nameView = new EditText(getActivity());
            builder.setView(nameView);

            builder.setNeutralButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = nameView.getText().toString();
                    finishDialogListener.saveHighscore(finishDialogListener.getCurrentScore(), name);
                    finishDialogListener.onDialogNeutralClick();
                }
            });
        } else {
            builder.setMessage(getString(R.string.youscored) + finishDialogListener.getCurrentScore()
                    + "\n\n" + getString(R.string.playagain));
        }
        builder.setPositiveButton(getString(R.string.play), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishDialogListener.onDialogPositiveClick();
            }
        });

        builder.setNegativeButton(getString(R.string.main), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishDialogListener.onDialogNegativeClick();
            }
        });

        return builder.create();
    }

    public String getName() {
        return name;
    }
}
