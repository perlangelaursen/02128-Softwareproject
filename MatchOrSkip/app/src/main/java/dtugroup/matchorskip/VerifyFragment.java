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
        String[] onPreExecute();
        void onPostExecute(int results);
    }

    private Callbacks mCallbacks;

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

    public void start(String type) {
        new CheckImageID(mCallbacks).execute(type);
    }

    protected class CheckImageID extends AsyncTask<String, Void, Void> {
        private Callbacks callbacks;
        private String[] data;
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
            if(params[0].toLowerCase().equals("Bonus")) {
                if(data[0].toLowerCase().equals("keep") && idMatch(data[1], data[2])) {
                    addPoints = 2;
                } else if(data[0].toLowerCase().equals("skip") && idMatch(data[1], data[2])) {
                    addPoints = -1;
                }
            } else {
                if(data[0].toLowerCase().equals("keep") && idMatch(data[1], data[2])) {
                    addPoints = 2;
                } else if(data[0].toLowerCase().equals("skip") && idMatch(data[1], data[2])) {
                    addPoints = -1;
                }
            }

            return null;
        }

        private boolean idMatch(String id1, String id2) {
            return id1.equals(id2);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callbacks.onPostExecute(addPoints);
        }
    }
}
