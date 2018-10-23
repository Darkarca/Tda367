package com.CEYMChatClient.View;


/**
 * Objects of this emoji class uses in Emojis map as elements
 */
public class Emoji {

    private String emojiChar;
    private String emojiCode;
    private String description;
    private String tag;

    public Emoji(String emojiChar, String emojiCode, String description, String tag) {
        this.emojiChar = emojiChar;
        this.emojiCode = emojiCode;
        this.description = description;
        this.tag = tag;
    }

    public String getEmojiChar() {
        return emojiChar;
    }
}
