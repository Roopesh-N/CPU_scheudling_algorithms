package com.example.osproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Integer.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int[] arrivalTimesIntArray;
    private int[] burstTimesIntArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner algorithms=findViewById(R.id.spinner);
        EditText NoOfProcess=findViewById(R.id.editTextNumber);
        EditText arrivalTimes=findViewById(R.id.arrivalTimes);
        EditText burstTimes=findViewById(R.id.serviceTimes);

        final TextView waitTimesView=findViewById(R.id.waitTimesView);
        final TextView turnAroundTimesView=findViewById(R.id.turnAroundTimesView);
        final TextView avgTurnAroundTimeView=findViewById(R.id.avgTurnAroundTimeView);



        Button confirm=findViewById(R.id.button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countOfProcess= parseInt(NoOfProcess.getText().toString());
                String selectedAlg=algorithms.getSelectedItem().toString();

                if(!NoOfProcess.getText().toString().isEmpty() && !arrivalTimes.getText().toString().isEmpty() && !burstTimes.getText().toString().isEmpty()) {

                    //code to convert arrival times string to a list starts here
                    String arrivalTimesString = arrivalTimes.getText().toString();
                    String[] arrivalTimesStringArray = arrivalTimesString.split("\\s+");
                    arrivalTimesIntArray = new int[arrivalTimesStringArray.length];
                    for (int i = 0; i < arrivalTimesStringArray.length; i++) {
                        arrivalTimesIntArray[i] = parseInt(arrivalTimesStringArray[i]);
                    }
                    //code to convert Arrival times string to a list ends here

                    //code to convert burst times string to a list starts here
                    String burstTimesString = burstTimes.getText().toString();
                    String[] burstTimesStringArray = burstTimesString.split("\\s+");
                    burstTimesIntArray = new int[burstTimesStringArray.length];
                    int sumOfBurstTimes=0;
                    for(int i = 0; i < burstTimesStringArray.length; i++) {
                        burstTimesIntArray[i] = parseInt(burstTimesStringArray[i]);
                        sumOfBurstTimes=sumOfBurstTimes+burstTimesIntArray[i];
                    }
                    int quantum= (int) Math.ceil(sumOfBurstTimes/countOfProcess);
                    //code to convert burst times string to a list ends here

                    /*
                    for (int item : arrivalTimesIntArray) {
                        Log.d(TAG, "" + item);
                    }
                    for (int item : burstTimesIntArray) {
                        Log.d(TAG, "" + item);
                    }
                    */
                    if (arrivalTimesIntArray.length !=burstTimesIntArray.length || countOfProcess != arrivalTimesIntArray.length || countOfProcess != burstTimesStringArray.length){
                        Toast.makeText(getApplicationContext(),"Wrong Data Provided",Toast.LENGTH_LONG).show();
                    }
                    else{
                        switch(selectedAlg){
                            case "First Come First Serve":
                                int[] fcfswaitlist=fcfsAlgorithm(countOfProcess,burstTimesIntArray,arrivalTimesIntArray);
                                int[] fcfsturnaroundtimes= getTurnAroundTime(fcfswaitlist,burstTimesIntArray);
                                String fcfswaitTimeString= Arrays.toString(fcfswaitlist);
                                Log.d(TAG,"Waiting Times:"+fcfswaitTimeString);
                                waitTimesView.setText("wait Times: "+fcfswaitTimeString);

                                String fcfsturnAroundTime=Arrays.toString(fcfsturnaroundtimes);
                                Log.d(TAG,"Turn Around Times:"+fcfsturnAroundTime);
                                turnAroundTimesView.setText("Turn Around Times: "+fcfsturnAroundTime);

                                float fcfsavgTurnAroundTime=getAvgTurnAroundTime(fcfsturnaroundtimes);
                                Log.d(TAG,"Avg TurnAround Time: "+fcfsavgTurnAroundTime);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + fcfsavgTurnAroundTime);
                                break;

                            case "Round Robin":
                                int[] rrwaitlist=rrAlgorithm(countOfProcess,burstTimesIntArray,arrivalTimesIntArray,5);
                                int[] rrturnaroundtimes= getTurnAroundTime(rrwaitlist,burstTimesIntArray);
                                String rrwaitTimeString= Arrays.toString(rrwaitlist);
                                waitTimesView.setText("wait Times: "+rrwaitTimeString);
                                String rrturnAroundTime=Arrays.toString(rrturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+rrturnAroundTime);
                                float rravgTurnAroundTime=getAvgTurnAroundTime(rrturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + rravgTurnAroundTime);
                                break;
                            case "Shortest Process Next":
                                int[] spnwaitlist=spnAlgorithm(countOfProcess,burstTimesIntArray,arrivalTimesIntArray);
                                int[] spnturnaroundtimes= getTurnAroundTime(spnwaitlist,burstTimesIntArray);
                                String spnwaitTimeString= Arrays.toString(spnwaitlist);
                                waitTimesView.setText("wait Times: "+spnwaitTimeString);

                                String spnturnAroundTime=Arrays.toString(spnturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+spnturnAroundTime);

                                float spnavgTurnAroundTime=getAvgTurnAroundTime(spnturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + spnavgTurnAroundTime);
                                break;

                            case "Shortest Remaining Time":
                                int[] srtwaitlist=srtAlgorithm(countOfProcess,burstTimesIntArray,arrivalTimesIntArray);
                                int[] srtturnaroundtimes= getTurnAroundTime(srtwaitlist,burstTimesIntArray);
                                String srtwaitTimeString= Arrays.toString(srtwaitlist);
                                waitTimesView.setText("wait Times: "+srtwaitTimeString);

                                String srtturnAroundTime=Arrays.toString(srtturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+srtturnAroundTime);

                                float srtavgTurnAroundTime=getAvgTurnAroundTime(srtturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + srtavgTurnAroundTime);
                                break;

                            case "Highest Response Ratio Next":
                                int[] hrrnwaitlist=hrrnAlgorithm(countOfProcess,burstTimesIntArray,arrivalTimesIntArray);
                                int[] hrrnturnaroundtimes= getTurnAroundTime(hrrnwaitlist,burstTimesIntArray);
                                String hrrnwaitTimeString= Arrays.toString(hrrnwaitlist);
                                waitTimesView.setText("wait Times: "+hrrnwaitTimeString);

                                String hrrnturnAroundTime=Arrays.toString(hrrnturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+hrrnturnAroundTime);

                                float hrrnavgTurnAroundTime=getAvgTurnAroundTime(hrrnturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + hrrnavgTurnAroundTime);
                                break;

                            case "Multi-Level Feedback":
                                int[] mlfwaitlist=multiLevelFeedbackAlg(countOfProcess,burstTimesIntArray,arrivalTimesIntArray,quantum);
                                int[] mlfturnaroundtimes= getTurnAroundTime(mlfwaitlist,burstTimesIntArray);
                                String mlfwaitTimeString= Arrays.toString(mlfwaitlist);
                                waitTimesView.setText("wait Times: "+mlfwaitTimeString);

                                String mlfturnAroundTime=Arrays.toString(mlfturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+mlfturnAroundTime);

                                float mlfavgTurnAroundTime=getAvgTurnAroundTime(mlfturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + mlfavgTurnAroundTime);
                                break;

                            case "New Algorithm":
                                int[] rpfwaitlist=recentProcessFirst(countOfProcess,burstTimesIntArray,arrivalTimesIntArray);
                                int[] rpfturnaroundtimes= getTurnAroundTime(rpfwaitlist,burstTimesIntArray);
                                String rpfwaitTimeString= Arrays.toString(rpfwaitlist);
                                waitTimesView.setText("wait Times: "+rpfwaitTimeString);

                                String rpfturnAroundTime=Arrays.toString(rpfturnaroundtimes);
                                turnAroundTimesView.setText("Turn Around Times: "+rpfturnAroundTime);

                                float rpfavgTurnAroundTime=getAvgTurnAroundTime(rpfturnaroundtimes);
                                avgTurnAroundTimeView.setText("Avg TurnAround Time: " + rpfavgTurnAroundTime);
                                break;

                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Insufficient Data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public int[] fcfsAlgorithm(int noOfProcess, int[] burstTimes, @NonNull int[] arrivalTimes){
        //fcfs Algorithm here
        int waitTimes[]=new int[noOfProcess];
        int service_time[] = new int[noOfProcess];
        int turnaroundtimes[]=new int[noOfProcess];
        service_time[0] = arrivalTimes[0];
        waitTimes[0] = 0;
        // calculating waiting time
        for (int i = 1; i < noOfProcess ; i++)
        {
            //representing wasted time in queue
            int wasted=0;
            // Add burst time of previous processes
            service_time[i] = service_time[i-1] + burstTimes[i-1];

            // Find waiting time for current process = sum - at[i]
            waitTimes[i] = service_time[i] - arrivalTimes[i];

            /* If waiting time for a process is in negative that means it is already in the ready queue before
            CPU becomes idle so its waiting time is 0 . wasted time is basically time for process to wait after a
            process is over */
            if (waitTimes[i] < 0) {
                wasted = Math.abs(waitTimes[i]);
                waitTimes[i] = 0;
            }
            //Add wasted time
            service_time[i] = service_time[i] + wasted;
        }
        // Calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < noOfProcess ; i++)
            turnaroundtimes[i] = burstTimes[i] + waitTimes[i];
        return waitTimes;
    }

    public int[] rrAlgorithm(int noOfProcess, int[] burstTimes, int[] arrivalTimes,int quantum) {

        int[] pro,rem,waitList,ta,cp;
        int time=0,flag=0;

        pro=new int[noOfProcess];
        waitList=new int[noOfProcess];
        ta=new int[noOfProcess];
        rem=new int[noOfProcess];
        cp=new int[noOfProcess];
        for(int i=0;i<noOfProcess;++i){
            rem[i]=pro[i]=burstTimes[i];
        }
        int it=0,j=0;
        float t=0.0f,b=0.0f;
        time=arrivalTimes[0];
        do{
            j=0;
            flag=0;
            for(int i=0;i<noOfProcess;i++){
                if((arrivalTimes[i]<=time)&&(rem[i]>0)){
                    flag=1;
                    time=time+quantum;
                    if(rem[i]<quantum)
                        time=time-(quantum-rem[i]);

                    rem[i]=rem[i]-quantum;
                    cp[i]=time;
                }
            }
            if(flag==0){
                it=it+1;
                time=time+1;
            }
            for(int i=0;i<noOfProcess;i++)
                if(rem[i]>0)
                    j=1;
        }while(j==1);
        for(int i=0;i<noOfProcess;i++){
            ta[i]=cp[i]-arrivalTimes[i];
            waitList[i]=ta[i]-pro[i];
            b+=waitList[i];
            t+=ta[i];
        }
        return waitList;
    }


    public int[] spnAlgorithm(int noOfProcess, int[] burstTimes, int[] arrivalTimes){

        int pid[] = new int[noOfProcess];

        int ct[] = new int[noOfProcess]; // ct means complete time
        int ta[] = new int[noOfProcess]; // ta means turn around time
        int wt[] = new int[noOfProcess];  //wt means waiting time
        int f[] = new int[noOfProcess];  // f means it is flag it checks process is completed or not
        int st=0, tot=0;

        boolean a = true;
        while(true){
            int c=noOfProcess, min=999;
            if (tot == noOfProcess) {// total no of process = completed process loop will be terminated
                break;
            }
            for (int i=0; i<noOfProcess; i++)	{
                /*
                 * If i'th process arrival time <= system time and its flag=0 and burst<min
                 * That process will be executed first
                 */
                if ((arrivalTimes[i] <= st) && (f[i] == 0) && (burstTimes[i]<min))
                {
                    min=burstTimes[i];
                    c=i;
                }
            }
            /* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
            if (c==noOfProcess){
                st++;
            }
            else{
                ct[c]=st+burstTimes[c];
                st+=burstTimes[c];
                ta[c]=ct[c]-arrivalTimes[c];
                wt[c]=ta[c]-burstTimes[c];
                f[c]=1;
                tot++;
            }
        }
        return wt;
    }

    public int[] srtAlgorithm(int noOfProcess, int[] burstTimes, int[] arrivalTimes){
        int rt[] = new int[noOfProcess];
        int waitTime[]=new int[noOfProcess];
        // Copy the burst time into rt[]
        for (int i = 0; i < noOfProcess; i++)
            rt[i] = burstTimes[i];
        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;
        // Process until all processes gets
        // completed
        while (complete != noOfProcess) {
            // Find process with minimum
            // remaining time among the
            // processes that arrives till the
            // current time`
            for (int j = 0; j < noOfProcess; j++)
            {
                if ((arrivalTimes[j] <= t) &&
                        (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }
            if (check == false) {
                t++;
                continue;
            }
            // Reduce remaining time by one
            rt[shortest]--;

            // Update minimum
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely
            // executed
            if (rt[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current
                // process
                finish_time = t + 1;

                // Calculate waiting time
                waitTime[shortest] = finish_time - burstTimes[shortest] - arrivalTimes[shortest];
                if (waitTime[shortest] < 0)
                    waitTime[shortest] = 0;
            }
            // Increment time
            t++;
        }
        return waitTime;
    }
    public int[] hrrnAlgorithm(int noOfProcess, int[] burstTimes, int[] arrivalTimes){

        int i, j, sum_bt = 0;
        char c;
        int[] waitTimes=new int[noOfProcess];
        float t=0;
        int[] completedStatus=new int[noOfProcess];

        // Initializing the structure variables
        for (i = 0; i < noOfProcess; i++) {

            // Variable for sum of all Burst Times
            sum_bt += burstTimes[i];
        }

        for (t =arrivalTimes[0]; t < sum_bt;) {

            // Set lower limit to response ratio
            float hrr = -9999;

            // Response Ratio Variable
            float temp;

            // Variable to store next process selected
            int loc = -1;
            for (i = 0; i < noOfProcess; i++) {

                // Checking if process has arrived and is
                // Incomplete
                if (arrivalTimes[i] <= t && completedStatus[i] != 1) {

                    // Calculating Response Ratio
                    temp = (burstTimes[i] + (t - arrivalTimes[i])) / burstTimes[i];

                    // Checking for Highest Response Ratio
                    if (hrr < temp) {
                        // Storing Response Ratio
                        hrr = temp;
                        // Storing Location
                        loc = i;
                    }
                }
            }
            // Updating time value
            t += burstTimes[loc];

            // Calculation of waiting time
            waitTimes[loc] = (int)(t - arrivalTimes[loc] - burstTimes[loc]);
            // Updating Completion Status
            completedStatus[loc] = 1;

        }
        return waitTimes;
    }
    public int[] multiLevelFeedbackAlg(int noOfProcess, int[] burstTimes, int[] arrivalTimes,int quantum){
        int i, total = 0, x, counter = 0, j;

        int waitList[] = new int[noOfProcess], pos, z;
        int[] p = new int[10];
        int[] prio = new int[10];
        int[] temp = new int[10];
        int b;

        x = noOfProcess;
        for (i = 0; i < noOfProcess; i++) {
            p[i] = i + 1;
            prio[i] = 0;
            temp[i] = burstTimes[i];
        }
        for (total = 0, i = 0; x != 0; ) {
            for (z = 0; z < noOfProcess; z++) {
                int temp1;
                pos = z;
                for (j = z + 1; j < noOfProcess; j++) {
                    if (prio[j] < prio[pos])
                        pos = j;
                }
                temp1 = prio[z];
                prio[z] = prio[pos];
                prio[pos] = temp1;

                temp1 = burstTimes[z];
                burstTimes[z] = burstTimes[pos];
                burstTimes[pos] = temp1;

                temp1 = arrivalTimes[z];
                arrivalTimes[z] = arrivalTimes[pos];
                arrivalTimes[pos] = temp1;

                temp1 = p[z];
                p[z] = p[pos];
                p[pos] = temp1;

                temp1 = temp[z];
                temp[z] = temp[pos];
                temp[pos] = temp1;
            }
            if (temp[i] <= quantum && temp[i] > 0) {
                total = total + temp[i];
                temp[i] = 0;
                counter = 1;
            } else if (temp[i] > 0) {
                temp[i] = temp[i] - quantum;
                total = total + quantum;
            }
            for (b = 0; b < noOfProcess; b++) {
                if (b == i)
                    prio[b] += 1;
                else
                    prio[b] += 2;
            }
            if (temp[i] == 0 && counter == 1) {
                x--;

                waitList[i] = total - arrivalTimes[i] - burstTimes[i];
                counter = 0;
            }

            if (i == noOfProcess - 1) {
                i = 0;
            } else if (arrivalTimes[i + 1] <= total) {
                i++;
            } else {
                i = 0;
            }
        }
        return waitList;
    }

    public int[] recentProcessFirst(int noOfProcess, int[] burstTimes, int[] arrivalTimes ) {
                int waitTimes[] = new int[noOfProcess];
                int service_time[] = new int[noOfProcess];
                int turnaroundtimes[] = new int[noOfProcess];
                int wastedTime = 0;

                service_time[0] = arrivalTimes[0];
                waitTimes[0] = 0;

                for (int i = 1; i < noOfProcess; i++) {
                    int currentProcessArrivalTime = arrivalTimes[i];
                    int currentProcessBurstTime = burstTimes[i];

                    // Calculate wasted time for the current process
                    wastedTime = Math.max(0, service_time[i - 1] + burstTimes[i - 1] - currentProcessArrivalTime);

                    // Add wasted time to service time
                    service_time[i] = Math.max(service_time[i - 1], currentProcessArrivalTime) + wastedTime;

                    // Calculate waiting time for the current process
                    waitTimes[i] = service_time[i] - currentProcessArrivalTime;

                    // Update service time to include the current process burst time
                    service_time[i] += currentProcessBurstTime;
                }

                for (int i = 0; i < noOfProcess; i++)
                    turnaroundtimes[i] = burstTimes[i] + waitTimes[i];

                return waitTimes;
        }

    public int[] getTurnAroundTime(int[] waitList,int[] burstTimeList){
        int[] turnaroundtimelist=new int[waitList.length];

        //getting turn around times here with burstTime+waitTime
        for (int i = 0; i < waitList.length ; i++) {
            turnaroundtimelist[i] = burstTimeList[i] + waitList[i];
        }
        return turnaroundtimelist;
    }
    public float getAvgTurnAroundTime(int[] turnAroundTimes){
        float totalTurnAroundTime=0;
        float avgTurnAroundTime;
        for (int i = 0; i < turnAroundTimes.length ; i++) {
            totalTurnAroundTime = totalTurnAroundTime + turnAroundTimes[i];
        }
        avgTurnAroundTime=totalTurnAroundTime/turnAroundTimes.length;
        return avgTurnAroundTime;
    }
}