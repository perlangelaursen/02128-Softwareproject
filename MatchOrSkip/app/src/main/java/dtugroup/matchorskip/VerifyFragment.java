package dtugroup.matchorskip;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by perlangelaursen on 14/06/15.
 */
public class VerifyFragment extends Fragment {
    interface Callbacks {
        Image[] onPreExecute();
        void onPostExecute(int results, boolean input);
    }

    private Callbacks mCallbacks;
    private boolean right = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Not a TaskCallbacks");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void start(String input) {
        new CheckImageID(mCallbacks).execute(input);
    }

    protected class CheckImageID extends AsyncTask<String, Void, Void> {
        private Callbacks callbacks;
        private Image[] data;
        private int addPoints;

        public CheckImageID(Callbacks callbacks) {
            this.callbacks = callbacks;
        }

        @Override
        protected void onPreExecute() {
           data = callbacks.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            imageMatch(params);

            return null;
        }

        private void imageMatch(String[] params) {
            if(params[0].toLowerCase().equals("bonus")) {
                if(data[1].isBonus()) {
                    addPoints = 5;
                    right = true;
                } else {
                    addPoints = -1;
                    right = false;
                }
            } else {
                if(params[0].toLowerCase().equals("keep") &&
                        idMatch(data[0].getID(), data[1].getID())) {
                    addPoints = 2;
                    right = true;
                } else if(params[0].toLowerCase().equals("skip") &&
                        idMatch(data[0].getID(), data[1].getID())) {
                    addPoints = 0;
                    right = false;
                } else if (params[0].toLowerCase().equals("keep") &&
                        !idMatch(data[0].getID(), data[1].getID())) {
                    addPoints = -1;
                    right = false;
                } else if (params[0].toLowerCase().equals("skip") &&
                        !idMatch(data[0].getID(), data[1].getID())) {
                    addPoints = 0;
                    right = false;
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callbacks.onPostExecute(addPoints, right);
        }

        private boolean idMatch(String id1, String id2) {
            return id1.equals(id2);
        }
    }
}
