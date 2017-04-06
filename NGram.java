import java.util.ArrayList;

/**
 * Created by sreenath on 6/04/2017.
 */
public class NGram {


    public static int calculateDist(String one, String two, int n){

        ArrayList<String> set1 = new ArrayList<String>();
        ArrayList<String> set2 = new ArrayList<String>();

        int distance = 0;

        int len1 = one.length();
        int len2 = two.length();

        if(n > len1 || n > len2){
            return 0;
        }

        int count = 0;
        int count2 = 0;

        char[] tmp1 = one.toCharArray();
        char[] tmp2 = two.toCharArray();

        String str2 = "#";
        String str1 = "#";

        for(int k = 0; k < n-1; k++){
            str1 = str1 + tmp1[k];
        }
        set1.add(str1);
        str1 = "";

        for(int i = 0; i < tmp1.length; i++){
            int j = i;
            while (count < n){
                if (j <= tmp1.length -1){
                    str1 = str1 + tmp1[j];
                    j++;
                }
                count++;
            }
            if (i == tmp1.length -1){
                str1 = str1 + "#";
                set1.add(str1);
            }
            else{
                set1.add(str1);
                str1 = "";
                count = 0;
            }
        }

        for(int k = 0; k < n-1; k++){
            str2 = str2 + tmp2[k];
        }
        set2.add(str2);
        str2 = "";

        for(int i = 0; i < tmp2.length; i++){
            int j = i;
            while (count2 < n){
                if (j <= tmp2.length -1){
                    str2 = str2 + tmp2[j];
                    j++;
                }
                count2++;
            }
            if (i == tmp2.length -1){
                str2 = str2 + "#";
                set2.add(str2);
            }
            else{
                set2.add(str2);
                str2 = "";
                count2 = 0;
            }
        }

        //calculate distance
        distance = set1.size() + set2.size() - 2 * numberOfMatches(set1,set2);
        return distance;
    }

    public static int numberOfMatches(ArrayList<String> wrd1, ArrayList<String> wrd2){

        int matches = 0;

        for(int i = 0; i < wrd1.size(); i++){
            for(int j = 0; j < wrd2.size(); j++){

                if(wrd1.get(i).equals(wrd2.get(j))){
                    matches = matches + 1;
                }
            }
        }

        return matches;

    }

}
