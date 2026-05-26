package com.mycompany.soap;

import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeResponse;
import GRPC.TypedSubmitRequest;
import GRPC.TypedSubmitResponse;
import GRPC.TransactionRecord;
import GRPC.TransactionRiskAnswer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

public class gRPC_TypedClient_Transaction {
    public static void main(String[] args) {
        String studentCode = "B22DCCN292";
        String questionAlias = "rqfemEcq";

        // 1. Khởi tạo Channel kết nối tới gRPC Server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242", 2240)
                .usePlaintext()
                .build();

        // Khởi tạo Stub
        TypedJudgeServiceGrpc.TypedJudgeServiceBlockingStub stub = TypedJudgeServiceGrpc.newBlockingStub(channel);

        // 2. Gửi RequestTyped
        TypedJudgeRequest request = TypedJudgeRequest.newBuilder()
                .setStudentCode(studentCode)
                .setQuestionAlias(questionAlias)
                .build();
                
        System.out.println("Đang gửi yêu cầu RequestTyped...");
        TypedJudgeResponse response = stub.requestTyped(request);
        
        System.out.println("Đã nhận phản hồi từ Server, Request ID: " + response.getRequestId());
        
        // Kiểm tra xem server có trả về transaction_risk_batch không
        if (!response.hasTransactionRiskBatch()) {
            System.out.println("Lỗi: Không nhận được transaction_risk_batch!");
            channel.shutdown();
            return;
        }

        List<TransactionRecord> transactions = response.getTransactionRiskBatch().getTransactionsList();
        System.out.println("Đã nhận " + transactions.size() + " giao dịch để kiểm tra.");

        List<String> highRiskIds = new ArrayList<>();
        double totalAmount = 0.0;
        
        // 3. Lọc giao dịch rủi ro
        for (TransactionRecord tx : transactions) {
            // Điều kiện review:
            // - amount >= 5000, HOẶC
            // - chargeback_count >= 2, HOẶC
            // - new_device=true VÀ country khác "VN"
            if (tx.getAmount() >= 5000 
                || tx.getChargebackCount() >= 2 
                || (tx.getNewDevice() && !tx.getCountry().equals("VN"))) {
                
                highRiskIds.add(tx.getTransactionId());
                totalAmount += tx.getAmount();
            }
        }
        
        // 4. Tính toán kết quả
        int reviewCount = highRiskIds.size();
        
        // CỰC KỲ QUAN TRỌNG: Làm tròn 2 chữ số thập phân trong Java
        double roundedTotalAmount = Math.round(totalAmount * 100.0) / 100.0;
        
        System.out.println("Số lượng giao dịch cần review (review_count): " + reviewCount);
        System.out.println("Tổng tiền rủi ro (total_high_risk_amount): " + roundedTotalAmount);
        
        // 5. Đóng gói vào đối tượng TransactionRiskAnswer
        TransactionRiskAnswer answer = TransactionRiskAnswer.newBuilder()
                .addAllHighRiskTransactionIds(highRiskIds)
                .setReviewCount(reviewCount)
                .setTotalHighRiskAmount(roundedTotalAmount)
                .build();
                
        // 6. Đóng gói vào SubmitRequest
        TypedSubmitRequest submitRequest = TypedSubmitRequest.newBuilder()
                .setStudentCode(studentCode)
                .setQuestionAlias(questionAlias)
                .setRequestId(response.getRequestId())
                .setTransactionRiskAnswer(answer)
                .build();
                
        System.out.println("\nĐang gửi kết quả SubmitTyped...");
        TypedSubmitResponse submitResponse = stub.submitTyped(submitRequest);
        System.out.println("Kết quả từ Server:");
        System.out.println("Status: " + submitResponse.getStatus());
        System.out.println("Message: " + submitResponse.getMessage());

        // Đóng channel
        channel.shutdown();
    }
}
