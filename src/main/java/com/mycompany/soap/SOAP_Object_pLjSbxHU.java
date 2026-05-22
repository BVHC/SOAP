
package com.mycompany.soap;

import ptit.dblab.judge.network.server.soap.ObjectService;
import ptit.dblab.judge.network.server.soap.ProductY;
import ptit.dblab.judge.network.server.soap.SoapObjectService;

public class SOAP_Object_pLjSbxHU {
    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String qCode = "pLjSbxHU";
        
        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();
        
        ProductY p = port.requestProductY(studentCode, qCode);
        
        Float price = p.getPrice();
        Float taxRate = p.getTaxRate();
        Float discount = p.getDiscount();
        Float finalPrice = price * (100 + taxRate)/100 * (100 - discount)/100;
        
        p.setFinalPrice(Math.round(finalPrice * 100.0f) / 100.0f);
        
        
            
        System.out.println("p: = " + p.getFinalPrice());
        
        port.submitProductY(studentCode, qCode, p);
        
    }
}
