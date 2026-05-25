
package com.mycompany.soap;

import java.util.List;
import ptit.dblab.judge.network.server.soap.DataService;
import ptit.dblab.judge.network.server.soap.SoapDataService;


public class SOAP1 {
    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String alias = "MkEUWFPQ";
        
        DataService sv = new DataService();
        SoapDataService port = sv.getSoapDataServicePort();
        
        List<Integer> arr = port.getData(studentCode, alias);
        
        
        int ans = 0;
        for(int x : arr){
            System.out.print(x + " ");
            ans += x;
        }
        System.out.println("");
        System.out.println(ans);
                
        port.submitDataInt(studentCode, alias, ans);
         
    }
}
