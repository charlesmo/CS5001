import java.util.ArrayList;
import java.util.Scanner;

public class Alignment {
   private String paragraph;

   public static String Right(String line,int length){
       //check if the last char is space
       if (line.endsWith(" ")){
           line = line.substring(0,line.length()-1);
       }
       String space = "";
       for (int i= 0 ; i < (length- line.length()); i++){
           space += " ";
       }

       return space + line ;
   }

    public static String Centre(String line,int length){
        //check if the last char is space
        if (line.endsWith(" ")){
            line = line.substring(0,line.length()-1);
        }
        String space = "";
        int spaceNum = length - line.length();
        for (int i= 0 ; i < spaceNum / 2 ; i++){
            space += " ";
        }
        if (spaceNum % 2 == 0){
            return space + line;
        }else{
            return space + " " + line ;
        }

    }



   public  static  ArrayList<String> adjust(String para,int length){
       ArrayList<String> paraNew = new ArrayList<>(); // ArrayList for strings after adjustment
       Scanner wordScanner = new Scanner(para);  // the scanner to go through each paragraph
       // int Listindex = 0;
       String stringline = "";
       String word;
       int currentlength = 0;

       while (wordScanner.hasNext()){
           word = wordScanner.next();
           if (currentlength + word.length() < length){
               stringline += word;
               currentlength += word.length();
               stringline += " ";
               currentlength += 1;
           } else if (currentlength + word.length() == length){ //if current length equal the length, then don't add space
               stringline += word;
               currentlength += word.length();
           }else {
               stringline = Centre(stringline,length);
               paraNew.add(stringline);
               currentlength = word.length();
               stringline = word;
               stringline += " ";
               currentlength += 1;
           }

       }

       if (stringline != "") {
           stringline = Centre(stringline,length);
           paraNew.add(stringline);
       }

       return paraNew;
   }

    public static ArrayList<String> Justify(String para,int length){
        ArrayList<String> paraNew = new ArrayList<>(); // ArrayList for strings after adjustment
        Scanner wordScanner = new Scanner(para);  // the scanner to go through each paragraph
        String stringline = "";
        String word;
        int currentlength = 0;

        while (wordScanner.hasNext()) {
            word = wordScanner.next();
            if (currentlength + word.length() < length -1){
                stringline += word;
                currentlength += word.length();
                stringline += " ";
                currentlength += 1;
            } else if ((currentlength + word.length() == length) || (currentlength + word.length() == length-1) ) { //if current length equal the length, then don't add space
                stringline += word;
                paraNew.add(stringline);

                stringline = "";
                currentlength = 0;
            }else{

                int num = length - stringline.length();
                if (num == 0 || num == 1){
                   // stringline += " ";
                   // currentlength += 1;
                    paraNew.add(stringline);
                    stringline = word ;
                    currentlength = stringline.length();
                }else {
                    stringline += word ;
                    currentlength = stringline.length();
                }

                if (stringline.length() > length  ) {
                    for (int i = 0; i < stringline.length(); i = i + length - 1) {
                        String stringTemp;
                        if (stringline.length() - i <= length ) {
                            stringTemp = stringline.substring(i);
                            stringline = stringTemp;
                            currentlength = stringline.length();

                        } else {
                            stringTemp = stringline.substring(i, i + length - 1) + "-";
                            paraNew.add(stringTemp);
                        }

                    }
                }
                if (stringline.length() == length) {
                    paraNew.add(stringline);
                    stringline = "";
                    currentlength = stringline.length();
                }

                if (stringline != ""){
                    stringline += " ";
                    currentlength = stringline.length();
                }

            }
        }
        if (stringline != ""){
            paraNew.add(stringline);
        }
        return paraNew;
    }
}