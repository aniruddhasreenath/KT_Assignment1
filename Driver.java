/**
 * Created by sreenath on 5/04/2017.
 */
import java.util.*;
import java.io.*;
import java.lang.*;

public class Driver {

    public static ArrayList<String> names = new ArrayList<String>();
    public static ArrayList<String> upNames = new ArrayList<String>();
    public static ArrayList<String> test = new ArrayList<String>();
    public static ArrayList<String> persian = new ArrayList<String>();
    public static ArrayList<String> english = new ArrayList<String>();
    public static ArrayList<String> upEnglish = new ArrayList<String>();

    public static void main(String[] args) throws IOException{

        //reads all the data into the arrays
        readFromFiles();

        //convert all the english names to CAPS
        for(int i = 0; i < english.size(); i++){

            upEnglish.add(english.get(i).toUpperCase());
        }

        //makes predictions based on global edit distance
        //globalEditDistance();

    }

    public static void globalEditDistance(){

        int correctPrediction = 0;

        for(int i = 0; i < names.size() -1 ; i++){

            upNames.add(names.get(i).toUpperCase());
        }


        for (int i = 0; i < persian.size(); i++){

            ArrayList<Integer> tmp = new ArrayList<Integer>();

            for(int j = 0; j < upNames.size(); j++){

                //System.out.println(editDistance(test.get(i), upNames.get(j)) + " name " + upNames.get(j));

                tmp.add(editDistance(persian.get(i), upNames.get(j)));

            }

            //find min value
            int min = 100000;
            for(int k = 0; k < tmp.size(); k++){

                if (tmp.get(k) < min){

                    min = tmp.get(k);
                }
            }

            //find at which locations the min value occurs and extract the predicted names
            ArrayList<String> possibleNames = new ArrayList<String>();
            for (int m = 0; m < tmp.size(); m++){

                if (tmp.get(m) == min){
                    possibleNames.add(upNames.get(m));
                }


            }


            if (possibleNames.contains(upEnglish.get(i))){
                correctPrediction = correctPrediction + 1;
                System.out.println("predicted name(s) for: " + persian.get(i)+ " => " + possibleNames + " CORRECT PREDICTION ");
            }
            else{
                System.out.println("predicted name(s) for: " + persian.get(i)+ " => " + possibleNames);
            }

        }

        //print stats about the accuracy
        System.out.println(correctPrediction + " names were predicted correctly out of: " + persian.size());

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

    public static int editDistance(String one, String two){

        //parameters
        int ins = 1;
        int del = 1;
        int match = 0;
        int replace = 1;

        int lengthOne = one.length();
        int lengthTwo = two.length();

        int[][] matrix = new int[lengthTwo + 1][lengthOne + 1];

        for(int j = 1; j <= lengthTwo; j++){

            matrix[j][0] = j * ins;

        }

        for(int k = 1; k <= lengthOne; k++){

            matrix[0][k] = k * del;

        }

        for (int j = 1; j <= lengthTwo; j++){

            for (int k = 1; k <= lengthOne; k++){
                matrix[j][k] = findMinOrMax(matrix, j, k, ins, del, match, replace, one, two);
            }
        }



        //printMatrix(matrix);

        return matrix[lengthTwo][lengthOne];
    }

    public static int findMinOrMax(int[][] grid, int j, int k, int insert, int delete, int match, int replace, String wrd1, String wrd2){

        ArrayList<Integer> temp = new ArrayList<Integer>();

        int costOfDel = grid[j][k-1] + delete;

        int costOfIns = grid[j-1][k] + insert;

        int relaceOrMatch = grid[j-1][k-1] + matchOrReplace(wrd1,wrd2,j,k, match, replace);

        temp.add(costOfDel);
        temp.add(costOfIns);
        temp.add(relaceOrMatch);

        int min = 100000;
        int max = -100000;

        if (match < insert && match < delete && match < replace){

            for(int i = 0; i < temp.size(); i++){

                if (temp.get(i) < min){
                    min = temp.get(i);
                }

            }
            return min;
        }
        else{

            for(int i = 0; i < temp.size(); i++){

                if (temp.get(i) > max){
                    max = temp.get(i);
                }

            }
            return max;

        }

    }

    public static int matchOrReplace(String wrd1, String wrd2, int j, int k, int match, int replace){

        ArrayList<Character> set1 = new ArrayList<Character>();
        set1.add('A');
        set1.add('E');
        set1.add('H');
        set1.add('I');
        set1.add('O');
        set1.add('U');
        set1.add('W');
        set1.add('Y');
        ArrayList<Character> set2 = new ArrayList<Character>();
        set2.add('B');
        set2.add('P');
        set2.add('F');
        set2.add('V');
        ArrayList<Character> set3 = new ArrayList<Character>();
        set3.add('C');
        set3.add('G');
        set3.add('J');
        set3.add('K');
        set3.add('Q');
        set3.add('S');
        set3.add('X');
        set3.add('Z');
        ArrayList<Character> set4 = new ArrayList<Character>();
        set4.add('D');
        set4.add('T');
        ArrayList<Character> set5 = new ArrayList<Character>();
        set5.add('M');
        set5.add('N');


        //if there is a match return 0 else return 1
        if (wrd1.charAt(k-1) == wrd2.charAt(j-1)){

            return match;

        }
        else{

            char a = wrd1.charAt(k-1);
            char b = wrd2.charAt(j-1);

            //this is the cost of replacing
            if(set1.contains(a) && set1.contains(b) || set2.contains(a) && set2.contains(b) || set3.contains(a) && set3.contains(b)
                    || set4.contains(a) && set4.contains(b) || set5.contains(a) && set5.contains(b)){

                return replace;

            }
            
            return (replace + 1);
        }


    }

    public static void printMatrix(int[][] mat){

        int a = mat.length;
        int b = mat[0].length;


        for(int i = 0; i < a; i++){
            for(int j = 0; j < b; j++){
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }

}
