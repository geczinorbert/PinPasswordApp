package com.example.pinpassword.DataManipulation;




import android.content.Context;
import android.util.Log;


import com.example.pinpassword.Model.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileOperation {

    private static final String TAG = "MyTagFileOperation";
    private static final String FILE_HEADER = "S,D,X,Y,FingerSize,TimeStamp\n";
    public static void  writeToFile(String filename, String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e){
            Log.d( TAG, "File writing failed" + e.toString());
        }
    }



    public static ArrayList<ArrayList<Point>> readPointsFromFile(Context context, String filename) {

        ArrayList<ArrayList<Point>> pointArrayList = new ArrayList<>();
        ArrayList<Point> point1 = new ArrayList<>();
        ArrayList<Point> point2 = new ArrayList<>();
        ArrayList<Point> point3 = new ArrayList<>();
        ArrayList<Point> point4 = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(receiveString.equals(FILE_HEADER)){
                        continue;
                    }
                    String [] assetString = receiveString.split(",");
                    if(assetString[1].equals("d1")){
                        Point temp = new Point();
                        temp.setxCoordinate(Float.parseFloat(assetString[2]));
                        temp.setyCoordinate(Float.parseFloat(assetString[3]));
                        point1.add(temp);
                    }
                    if(assetString[1].equals("d2")){
                        Point temp1 = new Point();
                        temp1.setxCoordinate(Float.parseFloat(assetString[2]));
                        temp1.setyCoordinate(Float.parseFloat(assetString[3]));
                        point2.add(temp1);
                    }
                    if(assetString[1].equals("d3")){
                        Point temp2 = new Point();
                        temp2.setxCoordinate(Float.parseFloat(assetString[2]));
                        temp2.setyCoordinate(Float.parseFloat(assetString[3]));
                        point3.add(temp2);
                    }
                    if(assetString[1].equals("d4")){
                        Point temp3 = new Point();
                        temp3.setxCoordinate(Float.parseFloat(assetString[2]));
                        temp3.setyCoordinate(Float.parseFloat(assetString[3]));
                        point4.add(temp3);
                    }

                    pointArrayList.add(point1);
                    pointArrayList.add(point2);
                    pointArrayList.add(point3);
                    pointArrayList.add(point4);
                    stringBuilder.append(receiveString);

                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.d( TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.d( TAG, "Can not read file: " + e.toString());
        }

        return pointArrayList;
    }

    public static String pointsToString(ArrayList<Point> points, int sample_number, int digit){
        String ret = "";
        ret = FILE_HEADER;

        for(Point point : points){
            ret = ret + "s" + sample_number + "," + "d" + digit + "," + point.getxCoordinate() + "," + point.getyCoordinate() + ","  + point.getFingerSize() + "," +
                    point.getTimeStamp()  + "\n";
        }
        return ret;
    }
    public static ArrayList<ArrayList<ArrayList<Point>>> getArrayOfSamples(Context context, int sample_number){
        ArrayList<ArrayList<ArrayList<Point>>> sample_array = new ArrayList<>();
        for(int i = 1; i <= sample_number; ++i){
            String file_name = "my_files" + i + ".csv";
            ArrayList<ArrayList<Point>> pointArrayList = readPointsFromFile(context, file_name);
            sample_array.add(pointArrayList);
        }
        return sample_array;
    }
}
