/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soap;

import ptit.dblab.judge.network.server.soap.CharacterService;
import ptit.dblab.judge.network.server.soap.SoapCharacterService;

/**
 *
 * @author Window 11
 */
public class Soap2 {
    public static void main(String[] args) {
        String code = "B22DCCN292";
        String alias = "pCXTGqF4";
        
        CharacterService sv = new CharacterService();
        SoapCharacterService port = sv.getSoapCharacterServicePort();
        
        String s = port.requestString(code, alias);
        System.out.println(s);
        
        StringBuilder sb = new StringBuilder(s);
        
        
        port.submitString(code, alias, sb.reverse().toString());
    }
}
