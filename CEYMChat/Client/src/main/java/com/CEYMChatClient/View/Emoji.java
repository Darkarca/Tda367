package com.CEYMChatClient.View;

import java.util.ArrayList;

public class Emoji {

    private String emojiChar;
    private String emojiCode;
    private String discription;
    private String tag;

    Emoji ( String emojiChar, String emojiCode, String discription, String tag) {
        this.emojiChar = emojiChar;
        this.emojiCode = emojiCode;
        this.discription = discription;
        this.tag = tag;
    }

    public String getEmojiChar() {
        return emojiChar;
    }
}
