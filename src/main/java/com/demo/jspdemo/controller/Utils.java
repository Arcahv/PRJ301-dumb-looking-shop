package com.demo.jspdemo.controller;

public class Utils {
    public static String toDateTimeString(String date) {
        String[] dateParts = date.split(" ");
        String[] datePart = dateParts[0].split("-");
        String[] timePart = dateParts[1].split(":");
        return datePart[0] + "-" + datePart[1] + "-" + datePart[2] + " " + timePart[0] + ":" + timePart[1];
    }

    public static String convertToSQLDate(String date) {
        String[] arr = date.split("[Tt]");
        return arr[0] + " " + arr[1] + ":00.000";
    }

    public static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        return timestamp.toString();
    }

    public static String randomString30() {
        String uuid = java.util.UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, 30);
    }

    public static void main(String[] args) {
        System.out.println(randomString30());
    }
}
