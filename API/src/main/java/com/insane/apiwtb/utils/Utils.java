package com.insane.apiwtb.utils;

import java.io.File;

public class Utils {

    public static String capitalizeFirst(String text){
        String captilizedString="";
        text = text.toLowerCase();
        if(!text.trim().equals("")){
            captilizedString = text.substring(0,1).toUpperCase() + text.substring(1);
        }
        return captilizedString;
    }

    public static String capitalize(String text) {
        String capitalizedSentence="";
        text = text.toLowerCase();
        String[] words = text.split(" ");
        String avoid = "de,des,da,das,do,dos";
        for (int i=0; i < words.length; i++ ) {
            if (!avoid.contains(words[i].toLowerCase())) {
                capitalizedSentence += capitalizeFirst(words[i])+" ";
            } else {
                capitalizedSentence += words[i]+" ";
            }
        }
        // Erases the last space added before on capitalization
        return capitalizedSentence.substring(0, capitalizedSentence.length() - 1);
    }

    public static boolean checkCreateFolder(String folderName) {
        File folder = new File(folderName);
        if (!folder.exists()) return folder.mkdirs();
        else return true;
    }

    public static boolean deleteFile(String file) {
        File myObj = new File(file);
        if (myObj.exists()) {
            return myObj.delete();
        } else {
            return false;
        }
    }

}