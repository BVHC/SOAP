package com.mycompany.soap;

import GRPC.JudgeRequest;
import GRPC.JudgeResponse;
import GRPC.JudgeServiceGrpc;
import GRPC.SubmitRequest;
import GRPC.SubmitResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Arrays;

public class gRPC_Data_4DCGleoI {

    private static String HOST = "36.50.135.242";
    private static int PORT = 2240;

    private static String STUDENT_CODE = "B22DCCN292";
    private static String QUESTION_ALIAS = "4DCGleoI";

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(HOST, PORT)
                .usePlaintext()
                .build();
        try {
            
            JudgeServiceGrpc.JudgeServiceBlockingStub stub
                    = JudgeServiceGrpc.newBlockingStub(channel);

            JudgeRequest request = JudgeRequest.newBuilder()
                    .setStudentCode(STUDENT_CODE)
                    .setQuestionAlias(QUESTION_ALIAS)
                    .build();

            JudgeResponse response = stub.request(request);

            String requestId = response.getRequestId();
            String data = response.getData();

            System.out.println("request_id = " + requestId);
            System.out.println("data = " + data);

//            long sum = Arrays.stream(data.split(","))
//                    .map(String::trim)
//                    .filter(part -> !part.isEmpty())
//                    .mapToLong(Long::parseLong)
//                    .sum();
            
            long sum = 0;
            String[] s = data.split(",");
            for(String x : s){
                sum += Long.parseLong(x);
            }

            String answer = "" + sum;

            System.out.println("answer = " + answer);

            SubmitRequest submitRequest = SubmitRequest.newBuilder()
                    .setStudentCode(STUDENT_CODE)
                    .setQuestionAlias(QUESTION_ALIAS)
                    .setRequestId(requestId)
                    .setAnswer(answer)
                    .build();

            SubmitResponse submitResponse = stub.submit(submitRequest);

            System.out.println("status = " + submitResponse.getStatus());
            System.out.println("message = " + submitResponse.getMessage());

        } finally {
            channel.shutdown();
        }
    }
}
