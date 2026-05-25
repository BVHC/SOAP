package com.mycompany.soap;

import GRPC.JudgeRequest;
import GRPC.JudgeResponse;
import GRPC.JudgeServiceGrpc;
import GRPC.SubmitRequest;
import GRPC.SubmitResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class gRPC_Data1 {

    private static String HOST = "36.50.135.242";
    private static int port = 2240;

    private static String scode = "B22DCCN292";
    private static String qcode = "4DCGleoI";

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, port).usePlaintext().build();

        try {
            JudgeServiceGrpc.JudgeServiceBlockingStub stub = JudgeServiceGrpc.newBlockingStub(channel);
            JudgeRequest request = JudgeRequest.newBuilder().setStudentCode(scode).setQuestionAlias(qcode).build();

            JudgeResponse response = stub.request(request);

            String requestId = response.getRequestId();
            String data = response.getData();

            System.out.println("rId: " + requestId + " DATA: " + data);
            
            String[] tmp = data.split(",");
            int sum = 0;
            
            for(String x : tmp) {
                sum += Integer.parseInt(x);
            }

            String ans = "" + sum;
            System.out.println("ans : " + ans);
            SubmitRequest submitRequest = SubmitRequest.newBuilder().setStudentCode(scode).setQuestionAlias(qcode).setRequestId(requestId).setAnswer(ans).build();
            SubmitResponse submitResponse = stub.submit(submitRequest);
            
            
            System.out.println("status = " + submitResponse.getStatus());
            System.out.println("message = " + submitResponse.getMessage());
        } finally {
            channel.shutdown();
        }
    }
}
