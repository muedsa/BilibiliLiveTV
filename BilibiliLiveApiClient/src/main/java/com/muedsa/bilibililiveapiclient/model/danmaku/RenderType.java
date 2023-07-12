// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

/**
 * Protobuf enum {@code com.muedsa.bilibililiveapiclient.model.RenderType}
 */
public enum RenderType
        implements com.google.protobuf.Internal.EnumLite {
    /**
     * <code>RenderTypeNone = 0;</code>
     */
    RenderTypeNone(0),
    /**
     * <code>RenderTypeSingle = 1;</code>
     */
    RenderTypeSingle(1),
    /**
     * <code>RenderTypeRotation = 2;</code>
     */
    RenderTypeRotation(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>RenderTypeNone = 0;</code>
     */
    public static final int RenderTypeNone_VALUE = 0;
    /**
     * <code>RenderTypeSingle = 1;</code>
     */
    public static final int RenderTypeSingle_VALUE = 1;
    /**
     * <code>RenderTypeRotation = 2;</code>
     */
    public static final int RenderTypeRotation_VALUE = 2;


    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }
        return value;
    }

    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static RenderType valueOf(int value) {
        return forNumber(value);
    }

    public static RenderType forNumber(int value) {
        switch (value) {
            case 0:
                return RenderTypeNone;
            case 1:
                return RenderTypeSingle;
            case 2:
                return RenderTypeRotation;
            default:
                return null;
        }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<RenderType>
    internalGetValueMap() {
        return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<
            RenderType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<RenderType>() {
                @Override
                public RenderType findValueByNumber(int number) {
                    return RenderType.forNumber(number);
                }
            };

    public static com.google.protobuf.Internal.EnumVerifier
    internalGetVerifier() {
        return RenderTypeVerifier.INSTANCE;
    }

    private static final class RenderTypeVerifier implements
            com.google.protobuf.Internal.EnumVerifier {
        static final com.google.protobuf.Internal.EnumVerifier INSTANCE = new RenderTypeVerifier();

        @Override
        public boolean isInRange(int number) {
            return RenderType.forNumber(number) != null;
        }
    }

    private final int value;

    RenderType(int value) {
        this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:com.muedsa.bilibililiveapiclient.model.RenderType)
}

