//package Depricated.Skystone;
//import android.os.SystemClock;
//import java.util.ArrayList;
//
//
//public class Tick {
//    long startTime, endTime; //Create startTime and endTime longs, in nanos so need to be longs
//    long sum = 0;
//    ArrayList<Long> timelog = new ArrayList<Long>();
//    ArrayList<Long> delay = new ArrayList<Long>();
//
//    public void updateStart(){
//        startTime = SystemClock.elapsedRealtimeNanos();
//        timelog.add(startTime);
//
//    }
//    public void updateEnd(){
//        endTime = SystemClock.elapsedRealtimeNanos();
//        delay.add((startTime - endTime)*10^-6);
//        //delay.toString()
//
//    }
//    public int getTicksPerSecond(){
//        for (int i = 0; i < timelog.size(); i++){
//            long sum =+ timelog.get(i);
//        }
//        if ((sum/10^9) > 1){
//            timelog.clear();
//            delay.clear();
//            return timelog.size();
//        } else {
//            return -1;
//        }
//    }
//}
