package com.sheygam.java_18_15_03_18_cw;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn, stopBtn, showDialogBtn;
    private ProgressBar myProgress;
    private TextView resultTxt;
    private MyTask myTask;
    private FrameLayout progressFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.start_btn);
        myProgress = findViewById(R.id.my_progress);
        resultTxt = findViewById(R.id.result_txt);
        stopBtn = findViewById(R.id.stop_btn);
        showDialogBtn = findViewById(R.id.show_dialog_btn);
        progressFrame = findViewById(R.id.progress_frame);
        progressFrame.setOnClickListener(null);
        showDialogBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
            myTask = new MyTask();
            MySecondTask mySecondTask = new MySecondTask(myTask);
            mySecondTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            myTask.execute(10);

//            try {
////                String result = myTask.get();
//                String result = myTask.get(5,TimeUnit.SECONDS);
//                Log.d("MY_TAG", "onClick: result = " + result);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
        }else if(v.getId() == R.id.stop_btn){
            myTask.cancel(false);
//            myTask.cancel(true);


        }else if(v.getId() == R.id.show_dialog_btn){
            showMyDialog();
        }
    }

    private void showMyDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view,null);
        final EditText inputName = view.findViewById(R.id.input_name);
        final EditText inputEmail = view.findViewById(R.id.input_email);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Form feedback")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = inputName.getText().toString();
                        String email = inputEmail.getText().toString();
                        Toast.makeText(MainActivity.this, name + " " + email, Toast.LENGTH_SHORT).show();
                    }
                })
//                .setCancelable(false)
                .create();
        dialog.show();
//        dialog.dismiss(); close dialog
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            myProgress.setVisibility(View.VISIBLE);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            progressFrame.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            resultTxt.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... args) {
            int count = args[0];
            for (int i = 0; i < count; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
                Log.d("MY_TAG", "doInBackground: " + i);

                if(isCancelled()){
                    return "Was stopped";
                }
            }
            return "All done";
        }

        @Override
        protected void onPostExecute(String result) {
            myProgress.setVisibility(View.INVISIBLE);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            resultTxt.setText(result);
            progressFrame.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled(String result) {
            myProgress.setVisibility(View.INVISIBLE);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            resultTxt.setText(result);
            progressFrame.setVisibility(View.GONE);
            super.onCancelled(result);

        }
    }


    class MySecondTask extends AsyncTask<Void,Void,Void>{
        private MyTask myTask;

        public MySecondTask(MyTask myTask) {
            this.myTask = myTask;
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            try {
//                String str = myTask.get();
//                Log.d("MY_TAG", "doInBackground MySecondTask: " + str);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }

            return null;
        }
    }
}
