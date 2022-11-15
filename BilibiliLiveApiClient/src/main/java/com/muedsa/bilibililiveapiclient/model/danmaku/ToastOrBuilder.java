// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

public interface ToastOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.muedsa.bilibililiveapiclient.model.Toast)
        com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>optional string text = 1;</code>
     *
     * @return Whether the text field is set.
     */
    boolean hasText();

    /**
     * <code>optional string text = 1;</code>
     *
     * @return The text.
     */
    String getText();

    /**
     * <code>optional string text = 1;</code>
     *
     * @return The bytes for text.
     */
    com.google.protobuf.ByteString
    getTextBytes();

    /**
     * <code>optional int32 duration = 2;</code>
     *
     * @return Whether the duration field is set.
     */
    boolean hasDuration();

    /**
     * <code>optional int32 duration = 2;</code>
     *
     * @return The duration.
     */
    int getDuration();

    /**
     * <code>optional bool show = 3;</code>
     *
     * @return Whether the show field is set.
     */
    boolean hasShow();

    /**
     * <code>optional bool show = 3;</code>
     *
     * @return The show.
     */
    boolean getShow();

    /**
     * <code>optional .com.muedsa.bilibililiveapiclient.model.Button button = 4;</code>
     *
     * @return Whether the button field is set.
     */
    boolean hasButton();

    /**
     * <code>optional .com.muedsa.bilibililiveapiclient.model.Button button = 4;</code>
     *
     * @return The button.
     */
    Button getButton();
}
