package com.su2;

/**
 * Hello world!
 *
 */
import java.util.Scanner;
public class App
{
    public static void main( String[] args )
    {
        Scanner sc=new Scanner(System.in);
        System.out.print( "Enter a number to check prime or not:" );
        int n=sc.nextInt();
        if(checkPrime(n)){
            System.out.println("Prime");
        }
        else{
            System.out.println("Not Prime");
        }
    }
    public static boolean checkPrime(int n){
        for(int i=2;i<Math.sqrt(n);i++){
            if(n%i==0){
                return false;
        }
    }
        return true;
}
}
