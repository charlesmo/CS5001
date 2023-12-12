import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TextAlignment {


 public static void main(String[] args) throws FileNotFoundException {
   int length = 10 ;

  /**
 // deal with the wrong input argument
  int length = 0;
  try {
   length = Integer.parseInt(args[2]);
  }
  catch (NumberFormatException e) {
   length = 0;
  }
*/




  /** try the FileUtil Class
   String[] paragraphs = FileUtil.readFile("../java.txt");
  int wordsCount = paragraphs.length;
  for(String s:paragraphs){
   System.out.println(s);
  }
  System.out.println(wordsCount);
  */

  // Create a String List to record the paragraph information of the file
  List<String> paralist = new ArrayList<>();

 //  File textFile = new File(args[0]);
  File textFile = new File("../java.txt");
  Scanner paraScanner = new Scanner(textFile);
  int lineCount = 0;
  while (paraScanner.hasNextLine()){
   paralist.add(paraScanner.nextLine());
  // System.out.println(paralist.get(lineCount));
   lineCount++;
  }
  paraScanner.close();
 // System.out.println(lineCount);
 // System.out.println(paralist.size());

  // new string list for text after adjustment
  List<String> paralistNew = new ArrayList<>();

  for (int i=0; i< paralist.size(); i++) {
   paralistNew.addAll(Alignment.Justify( paralist.get(i),length ));
  }


  for (int i = 0 ; i<paralistNew.size();i++){
   System.out.println(paralistNew.get(i));
  }


 }
}