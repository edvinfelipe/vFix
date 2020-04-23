/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix;




/**
 *
 * @author felipe
 */
public class Header{

    static private  String name, value;

    public Header() {}
    
    
   public static void setValue( String value){
       name = "Authorization";
       Header.value = "Token " + value;
       
   }
   
   public static  String getValue(){ return value; }
   public static String getName(){ return name; }
    
   
}
