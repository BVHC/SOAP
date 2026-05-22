package com.mycompany.soap;

import GRPC.JudgeRequest;
import GRPC.JudgeResponse;
import GRPC.SubmitRequest;
import GRPC.SubmitResponse;
import GRPC.JudgeServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class gRPC {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2240;

    private static final String STUDENT_CODE = "B22DCCN292";
    private static final String QUESTION_ALIAS = "HZRtgMol";

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        try {
            JudgeServiceGrpc.JudgeServiceBlockingStub stub =
                    JudgeServiceGrpc.newBlockingStub(channel);

            JudgeRequest request = JudgeRequest.newBuilder()
                    .setStudentCode(STUDENT_CODE)
                    .setQuestionAlias(QUESTION_ALIAS)
                    .build();

            JudgeResponse response = stub.request(request);

            String requestId = response.getRequestId();
            String data = response.getData();

            System.out.println("request_id = " + requestId);
            System.out.println("data = " + data);

            String answer = Arrays.stream(data.split(","))
                    .map(String::trim)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.joining(","));

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