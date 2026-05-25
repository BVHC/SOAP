
package com.mycompany.soap;

import ptit.dblab.judge.network.server.soap.CharacterService;
import ptit.dblab.judge.network.server.soap.SoapCharacterService;



public class SOAP_String_pCXTGqF4 {
    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String qCode = "pCXTGqF4";
        
        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();
        
        String s = port.requestString(studentCode, qCode);
        System.out.println("DATA: " + s);
        
        StringBuilder sb = new StringBuilder(s);
        
        String reversedString = sb.reverse().toString();
        System.out.println(reversedString);
        port.submitString(studentCode, qCode, reversedString);
        
    }
}
