// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

/**
 * Protobuf type {@code com.muedsa.bilibililiveapiclient.model.CommandDm}
 */
public final class CommandDm extends
        com.google.protobuf.GeneratedMessageLite<
                CommandDm, CommandDm.Builder> implements
        // @@protoc_insertion_point(message_implements:com.muedsa.bilibililiveapiclient.model.CommandDm)
        CommandDmOrBuilder {
    private CommandDm() {
        content_ = "";
        ctime_ = "";
        mtime_ = "";
        extra_ = "";
        idStr_ = "";
    }

    private int bitField0_;
    public static final int ID_FIELD_NUMBER = 1;
    private long id_;

    /**
     * <code>optional int64 id = 1;</code>
     *
     * @return Whether the id field is set.
     */
    @Override
    public boolean hasId() {
        return ((bitField0_ & 0x00000001) != 0);
    }

    /**
     * <code>optional int64 id = 1;</code>
     *
     * @return The id.
     */
    @Override
    public long getId() {
        return id_;
    }

    /**
     * <code>optional int64 id = 1;</code>
     *
     * @param value The id to set.
     */
    private void setId(long value) {
        bitField0_ |= 0x00000001;
        id_ = value;
    }

    /**
     * <code>optional int64 id = 1;</code>
     */
    private void clearId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        id_ = 0L;
    }

    public static final int OID_FIELD_NUMBER = 2;
    private long oid_;

    /**
     * <code>optional int64 oid = 2;</code>
     *
     * @return Whether the oid field is set.
     */
    @Override
    public boolean hasOid() {
        return ((bitField0_ & 0x00000002) != 0);
    }

    /**
     * <code>optional int64 oid = 2;</code>
     *
     * @return The oid.
     */
    @Override
    public long getOid() {
        return oid_;
    }

    /**
     * <code>optional int64 oid = 2;</code>
     *
     * @param value The oid to set.
     */
    private void setOid(long value) {
        bitField0_ |= 0x00000002;
        oid_ = value;
    }

    /**
     * <code>optional int64 oid = 2;</code>
     */
    private void clearOid() {
        bitField0_ = (bitField0_ & ~0x00000002);
        oid_ = 0L;
    }

    public static final int MID_FIELD_NUMBER = 3;
    private long mid_;

    /**
     * <code>optional int64 mid = 3;</code>
     *
     * @return Whether the mid field is set.
     */
    @Override
    public boolean hasMid() {
        return ((bitField0_ & 0x00000004) != 0);
    }

    /**
     * <code>optional int64 mid = 3;</code>
     *
     * @return The mid.
     */
    @Override
    public long getMid() {
        return mid_;
    }

    /**
     * <code>optional int64 mid = 3;</code>
     *
     * @param value The mid to set.
     */
    private void setMid(long value) {
        bitField0_ |= 0x00000004;
        mid_ = value;
    }

    /**
     * <code>optional int64 mid = 3;</code>
     */
    private void clearMid() {
        bitField0_ = (bitField0_ & ~0x00000004);
        mid_ = 0L;
    }

    public static final int COMMAND_FIELD_NUMBER = 4;
    private long command_;

    /**
     * <code>optional int64 command = 4;</code>
     *
     * @return Whether the command field is set.
     */
    @Override
    public boolean hasCommand() {
        return ((bitField0_ & 0x00000008) != 0);
    }

    /**
     * <code>optional int64 command = 4;</code>
     *
     * @return The command.
     */
    @Override
    public long getCommand() {
        return command_;
    }

    /**
     * <code>optional int64 command = 4;</code>
     *
     * @param value The command to set.
     */
    private void setCommand(long value) {
        bitField0_ |= 0x00000008;
        command_ = value;
    }

    /**
     * <code>optional int64 command = 4;</code>
     */
    private void clearCommand() {
        bitField0_ = (bitField0_ & ~0x00000008);
        command_ = 0L;
    }

    public static final int CONTENT_FIELD_NUMBER = 5;
    private String content_;

    /**
     * <code>optional string content = 5;</code>
     *
     * @return Whether the content field is set.
     */
    @Override
    public boolean hasContent() {
        return ((bitField0_ & 0x00000010) != 0);
    }

    /**
     * <code>optional string content = 5;</code>
     *
     * @return The content.
     */
    @Override
    public String getContent() {
        return content_;
    }

    /**
     * <code>optional string content = 5;</code>
     *
     * @return The bytes for content.
     */
    @Override
    public com.google.protobuf.ByteString
    getContentBytes() {
        return com.google.protobuf.ByteString.copyFromUtf8(content_);
    }

    /**
     * <code>optional string content = 5;</code>
     *
     * @param value The content to set.
     */
    private void setContent(
            String value) {
        Class<?> valueClass = value.getClass();
        bitField0_ |= 0x00000010;
        content_ = value;
    }

    /**
     * <code>optional string content = 5;</code>
     */
    private void clearContent() {
        bitField0_ = (bitField0_ & ~0x00000010);
        content_ = getDefaultInstance().getContent();
    }

    /**
     * <code>optional string content = 5;</code>
     *
     * @param value The bytes for content to set.
     */
    private void setContentBytes(
            com.google.protobuf.ByteString value) {
        checkByteStringIsUtf8(value);
        content_ = value.toStringUtf8();
        bitField0_ |= 0x00000010;
    }

    public static final int PROGRESS_FIELD_NUMBER = 6;
    private int progress_;

    /**
     * <code>optional int32 progress = 6;</code>
     *
     * @return Whether the progress field is set.
     */
    @Override
    public boolean hasProgress() {
        return ((bitField0_ & 0x00000020) != 0);
    }

    /**
     * <code>optional int32 progress = 6;</code>
     *
     * @return The progress.
     */
    @Override
    public int getProgress() {
        return progress_;
    }

    /**
     * <code>optional int32 progress = 6;</code>
     *
     * @param value The progress to set.
     */
    private void setProgress(int value) {
        bitField0_ |= 0x00000020;
        progress_ = value;
    }

    /**
     * <code>optional int32 progress = 6;</code>
     */
    private void clearProgress() {
        bitField0_ = (bitField0_ & ~0x00000020);
        progress_ = 0;
    }

    public static final int CTIME_FIELD_NUMBER = 7;
    private String ctime_;

    /**
     * <code>optional string ctime = 7;</code>
     *
     * @return Whether the ctime field is set.
     */
    @Override
    public boolean hasCtime() {
        return ((bitField0_ & 0x00000040) != 0);
    }

    /**
     * <code>optional string ctime = 7;</code>
     *
     * @return The ctime.
     */
    @Override
    public String getCtime() {
        return ctime_;
    }

    /**
     * <code>optional string ctime = 7;</code>
     *
     * @return The bytes for ctime.
     */
    @Override
    public com.google.protobuf.ByteString
    getCtimeBytes() {
        return com.google.protobuf.ByteString.copyFromUtf8(ctime_);
    }

    /**
     * <code>optional string ctime = 7;</code>
     *
     * @param value The ctime to set.
     */
    private void setCtime(
            String value) {
        Class<?> valueClass = value.getClass();
        bitField0_ |= 0x00000040;
        ctime_ = value;
    }

    /**
     * <code>optional string ctime = 7;</code>
     */
    private void clearCtime() {
        bitField0_ = (bitField0_ & ~0x00000040);
        ctime_ = getDefaultInstance().getCtime();
    }

    /**
     * <code>optional string ctime = 7;</code>
     *
     * @param value The bytes for ctime to set.
     */
    private void setCtimeBytes(
            com.google.protobuf.ByteString value) {
        checkByteStringIsUtf8(value);
        ctime_ = value.toStringUtf8();
        bitField0_ |= 0x00000040;
    }

    public static final int MTIME_FIELD_NUMBER = 8;
    private String mtime_;

    /**
     * <code>optional string mtime = 8;</code>
     *
     * @return Whether the mtime field is set.
     */
    @Override
    public boolean hasMtime() {
        return ((bitField0_ & 0x00000080) != 0);
    }

    /**
     * <code>optional string mtime = 8;</code>
     *
     * @return The mtime.
     */
    @Override
    public String getMtime() {
        return mtime_;
    }

    /**
     * <code>optional string mtime = 8;</code>
     *
     * @return The bytes for mtime.
     */
    @Override
    public com.google.protobuf.ByteString
    getMtimeBytes() {
        return com.google.protobuf.ByteString.copyFromUtf8(mtime_);
    }

    /**
     * <code>optional string mtime = 8;</code>
     *
     * @param value The mtime to set.
     */
    private void setMtime(
            String value) {
        Class<?> valueClass = value.getClass();
        bitField0_ |= 0x00000080;
        mtime_ = value;
    }

    /**
     * <code>optional string mtime = 8;</code>
     */
    private void clearMtime() {
        bitField0_ = (bitField0_ & ~0x00000080);
        mtime_ = getDefaultInstance().getMtime();
    }

    /**
     * <code>optional string mtime = 8;</code>
     *
     * @param value The bytes for mtime to set.
     */
    private void setMtimeBytes(
            com.google.protobuf.ByteString value) {
        checkByteStringIsUtf8(value);
        mtime_ = value.toStringUtf8();
        bitField0_ |= 0x00000080;
    }

    public static final int EXTRA_FIELD_NUMBER = 9;
    private String extra_;

    /**
     * <code>optional string extra = 9;</code>
     *
     * @return Whether the extra field is set.
     */
    @Override
    public boolean hasExtra() {
        return ((bitField0_ & 0x00000100) != 0);
    }

    /**
     * <code>optional string extra = 9;</code>
     *
     * @return The extra.
     */
    @Override
    public String getExtra() {
        return extra_;
    }

    /**
     * <code>optional string extra = 9;</code>
     *
     * @return The bytes for extra.
     */
    @Override
    public com.google.protobuf.ByteString
    getExtraBytes() {
        return com.google.protobuf.ByteString.copyFromUtf8(extra_);
    }

    /**
     * <code>optional string extra = 9;</code>
     *
     * @param value The extra to set.
     */
    private void setExtra(
            String value) {
        Class<?> valueClass = value.getClass();
        bitField0_ |= 0x00000100;
        extra_ = value;
    }

    /**
     * <code>optional string extra = 9;</code>
     */
    private void clearExtra() {
        bitField0_ = (bitField0_ & ~0x00000100);
        extra_ = getDefaultInstance().getExtra();
    }

    /**
     * <code>optional string extra = 9;</code>
     *
     * @param value The bytes for extra to set.
     */
    private void setExtraBytes(
            com.google.protobuf.ByteString value) {
        checkByteStringIsUtf8(value);
        extra_ = value.toStringUtf8();
        bitField0_ |= 0x00000100;
    }

    public static final int IDSTR_FIELD_NUMBER = 10;
    private String idStr_;

    /**
     * <code>optional string idStr = 10;</code>
     *
     * @return Whether the idStr field is set.
     */
    @Override
    public boolean hasIdStr() {
        return ((bitField0_ & 0x00000200) != 0);
    }

    /**
     * <code>optional string idStr = 10;</code>
     *
     * @return The idStr.
     */
    @Override
    public String getIdStr() {
        return idStr_;
    }

    /**
     * <code>optional string idStr = 10;</code>
     *
     * @return The bytes for idStr.
     */
    @Override
    public com.google.protobuf.ByteString
    getIdStrBytes() {
        return com.google.protobuf.ByteString.copyFromUtf8(idStr_);
    }

    /**
     * <code>optional string idStr = 10;</code>
     *
     * @param value The idStr to set.
     */
    private void setIdStr(
            String value) {
        Class<?> valueClass = value.getClass();
        bitField0_ |= 0x00000200;
        idStr_ = value;
    }

    /**
     * <code>optional string idStr = 10;</code>
     */
    private void clearIdStr() {
        bitField0_ = (bitField0_ & ~0x00000200);
        idStr_ = getDefaultInstance().getIdStr();
    }

    /**
     * <code>optional string idStr = 10;</code>
     *
     * @param value The bytes for idStr to set.
     */
    private void setIdStrBytes(
            com.google.protobuf.ByteString value) {
        checkByteStringIsUtf8(value);
        idStr_ = value.toStringUtf8();
        bitField0_ |= 0x00000200;
    }

    public static CommandDm parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static CommandDm parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommandDm parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static CommandDm parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommandDm parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data);
    }

    public static CommandDm parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommandDm parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input);
    }

    public static CommandDm parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CommandDm parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CommandDm parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CommandDm parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input);
    }

    public static CommandDm parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageLite.parseFrom(
                DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CommandDm prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code com.muedsa.bilibililiveapiclient.model.CommandDm}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageLite.Builder<
                    CommandDm, Builder> implements
            // @@protoc_insertion_point(builder_implements:com.muedsa.bilibililiveapiclient.model.CommandDm)
            CommandDmOrBuilder {
        // Construct using com.muedsa.bilibililiveapiclient.model.danmaku.CommandDm.newBuilder()
        private Builder() {
            super(DEFAULT_INSTANCE);
        }


        /**
         * <code>optional int64 id = 1;</code>
         *
         * @return Whether the id field is set.
         */
        @Override
        public boolean hasId() {
            return instance.hasId();
        }

        /**
         * <code>optional int64 id = 1;</code>
         *
         * @return The id.
         */
        @Override
        public long getId() {
            return instance.getId();
        }

        /**
         * <code>optional int64 id = 1;</code>
         *
         * @param value The id to set.
         * @return This builder for chaining.
         */
        public Builder setId(long value) {
            copyOnWrite();
            instance.setId(value);
            return this;
        }

        /**
         * <code>optional int64 id = 1;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearId() {
            copyOnWrite();
            instance.clearId();
            return this;
        }

        /**
         * <code>optional int64 oid = 2;</code>
         *
         * @return Whether the oid field is set.
         */
        @Override
        public boolean hasOid() {
            return instance.hasOid();
        }

        /**
         * <code>optional int64 oid = 2;</code>
         *
         * @return The oid.
         */
        @Override
        public long getOid() {
            return instance.getOid();
        }

        /**
         * <code>optional int64 oid = 2;</code>
         *
         * @param value The oid to set.
         * @return This builder for chaining.
         */
        public Builder setOid(long value) {
            copyOnWrite();
            instance.setOid(value);
            return this;
        }

        /**
         * <code>optional int64 oid = 2;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearOid() {
            copyOnWrite();
            instance.clearOid();
            return this;
        }

        /**
         * <code>optional int64 mid = 3;</code>
         *
         * @return Whether the mid field is set.
         */
        @Override
        public boolean hasMid() {
            return instance.hasMid();
        }

        /**
         * <code>optional int64 mid = 3;</code>
         *
         * @return The mid.
         */
        @Override
        public long getMid() {
            return instance.getMid();
        }

        /**
         * <code>optional int64 mid = 3;</code>
         *
         * @param value The mid to set.
         * @return This builder for chaining.
         */
        public Builder setMid(long value) {
            copyOnWrite();
            instance.setMid(value);
            return this;
        }

        /**
         * <code>optional int64 mid = 3;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearMid() {
            copyOnWrite();
            instance.clearMid();
            return this;
        }

        /**
         * <code>optional int64 command = 4;</code>
         *
         * @return Whether the command field is set.
         */
        @Override
        public boolean hasCommand() {
            return instance.hasCommand();
        }

        /**
         * <code>optional int64 command = 4;</code>
         *
         * @return The command.
         */
        @Override
        public long getCommand() {
            return instance.getCommand();
        }

        /**
         * <code>optional int64 command = 4;</code>
         *
         * @param value The command to set.
         * @return This builder for chaining.
         */
        public Builder setCommand(long value) {
            copyOnWrite();
            instance.setCommand(value);
            return this;
        }

        /**
         * <code>optional int64 command = 4;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearCommand() {
            copyOnWrite();
            instance.clearCommand();
            return this;
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @return Whether the content field is set.
         */
        @Override
        public boolean hasContent() {
            return instance.hasContent();
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @return The content.
         */
        @Override
        public String getContent() {
            return instance.getContent();
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @return The bytes for content.
         */
        @Override
        public com.google.protobuf.ByteString
        getContentBytes() {
            return instance.getContentBytes();
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @param value The content to set.
         * @return This builder for chaining.
         */
        public Builder setContent(
                String value) {
            copyOnWrite();
            instance.setContent(value);
            return this;
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearContent() {
            copyOnWrite();
            instance.clearContent();
            return this;
        }

        /**
         * <code>optional string content = 5;</code>
         *
         * @param value The bytes for content to set.
         * @return This builder for chaining.
         */
        public Builder setContentBytes(
                com.google.protobuf.ByteString value) {
            copyOnWrite();
            instance.setContentBytes(value);
            return this;
        }

        /**
         * <code>optional int32 progress = 6;</code>
         *
         * @return Whether the progress field is set.
         */
        @Override
        public boolean hasProgress() {
            return instance.hasProgress();
        }

        /**
         * <code>optional int32 progress = 6;</code>
         *
         * @return The progress.
         */
        @Override
        public int getProgress() {
            return instance.getProgress();
        }

        /**
         * <code>optional int32 progress = 6;</code>
         *
         * @param value The progress to set.
         * @return This builder for chaining.
         */
        public Builder setProgress(int value) {
            copyOnWrite();
            instance.setProgress(value);
            return this;
        }

        /**
         * <code>optional int32 progress = 6;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearProgress() {
            copyOnWrite();
            instance.clearProgress();
            return this;
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @return Whether the ctime field is set.
         */
        @Override
        public boolean hasCtime() {
            return instance.hasCtime();
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @return The ctime.
         */
        @Override
        public String getCtime() {
            return instance.getCtime();
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @return The bytes for ctime.
         */
        @Override
        public com.google.protobuf.ByteString
        getCtimeBytes() {
            return instance.getCtimeBytes();
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @param value The ctime to set.
         * @return This builder for chaining.
         */
        public Builder setCtime(
                String value) {
            copyOnWrite();
            instance.setCtime(value);
            return this;
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearCtime() {
            copyOnWrite();
            instance.clearCtime();
            return this;
        }

        /**
         * <code>optional string ctime = 7;</code>
         *
         * @param value The bytes for ctime to set.
         * @return This builder for chaining.
         */
        public Builder setCtimeBytes(
                com.google.protobuf.ByteString value) {
            copyOnWrite();
            instance.setCtimeBytes(value);
            return this;
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @return Whether the mtime field is set.
         */
        @Override
        public boolean hasMtime() {
            return instance.hasMtime();
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @return The mtime.
         */
        @Override
        public String getMtime() {
            return instance.getMtime();
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @return The bytes for mtime.
         */
        @Override
        public com.google.protobuf.ByteString
        getMtimeBytes() {
            return instance.getMtimeBytes();
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @param value The mtime to set.
         * @return This builder for chaining.
         */
        public Builder setMtime(
                String value) {
            copyOnWrite();
            instance.setMtime(value);
            return this;
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearMtime() {
            copyOnWrite();
            instance.clearMtime();
            return this;
        }

        /**
         * <code>optional string mtime = 8;</code>
         *
         * @param value The bytes for mtime to set.
         * @return This builder for chaining.
         */
        public Builder setMtimeBytes(
                com.google.protobuf.ByteString value) {
            copyOnWrite();
            instance.setMtimeBytes(value);
            return this;
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @return Whether the extra field is set.
         */
        @Override
        public boolean hasExtra() {
            return instance.hasExtra();
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @return The extra.
         */
        @Override
        public String getExtra() {
            return instance.getExtra();
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @return The bytes for extra.
         */
        @Override
        public com.google.protobuf.ByteString
        getExtraBytes() {
            return instance.getExtraBytes();
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @param value The extra to set.
         * @return This builder for chaining.
         */
        public Builder setExtra(
                String value) {
            copyOnWrite();
            instance.setExtra(value);
            return this;
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearExtra() {
            copyOnWrite();
            instance.clearExtra();
            return this;
        }

        /**
         * <code>optional string extra = 9;</code>
         *
         * @param value The bytes for extra to set.
         * @return This builder for chaining.
         */
        public Builder setExtraBytes(
                com.google.protobuf.ByteString value) {
            copyOnWrite();
            instance.setExtraBytes(value);
            return this;
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @return Whether the idStr field is set.
         */
        @Override
        public boolean hasIdStr() {
            return instance.hasIdStr();
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @return The idStr.
         */
        @Override
        public String getIdStr() {
            return instance.getIdStr();
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @return The bytes for idStr.
         */
        @Override
        public com.google.protobuf.ByteString
        getIdStrBytes() {
            return instance.getIdStrBytes();
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @param value The idStr to set.
         * @return This builder for chaining.
         */
        public Builder setIdStr(
                String value) {
            copyOnWrite();
            instance.setIdStr(value);
            return this;
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearIdStr() {
            copyOnWrite();
            instance.clearIdStr();
            return this;
        }

        /**
         * <code>optional string idStr = 10;</code>
         *
         * @param value The bytes for idStr to set.
         * @return This builder for chaining.
         */
        public Builder setIdStrBytes(
                com.google.protobuf.ByteString value) {
            copyOnWrite();
            instance.setIdStrBytes(value);
            return this;
        }

        // @@protoc_insertion_point(builder_scope:com.muedsa.bilibililiveapiclient.model.CommandDm)
    }

    @Override
    @SuppressWarnings({"unchecked", "fallthrough"})
    protected final Object dynamicMethod(
            MethodToInvoke method,
            Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE: {
                return new CommandDm();
            }
            case NEW_BUILDER: {
                return new Builder();
            }
            case BUILD_MESSAGE_INFO: {
                Object[] objects = new Object[]{
                        "bitField0_",
                        "id_",
                        "oid_",
                        "mid_",
                        "command_",
                        "content_",
                        "progress_",
                        "ctime_",
                        "mtime_",
                        "extra_",
                        "idStr_",
                };
                String info =
                        "\u0000\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001\u1002\u0000\u0002\u1002\u0001" +
                                "\u0003\u1002\u0002\u0004\u1002\u0003\u0005\u1208\u0004\u0006\u1004\u0005\u0007\u1208" +
                                "\u0006\b\u1208\u0007\t\u1208\b\n\u1208\t";
                return newMessageInfo(DEFAULT_INSTANCE, info, objects);
            }
            // fall through
            case GET_DEFAULT_INSTANCE: {
                return DEFAULT_INSTANCE;
            }
            case GET_PARSER: {
                com.google.protobuf.Parser<CommandDm> parser = PARSER;
                if (parser == null) {
                    synchronized (CommandDm.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser =
                                    new DefaultInstanceBasedParser<CommandDm>(
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


    // @@protoc_insertion_point(class_scope:com.muedsa.bilibililiveapiclient.model.CommandDm)
    private static final CommandDm DEFAULT_INSTANCE;

    static {
        CommandDm defaultInstance = new CommandDm();
        // New instances are implicitly immutable so no need to make
        // immutable.
        DEFAULT_INSTANCE = defaultInstance;
        com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
                CommandDm.class, defaultInstance);
    }

    public static CommandDm getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<CommandDm> PARSER;

    public static com.google.protobuf.Parser<CommandDm> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}

