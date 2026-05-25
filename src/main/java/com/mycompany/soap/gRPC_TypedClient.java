package com.mycompany.soap;

import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeResponse;
import GRPC.TypedSubmitRequest;
import GRPC.TypedSubmitResponse;
import GRPC.TextBatchAnswer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gRPC_TypedClient {
    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String questionAlias = "xw74a56O";

        // 1. Khởi tạo Channel kết nối tới gRPC Server (plaintext, không TLS)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242", 2240)
                .usePlaintext()
                .build();

        // Khởi tạo Stub (Blocking)
        TypedJudgeServiceGrpc.TypedJudgeServiceBlockingStub stub = TypedJudgeServiceGrpc.newBlockingStub(channel);

        // 2. Gửi RequestTyped
        TypedJudgeRequest request = TypedJudgeRequest.newBuilder()
                .setStudentCode(studentCode)
                .setQuestionAlias(questionAlias)
                .build();
                
        System.out.println("Đang gửi yêu cầu RequestTyped...");
        TypedJudgeResponse response = stub.requestTyped(request);
        
        System.out.println("Đã nhận phản hồi từ Server, Request ID: " + response.getRequestId());
        
        // Kiểm tra xem server có trả về text_batch không
        if (!response.hasTextBatch()) {
            System.out.println("Lỗi: Không nhận được text_batch!");
            channel.shutdown();
            return;
        }

        System.out.println("Mode nhận được: " + response.getTextBatch().getMode());

        // 3. Phân tích văn bản
        List<String> entries = response.getTextBatch().getEntriesList();
        String[] targets = {"account", "payment", "refund", "shipping"};
        Map<String, Integer> counts = new HashMap<>();
        
        // Khởi tạo số đếm bằng 0
        for (String t : targets) {
            counts.put(t, 0);
        }

        // Đếm số lần xuất hiện không phân biệt hoa thường
        for (String entry : entries) {
            String lowerEntry = entry.toLowerCase();
            for (String t : targets) {
                if (lowerEntry.contains(t)) {
                    counts.put(t, counts.get(t) + 1);
                }
            }
        }
        
        // 4. Lọc bỏ các nhãn có số lượng = 0 và sắp xếp
        List<String> values = new ArrayList<>();
        Map<String, Integer> finalCounts = new HashMap<>();
        
        // Mảng targets đã định nghĩa là "account", "payment", "refund", "shipping"
        // đã theo chuẩn thứ tự ABC nên ta chỉ việc thêm vào. Để an toàn, gọi Arrays.sort
        Arrays.sort(targets);
        
        for (String t : targets) {
            if (counts.get(t) > 0) {
                values.add(t);
                finalCounts.put(t, counts.get(t));
            }
        }
        
        System.out.println("Values: " + values);
        System.out.println("Counts: " + finalCounts);
        
        // Đóng gói vào đối tượng TextBatchAnswer
        TextBatchAnswer answer = TextBatchAnswer.newBuilder()
                .addAllValues(values)
                .putAllCounts(finalCounts)
                .build();
                
        // 5. Đóng gói vào SubmitRequest
        TypedSubmitRequest submitRequest = TypedSubmitRequest.newBuilder()
                .setStudentCode(studentCode)
                .setQuestionAlias(questionAlias)
                .setRequestId(response.getRequestId())
                .setTextBatchAnswer(answer)
                .build();
                
        System.out.println("\nĐang gửi kết quả SubmitTyped...");
        TypedSubmitResponse submitResponse = stub.submitTyped(submitRequest);
        System.out.println("Kết quả từ Server:");
        System.out.println("Status: " + submitResponse.getStatus());
        System.out.println("Message: " + submitResponse.getMessage());

        // Đóng channel sau khi hoàn thành
        channel.shutdown();
    }
}
