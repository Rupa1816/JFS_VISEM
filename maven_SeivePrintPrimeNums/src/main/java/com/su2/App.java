package com.su2;

/**
 * Hello world!
 *
 */
import java.util.*;
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the starting range:");
        int s=sc.nextInt();
        System.out.print("Enter the ending range:");
        int e=sc.nextInt();
        SE_Prime(s,e);
    }
    public static void SE_Prime(int s, int e){
        boolean[] prime = new boolean[e+1];
        Arrays.fill(prime,true);
        for(int i=2;i<=Math.sqrt(e);i++){
            if(prime[i]){
                for(int j=i*i;j<=e;j+=i){
                    prime[j]=false;
                }
            }
        }
        for(int i=s;i<=e;i++){
            if(prime[i]){
                System.out.print(i+" ");
            }
        }
    }
}
