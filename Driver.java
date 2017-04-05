/**
 * Created by sreenath on 5/04/2017.
 */
import java.util.*;
import java.io.*;
import java.lang.*;

public class Driver {

    public static ArrayList<String> names = new ArrayList<String>();
    public static ArrayList<String> test = new ArrayList<String>();
    public static ArrayList<String> persian = new ArrayList<String>();
    public static ArrayList<String> english = new ArrayList<String>();

    public static void main(String[] args) throws IOException{

        //reads all the data into the arrays
        readFromFiles();

        

    }

    public static void readFromFiles() throws IOException{

        BufferedReader reader1 = new BufferedReader(new FileReader("names.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader("test.txt"));
        BufferedReader reader3 = new BufferedReader(new FileReader("train.txt"));


        String str1 = reader1.readLine();
        String str2 = reader2.readLine();
        String str3 = reader3.readLine();


        while (str1 != null) {
            str1 = reader1.readLine();

            names.add(str1);

        }
        while (str2 != null) {
            String[] tmp;
            if(str2 != null){
                tmp = str2.split("\\t");
                test.add(tmp[0]);
            }
            str2 = reader2.readLine();

        }
        while (str3 != null) {

            String[] tmp2;

            if (str3 != null){
                tmp2 = str3.split("\\t");
                persian.add(tmp2[0]);
                english.add(tmp2[1]);
            }

            str3 = reader3.readLine();

        }

        reader1.close();
        reader2.close();
        reader3.close();

    }

    public static void editGlobalDistance(){



    }

}
