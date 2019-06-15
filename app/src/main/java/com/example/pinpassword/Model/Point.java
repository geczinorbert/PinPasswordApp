package com.example.pinpassword.Model;


import static java.lang.Math.sqrt;

public class Point {
    float xCoordinate;
    float yCoordinate;
    float fingerSize;
    long timeStamp;

    public Point() {
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public float getFingerSize() {
        return fingerSize;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setFingerSize(float fingerSize) {
        this.fingerSize = fingerSize;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static double Distance(Point x, Point y) {
        return sqrt((y.getxCoordinate() - x.getxCoordinate()) * (y.getxCoordinate() - x.getxCoordinate())
                + (y.getyCoordinate() - x.getyCoordinate()) * (y.getyCoordinate() - x.getyCoordinate()));
    }
}