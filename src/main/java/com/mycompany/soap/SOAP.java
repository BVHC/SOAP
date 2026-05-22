package com.mycompany.soap;

import java.util.List;
import ptit.dblab.judge.network.server.soap.DataService;
import ptit.dblab.judge.network.server.soap.SoapDataService;

public class SOAP {

    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String qCode = "MkEUWFPQ";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> data = port.getData(studentCode, qCode);

        int sum = 0;
        for (Integer x : data) {
            sum += x;
        }

        port.submitDataInt(studentCode, qCode, sum);

        System.out.println("DATA: " + data);
        System.out.println("SUM: " + sum);
        System.out.println("SUBMITTED");
    }
}