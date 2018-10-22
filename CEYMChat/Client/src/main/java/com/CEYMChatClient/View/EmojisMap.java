package com.CEYMChatClient.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmojisMap {

    private Map<String, Emoji> emojiHashMap = new HashMap<String, Emoji>();

    public Map<String, Emoji> createEmojiHashMap() {
        String[][] emojisarray = new String[][]{
                {
                        "😄",
                        "\uD83D\uDE04",
                        "smiling face with open mouth and smiling eyes",
                        "happy"
                },
                {
                        "😀",
                        "\uD83D\uDE00",
                        "grinning face",
                        "smile"
                },

                {
                        "😊",
                        "\uD83D\uDE0A",
                        "smiling face with smiling eyes",
                        "proud"
                },

                {
                        "☺",
                        "\u263A",
                        "white smiling face",
                        "blush"
                },

                {
                        "😉",
                        "\uD83D\uDE09",
                        "winking face",
                        "flirt"
                },

                {
                        "😍",
                        "\uD83D\uDE0D",
                        "smiling face with heart-shaped eyes",
                        "love"
                },

                {
                        "😘",
                        "\uD83D\uDE18",
                        "face throwing a kiss",
                        "flirt"
                },

                {
                        "😚",
                        "\uD83D\uDE1A",
                        "kissing face with closed eyes",
                        "kiss"
                },

                {
                        "😗",
                        "\uD83D\uDE17",
                        "kissing face",
                        "kissFace"
                },

                {
                        "😙",
                        "\uD83D\uDE19",
                        "kissing face with smiling eyes",
                        "kissFaceWithSmile"
                },

                {
                        "😜",
                        "\uD83D\uDE1C",
                        "face with stuck-out tongue and winking eye",
                        "silly"
                },

                {
                        "😝",
                        "\uD83D\uDE1D",
                        "face with stuck-out tongue and tightly-closed eyes",
                        "prank"

                },

                {
                        "😛",
                        "\uD83D\uDE1B",
                        "face with stuck-out tongue",
                        "muchSilly"
                },

                {
                        "😳",
                        "\uD83D\uDE33",
                        "flushed face",
                        "flushed"
                },

                {
                        "😁",
                        "\uD83D\uDE01",
                        "grinning face with smiling eyes",
                        "grinningSmileyEyes "
                },

                {
                        "😔",
                        "\uD83D\uDE14",
                        "pensive face",
                        "pensive"
                },

                {
                        "😌",
                        "\uD83D\uDE0C",
                        "relieved face",
                        "whew"
                },

                {
                        "😒",
                        "\uD83D\uDE12",
                        "unamused face",
                        "meh"
                },

                {
                        "😞",
                        "\uD83D\uDE1E",
                        "disappointed face",
                        "sad"
                },

                {
                        "😣",
                        "\uD83D\uDE23",
                        "persevering face",
                        "struggling"
                },

                {
                        "😢",
                        "\uD83D\uDE22",
                        "crying face",
                        "sad"
                },

                {
                        "😂",
                        "\uD83D\uDE02",
                        "face with tears of joy",
                        "tears"
                },

                {
                        "😭",
                        "\uD83D\uDE2D",
                        "loudly crying face",
                        "cry",
                },

                {
                        "😪",
                        "\uD83D\uDE2A",
                        "sleepy face",
                        "tired"
                },

                {
                        "😥",
                        "\uD83D\uDE25",
                        "disappointed but relieved face",
                        "phew",
                },

                {
                        "😰",
                        "\uD83D\uDE30",
                        "face with open mouth and cold sweat",
                        "nervous"
                },

                {
                        "😅",
                        "\uD83D\uDE05",
                        "smiling face with open mouth and cold sweat",
                        "hot"
                },

                {
                        "😓",
                        "\uD83D\uDE13",
                        "face with cold sweat",
                        "cold"
                },

                {
                        "😩",
                        "\uD83D\uDE29",
                        "weary face",
                        "tired"
                }

        };

        fillEmojisHashMap(emojisarray);

        return emojiHashMap;
    }

    public void fillEmojisHashMap(String[][] emojisArray) {
        for (int i = 0; i < emojisArray.length; i++) {
            emojiHashMap.put(emojisArray[i][3], new Emoji(emojisArray[i][0], emojisArray[i][1], emojisArray[i][2], emojisArray[i][3]));
        }
    }
}


