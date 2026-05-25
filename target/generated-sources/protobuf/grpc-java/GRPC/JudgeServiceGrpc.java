package GRPC;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Backward-compatible string-based judging service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: judge.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class JudgeServiceGrpc {

  private JudgeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "GRPC.JudgeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GRPC.JudgeRequest,
      GRPC.JudgeResponse> getRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Request",
      requestType = GRPC.JudgeRequest.class,
      responseType = GRPC.JudgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.JudgeRequest,
      GRPC.JudgeResponse> getRequestMethod() {
    io.grpc.MethodDescriptor<GRPC.JudgeRequest, GRPC.JudgeResponse> getRequestMethod;
    if ((getRequestMethod = JudgeServiceGrpc.getRequestMethod) == null) {
      synchronized (JudgeServiceGrpc.class) {
        if ((getRequestMethod = JudgeServiceGrpc.getRequestMethod) == null) {
          JudgeServiceGrpc.getRequestMethod = getRequestMethod =
              io.grpc.MethodDescriptor.<GRPC.JudgeRequest, GRPC.JudgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Request"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.JudgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.JudgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new JudgeServiceMethodDescriptorSupplier("Request"))
              .build();
        }
      }
    }
    return getRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GRPC.SubmitRequest,
      GRPC.SubmitResponse> getSubmitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Submit",
      requestType = GRPC.SubmitRequest.class,
      responseType = GRPC.SubmitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.SubmitRequest,
      GRPC.SubmitResponse> getSubmitMethod() {
    io.grpc.MethodDescriptor<GRPC.SubmitRequest, GRPC.SubmitResponse> getSubmitMethod;
    if ((getSubmitMethod = JudgeServiceGrpc.getSubmitMethod) == null) {
      synchronized (JudgeServiceGrpc.class) {
        if ((getSubmitMethod = JudgeServiceGrpc.getSubmitMethod) == null) {
          JudgeServiceGrpc.getSubmitMethod = getSubmitMethod =
              io.grpc.MethodDescriptor.<GRPC.SubmitRequest, GRPC.SubmitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Submit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.SubmitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.SubmitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new JudgeServiceMethodDescriptorSupplier("Submit"))
              .build();
        }
      }
    }
    return getSubmitMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static JudgeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JudgeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JudgeServiceStub>() {
        @java.lang.Override
        public JudgeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JudgeServiceStub(channel, callOptions);
        }
      };
    return JudgeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static JudgeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JudgeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JudgeServiceBlockingStub>() {
        @java.lang.Override
        public JudgeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JudgeServiceBlockingStub(channel, callOptions);
        }
      };
    return JudgeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static JudgeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JudgeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JudgeServiceFutureStub>() {
        @java.lang.Override
        public JudgeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JudgeServiceFutureStub(channel, callOptions);
        }
      };
    return JudgeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Backward-compatible string-based judging service.
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void request(GRPC.JudgeRequest request,
        io.grpc.stub.StreamObserver<GRPC.JudgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestMethod(), responseObserver);
    }

    /**
     */
    default void submit(GRPC.SubmitRequest request,
        io.grpc.stub.StreamObserver<GRPC.SubmitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service JudgeService.
   * <pre>
   * Backward-compatible string-based judging service.
   * </pre>
   */
  public static abstract class JudgeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return JudgeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service JudgeService.
   * <pre>
   * Backward-compatible string-based judging service.
   * </pre>
   */
  public static final class JudgeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<JudgeServiceStub> {
    private JudgeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JudgeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JudgeServiceStub(channel, callOptions);
    }

    /**
     */
    public void request(GRPC.JudgeRequest request,
        io.grpc.stub.StreamObserver<GRPC.JudgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submit(GRPC.SubmitRequest request,
        io.grpc.stub.StreamObserver<GRPC.SubmitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service JudgeService.
   * <pre>
   * Backward-compatible string-based judging service.
   * </pre>
   */
  public static final class JudgeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<JudgeServiceBlockingStub> {
    private JudgeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JudgeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JudgeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public GRPC.JudgeResponse request(GRPC.JudgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public GRPC.SubmitResponse submit(GRPC.SubmitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service JudgeService.
   * <pre>
   * Backward-compatible string-based judging service.
   * </pre>
   */
  public static final class JudgeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<JudgeServiceFutureStub> {
    private JudgeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JudgeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JudgeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.JudgeResponse> request(
        GRPC.JudgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.SubmitResponse> submit(
        GRPC.SubmitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST = 0;
  private static final int METHODID_SUBMIT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST:
          serviceImpl.request((GRPC.JudgeRequest) request,
              (io.grpc.stub.StreamObserver<GRPC.JudgeResponse>) responseObserver);
          break;
        case METHODID_SUBMIT:
          serviceImpl.submit((GRPC.SubmitRequest) request,
              (io.grpc.stub.StreamObserver<GRPC.SubmitResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRequestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GRPC.JudgeRequest,
              GRPC.JudgeResponse>(
                service, METHODID_REQUEST)))
        .addMethod(
          getSubmitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GRPC.SubmitRequest,
              GRPC.SubmitResponse>(
                service, METHODID_SUBMIT)))
        .build();
  }

  private static abstract class JudgeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    JudgeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GRPC.Judge.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("JudgeService");
    }
  }

  private static final class JudgeServiceFileDescriptorSupplier
      extends JudgeServiceBaseDescriptorSupplier {
    JudgeServiceFileDescriptorSupplier() {}
  }

  private static final class JudgeServiceMethodDescriptorSupplier
      extends JudgeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    JudgeServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (JudgeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new JudgeServiceFileDescriptorSupplier())
              .addMethod(getRequestMethod())
              .addMethod(getSubmitMethod())
              .build();
        }
      }
    }
    return result;
  }
}
