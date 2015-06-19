package dtugroup.matchorskip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by perlangelaursen on 15/06/15.
 */
public class FinishDialogFragment extends DialogFragment {
    public interface FinishDialogListener {
        void onDialogPositiveClick();
        int getCurrentScore();
        void onDialogNegativeClick();
        int getHighestScore();
        int getLowestScore();
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
        setCancelable(false);
        verifyIfScoreIsHighScore(builder);
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

    private void verifyIfScoreIsHighScore(AlertDialog.Builder builder) {
        if (isHighScore()) {
            typeOfHighScore(builder);

            final EditText nameView = setupEditText(builder);

            submitButton(builder, nameView);
        } else {
            builder.setMessage(getString(R.string.youscored) + finishDialogListener.getCurrentScore()
                    + "\n\n" + getString(R.string.playagain));
        }
    }

    private void submitButton(AlertDialog.Builder builder, final EditText nameView) {
        builder.setNeutralButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = nameView.getText().toString();
                finishDialogListener.saveHighscore(finishDialogListener.getCurrentScore(), name);
                finishDialogListener.onDialogNeutralClick();
            }
        });
    }

    private EditText setupEditText(AlertDialog.Builder builder) {
        final EditText nameView = new EditText(getActivity());
        InputFilter filter = new InputFilter.LengthFilter(10);
        nameView.setFilters(new InputFilter[] {filter});
        nameView.setSingleLine();
        FrameLayout frameLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(60, 0, 60, 0);
        nameView.setLayoutParams(params);
        frameLayout.addView(nameView);
        builder.setView(frameLayout);
        return nameView;
    }

    private void typeOfHighScore(AlertDialog.Builder builder) {
        if (isTheHighestScore()) {
            builder.setMessage(getString(R.string.congratulations) + "\n\n" +
                    getString(R.string.youscored) + finishDialogListener.getCurrentScore()
                    + "\n\n" + getString(R.string.typeName));
        } else {
            builder.setMessage(getString(R.string.congratulations2) + "\n\n" +
                    getString(R.string.youscored) + finishDialogListener.getCurrentScore()
                    + "\n\n" + getString(R.string.typeName));
        }
    }

    private boolean isTheHighestScore() {
        return finishDialogListener.getCurrentScore() > finishDialogListener.getHighestScore();
    }

    private boolean isHighScore() {
        return finishDialogListener.getCurrentScore() >= finishDialogListener.getLowestScore()
                && finishDialogListener.getCurrentScore()!=0;
    }

    public String getName() {
        return name;
    }
}
