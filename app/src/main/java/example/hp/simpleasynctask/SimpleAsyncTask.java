package example.hp.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private int SLEEP_TIME;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Random r = new Random();
        int n = r.nextInt(11);
        SLEEP_TIME = n * 200;
        mProgressBar.get().setMax(SLEEP_TIME);
        mProgressBar.get().setProgress(0);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int n = SLEEP_TIME/200;
        try {
            for (int i = 1; i <= n; i++){
                Thread.sleep(200);
                publishProgress(i*200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for " + SLEEP_TIME + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mTextView.get().setText("Sleeping for " + values[0] + " miliseconds...");
        mProgressBar.get().incrementProgressBy(200);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}


