// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

/**
 * Protobuf type {@code com.muedsa.bilibililiveapiclient.model.DmSegConfig}
 */
public final class DmSegConfig extends
        com.google.protobuf.GeneratedMessageLite<
                DmSegConfig, DmSegConfig.Builder> implements
        // @@protoc_insertion_point(message_implements:com.muedsa.bilibililiveapiclient.model.DmSegConfig)
        DmSegConfigOrBuilder {
    private DmSegConfig() {
    }

    private int bitField0_;
    public static final int PAGESIZE_FIELD_NUMBER = 1;
    private long pageSize_;

    /**
     * <code>optional int64 pageSize = 1;</code>
     *
     * @return Whether the pageSize field is set.
     */
    @Override
    public boolean hasPageSize() {
        return ((bitField0_ & 0x00000001) != 0);
    }

    /**
     * <code>optional int64 pageSize = 1;</code>
     *
     * @return The pageSize.
     */
    @Override
    public long getPageSize() {
        return pageSize_;
    }

    /**
     * <code>optional int64 pageSize = 1;</code>
     *
     * @param value The pageSize to set.
     */
    private void setPageSize(long value) {
        bitField0_ |= 0x00000001;
        pageSize_ = value;
    }

    /**
     * <code>optional int64 pageSize = 1;</code>
     */
    private void clearPageSize() {
        bitField0_ = (bitField0_ & ~0x00000001);
        pageSize_ = 0L;
    }

    public static final int TOTAL_FIELD_NUMBER = 2;
    private long total_;

    /**
     * <code>optional int64 total = 2;</code>
     *
     * @return Whether the total field is set.
     */
    @Override
    public boolean hasTotal() {
        return ((bitField0_ & 0x00000002) != 0);
    }

    /**
     * <code>optional int64 total = 2;</code>
     *
     * @return The total.
     */
    @Override
    public long getTotal() {
        return total_;
    }

    /**
     * <code>optional int64 total = 2;</code>
     *
     * @param value The total to set.
     */
    private void setTotal(long value) {
        bitField0_ |= 0x00000002;
        total_ = value;
    }

    /**
     * <code>optional int64 total = 2;</code>
     */
    private void clearTotal() {
        bitField0_ = (bitField0_ & ~0x00000002);
        total_ = 0L;
    }

    public static DmSegConfig parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static DmSegConfig parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DmSegConfig parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static DmSegConfig parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DmSegConfig parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static DmSegConfig parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DmSegConfig parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input);
    }

    public static DmSegConfig parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DmSegConfig parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DmSegConfig parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DmSegConfig parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input);
    }

    public static DmSegConfig parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DmSegConfig prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code com.muedsa.bilibililiveapiclient.model.DmSegConfig}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageLite.Builder<
                    DmSegConfig, Builder> implements
            // @@protoc_insertion_point(builder_implements:com.muedsa.bilibililiveapiclient.model.DmSegConfig)
            DmSegConfigOrBuilder {
        // Construct using com.muedsa.bilibililiveapiclient.model.danmaku.DmSegConfig.newBuilder()
        private Builder() {
            super(DEFAULT_INSTANCE);
        }


        /**
         * <code>optional int64 pageSize = 1;</code>
         *
         * @return Whether the pageSize field is set.
         */
        @Override
        public boolean hasPageSize() {
            return instance.hasPageSize();
        }

        /**
         * <code>optional int64 pageSize = 1;</code>
         *
         * @return The pageSize.
         */
        @Override
        public long getPageSize() {
            return instance.getPageSize();
        }

        /**
         * <code>optional int64 pageSize = 1;</code>
         *
         * @param value The pageSize to set.
         * @return This builder for chaining.
         */
        public Builder setPageSize(long value) {
            copyOnWrite();
            instance.setPageSize(value);
            return this;
        }

        /**
         * <code>optional int64 pageSize = 1;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearPageSize() {
            copyOnWrite();
            instance.clearPageSize();
            return this;
        }

        /**
         * <code>optional int64 total = 2;</code>
         *
         * @return Whether the total field is set.
         */
        @Override
        public boolean hasTotal() {
            return instance.hasTotal();
        }

        /**
         * <code>optional int64 total = 2;</code>
         *
         * @return The total.
         */
        @Override
        public long getTotal() {
            return instance.getTotal();
        }

        /**
         * <code>optional int64 total = 2;</code>
         *
         * @param value The total to set.
         * @return This builder for chaining.
         */
        public Builder setTotal(long value) {
            copyOnWrite();
            instance.setTotal(value);
            return this;
        }

        /**
         * <code>optional int64 total = 2;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearTotal() {
            copyOnWrite();
            instance.clearTotal();
            return this;
        }

        // @@protoc_insertion_point(builder_scope:com.muedsa.bilibililiveapiclient.model.DmSegConfig)
    }

    @Override
    @SuppressWarnings({"unchecked", "fallthrough"})
    protected Object dynamicMethod(
            MethodToInvoke method,
            Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE: {
                return new DmSegConfig();
            }
            case NEW_BUILDER: {
                return new Builder();
            }
            case BUILD_MESSAGE_INFO: {
                Object[] objects = new Object[]{
                        "bitField0_",
                        "pageSize_",
                        "total_",
                };
                String info =
                        "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u1002\u0000\u0002" +
                                "\u1002\u0001";
                return newMessageInfo(DEFAULT_INSTANCE, info, objects);
            }
            // fall through
            case GET_DEFAULT_INSTANCE: {
                return DEFAULT_INSTANCE;
            }
            case GET_PARSER: {
                com.google.protobuf.Parser<DmSegConfig> parser = PARSER;
                if (parser == null) {
                    synchronized (DmSegConfig.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser =
                                    new DefaultInstanceBasedParser<DmSegConfig>(
                                            DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            }
            case GET_MEMOIZED_IS_INITIALIZED: {
                return (byte) 1;
            }
            case SET_MEMOIZED_IS_INITIALIZED: {
                return null;
            }
        }
        throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:com.muedsa.bilibililiveapiclient.model.DmSegConfig)
    private static final DmSegConfig DEFAULT_INSTANCE;

    static {
        DmSegConfig defaultInstance = new DmSegConfig();
        // New instances are implicitly immutable so no need to make
        // immutable.
        DEFAULT_INSTANCE = defaultInstance;
        com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
                DmSegConfig.class, defaultInstance);
    }

    public static DmSegConfig getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<DmSegConfig> PARSER;

    public static com.google.protobuf.Parser<DmSegConfig> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}

