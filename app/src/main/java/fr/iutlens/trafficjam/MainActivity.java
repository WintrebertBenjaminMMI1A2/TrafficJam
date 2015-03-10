package fr.iutlens.trafficjam;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class MainActivity extends Activity {


    private TrafficView trafficView;
    private int tempsRestant;
    private ProgressBar timer;
    private ProgressBar pas_content;

    // Gestion du timer

    static class RefreshHandler extends Handler {
        WeakReference<MainActivity> weak;

        RefreshHandler(MainActivity animator){
            weak = new WeakReference(animator);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weak.get() == null) return;
            weak.get().update();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }

    }

    private RefreshHandler handler;

    private TextView nb_voit;

    private void update() {
        int getTmpstotal;
        handler.sleep(40);
        trafficView.act();
        int tmptotal = trafficView.getTmpstotal();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pas_content);
        progressBar.setProgress(tmptotal);
        //}
        int nbVoitures = trafficView.getNbVoitures(); // on récupère le nombre de voitures dans TrafficView
        nb_voit = (TextView) findViewById(R.id.nb_voit); // on récupère le TextView
        nb_voit.setText(""+nbVoitures);



        int a = 0;

        tempsRestant--;

        timer.setProgress(tempsRestant);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        handler = new RefreshHandler(this);

        trafficView = (TrafficView) findViewById(R.id.view);

        // on démarre l'animation
        timer =(ProgressBar)findViewById(R.id.timer);
        timer.setMax(1000);
        tempsRestant = 1000;
        update();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
