/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author carlo
 */
public class Encriptacion {

    public static String getEncriptacion(String contrasena){
        
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(contrasena.getBytes());
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            
            return hashtext; 
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex); 
        }
    }
    
    
}
