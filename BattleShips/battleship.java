import java.util.Scanner;
/**
 * Write a description of class battleship here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class battleship
{
    public static void main(String[]args){
        int [][] board = new int[12][12];
       
        for (int x=0; x<12;x++){
            for (int y=0; y<11;y++){
                if (y==0){
                System.out.print(" +");
                }
                System.out.print(" +");
            }
            System.out.println(); 
        }
    }
}
