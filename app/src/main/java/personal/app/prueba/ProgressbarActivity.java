package personal.app.prueba;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ProgressbarActivity extends AppCompatActivity {
    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick(){
        btnStartProgress = findViewById(R.id.button1);
        btnStartProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creando un progressBarDialog
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Descargando Archivo...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                //Restablecer el progressBar y el filestatus
                progressBarStatus = 0;
                fileSize = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressBarStatus < 100){
                            //Performing operation
                            progressBarStatus = doOperation();
                            try{
                                Thread.sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            //Updating the progress bar
                            progressBarHandler.post(new Runnable(){
                               public void run(){
                                   progressBar.setProgress(progressBarStatus);
                               }
                            });
                        }
                        //Performing operation if file is downloaded
                        if(progressBarStatus >= 100){
                            //Sleeping for 1 second after operation
                            try{
                                Thread.sleep(1000);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            //Close the progress bar dialog
                            progressBar.dismiss();
                        }
                    }
                }).start();
            }
        });
    }

    public int doOperation(){
        while(fileSize <= 10000) {
            fileSize++;
            if (fileSize == 1000) {
                return 10;
            } else if (fileSize == 2000) {
                return 20;
            } else if (fileSize == 3000) {
                return 30;
            } else if (fileSize == 4000) {
                return 40;
            }


        }//fin while
        return 100;
    }//fin doOperation

}