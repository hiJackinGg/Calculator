package com.example.AndroidHello;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * Created by Denis on 20.04.14.
 */

public class main {

    public static String parseStringExpression(String exp, final int fromRadix, final int toRadix) throws NumberFormatException, IllegalArgumentException{
        String buffer[];

        buffer = exp.split("[+-/*]");

        for(int i = 0; i<buffer.length; i++){

            if(!buffer[i].isEmpty()) {
                System.out.println(buffer[i]);
                exp = exp.replace(buffer[i], notationsConversion(buffer[i], fromRadix, toRadix));

            }

        } 
        return exp;

    }

       public static String notationsConversion(final String str, final int fromRadix, final int toRadix) throws NumberFormatException, IllegalArgumentException{

           Integer decValue;

           switch (fromRadix){
               case 2:
               case 8:
               case 10:
               case 16:
                   decValue = Integer.valueOf(str, fromRadix);
                   break;
               default:
                   throw new IllegalArgumentException("The radix to be converted from is incorrect: "+fromRadix);
           }

           switch (toRadix){
               case 2:
                   return Integer.toBinaryString(decValue);
               case 8:
                   return Integer.toOctalString(decValue);
               case 16:
                   return Integer.toHexString(decValue);
               case 10:
                   return decValue.toString();
               default:
                   throw new IllegalArgumentException("The radix to be converted to int incorrect: "+toRadix);
           }


       }

    public static void main(String []args){
        Integer dig;
        dig = 3;
        System.out.println("bin :"+Integer.toBinaryString(55)+"\n"+"oct :"+Integer.toOctalString(55)+"\n"+"hex :"+Integer.toHexString(55));
        System.out.println(Integer.bitCount(55));
        System.out.println(Integer.highestOneBit(55)+" : "+Integer.lowestOneBit(55));
        System.out.println(Integer.toBinaryString(55)+Integer.toBinaryString(55));

        System.out.println(Integer.toString(056,10));

        System.out.println(Integer.valueOf("1010",2));
        System.out.println(Integer.toHexString(58));

        System.out.println(notationsConversion("23", 10, 8 ));
      /*  String b[] = "23+34-55-23*123/2".split("[+-/*]");
for(String s:b)
    System.out.println(notationsConversion(s, 16, 10 ));*/
       System.out.println(parseStringExpression("FF", 16, 10));

    }
}
