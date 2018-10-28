package com.CEYMChatClient.View;

import java.util.HashMap;
import java.util.Map;

/**
 * creates and handles a hash map with emojis (the key is the name of the emoji and the value is the emoji object)
 */
public class EmojisMap {

    private Map<String, Emoji> emojiHashMap = new HashMap<String, Emoji>();

    public Map<String, Emoji> createEmojiHashMap() {
        final String[][] emojisarray = new String[][]{
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
                },

                {
                        "😥",
                        "\uD83D\uDE25",
                        "disappointed but relieved face",
                        "phew"

                },
                {
                        "😰",
                        "\uD83D\uDE30",
                        "face with open mouth and cold sweat",
                        "cold_sweat"

                },
                {
                        "😅",
                        "\uD83D\uDE05",
                        "smiling face with open mouth and cold sweat",
                        "sweat_smile"
                },
                {
                        "😓",
                        "\uD83D\uDE13",
                        "face with cold sweat",
                        "sweat"
                },
                {
                        "😩",
                        "\uD83D\uDE29",
                        "weary face",
                        "weary"


                },
                {
                        "😫",
                        "\uD83D\uDE2B",
                        "tired face",
                        "tired_face"


                },
                {
                        "😨",
                        "\uD83D\uDE28",
                        "fearful face",
                        "fearful"
                },
                {
                        "😱",
                        "\uD83D\uDE31",
                        "face screaming in fear",
                        "scream"
                },
                {
                        "😠",
                        "\uD83D\uDE20",
                        "angry face",
                        "angry"

                },
                {
                        "😡",
                        "\uD83D\uDE21",
                        "pouting face",
                        "rage"
                },
                {
                        "😤",
                        "\uD83D\uDE24",
                        "face with look of triumph",
                        "triumph"

                },
                {
                        "😖",
                        "\uD83D\uDE16",
                        "confounded face",
                        "confounded"

                },
                {
                        "😆",
                        "\uD83D\uDE06",
                        "smiling face with open mouth and tightly-closed eyes",
                        "laughing"
                },
                {
                        "😋",
                        "\uD83D\uDE0B",
                        "face savouring delicious food",
                        "yum"

                },
                {
                        "😷",
                        "\uD83D\uDE37",
                        "face with medical mask",
                        "mask"

                },
                {
                        "😎",
                        "\uD83D\uDE0E",
                        "smiling face with sunglasses",
                        "sunglasses"
                },
                {
                        "😴",
                        "\uD83D\uDE34",
                        "sleeping face",
                        "sleeping"
                },
                {
                        "😵",
                        "\uD83D\uDE35",
                        "dizzy face",
                        "dizzy_face"
                },
                {
                        "😲",
                        "\uD83D\uDE32",
                        "astonished face",
                        "astonished"
                },
                {
                        "😟",
                        "\uD83D\uDE1F",
                        "worried face",
                        "worried"
                },
                {
                        "😦",
                        "\uD83D\uDE26",
                        "frowning face with open mouth",
                        "frowning"
                },
                {
                        "😧",
                        "\uD83D\uDE27",
                        "anguished face",
                        "anguished"
                },
                {
                        "😈",
                        "\uD83D\uDE08",
                        "smiling face with horns",
                        "smiling_imp"
                },
                {
                        "👿",
                        "\uD83D\uDC7F",
                        "imp",
                        "imp"
                },
                {
                        "😮",
                        "\uD83D\uDE2E",
                        "face with open mouth",
                        "open_mouth"
                },
                {
                        "😬",
                        "\uD83D\uDE2C",
                        "grimacing face",
                        "grimacing"
                },
                {
                        "😐",
                        "\uD83D\uDE10",
                        "neutral face",

                        "neutral_face"

                },
                {
                        "😕",
                        "\uD83D\uDE15",
                        "confused face",
                        "confused"
                },
                {
                        "😯",
                        "\uD83D\uDE2F",
                        "hushed face",
                        "hushed"
                },
                {
                        "😶",
                        "\uD83D\uDE36",
                        "face without mouth",
                        "no_mouth"

                },
                {
                        "😇",
                        "\uD83D\uDE07",
                        "smiling face with halo",
                        "innocent"
                },
                {
                        "😏",
                        "\uD83D\uDE0F",
                        "smirking face",
                        "smirk"
                },
                {
                        "😑",
                        "\uD83D\uDE11",
                        "expressionless face",
                        "expressionless"
                },
                {
                        "👲",
                        "\uD83D\uDC72",
                        "man with gua pi mao",
                        "man_with_gua_pi_mao"
                },

                {
                        "👳",
                        "\uD83D\uDC73",
                        "man with turban",
                        "man_with_turban"
                },
                {
                        "👮",
                        "\uD83D\uDC6E",
                        "police officer",
                        "cop"
                },
                {
                        "👷",
                        "\uD83D\uDC77",
                        "construction worker",
                        "supports_fitzpatrick"
                },
                {
                        "💂",
                        "\uD83D\uDC82",
                        "guardsman",
                        "guardsman"
                },
                {
                        "👶",
                        "\uD83D\uDC76",
                        "baby",
                        "baby"
                },
                {
                        "👦",
                        "\uD83D\uDC66",
                        "boy",
                        "boy"
                },
                {
                        "👧",
                        "\uD83D\uDC67",
                        "girl",
                        "girl"
                },
                {
                        "👨",
                        "\uD83D\uDC68",
                        "man",
                        "man"
                },
                {
                        "👩",
                        "\uD83D\uDC69",
                        "woman",
                        "woman"
                },
                {
                        "👴",
                        "\uD83D\uDC74",
                        "older man",
                        "older_man"
                },
                {
                        "👵",
                        "\uD83D\uDC75",
                        "older woman",
                        "older_woman"
                },
                {
                        "👱",
                        "\uD83D\uDC71",
                        "person with blond hair",
                        "person_with_blond_hair"
                },
                {
                        "👼",
                        "\uD83D\uDC7C",
                        "baby angel",
                        "angel"

                },
                {
                        "👸",
                        "\uD83D\uDC78",
                        "princess",
                        "princess"

                },
                {
                        "😺",
                        "\uD83D\uDE3A",
                        "smiling cat face with open mouth",
                        "smiley_cat"

                },
                {
                        "😸",
                        "\uD83D\uDE38",
                        "grinning cat face with smiling eyes",
                        "smile_cat"

                },
                {
                        "😻",
                        "\uD83D\uDE3B",
                        "smiling cat face with heart-shaped eyes",
                        "heart_eyes_cat"

                },
                {
                        "😽",
                        "\uD83D\uDE3D",
                        "kissing cat face with closed eyes",
                        "kissing_cat"

                },
                {
                        "😼",
                        "\uD83D\uDE3C",
                        "cat face with wry smile",
                        "smirk_cat"

                },
                {
                        "🙀",
                        "\uD83D\uDE40",
                        "weary cat face",
                        "scream_cat"

                },
                {
                        "😿",
                        "\uD83D\uDE3F",
                        "crying cat face",
                        "crying_cat_face"
                },
                {
                        "😹",
                        "\uD83D\uDE39",
                        "cat face with tears of joy",
                        "joy_cat"
                },
                {
                        "😾",
                        "\uD83D\uDE3E",
                        "pouting cat face",
                        "pouting_cat"
                },
                {
                        "👹",
                        "\uD83D\uDC79",
                        "japanese ogre",
                        "japanese_ogre"
                },
                {
                        "👺",
                        "\uD83D\uDC7A",
                        "japanese goblin",
                        "japanese_goblin"
                },
                {
                        "🙈",
                        "\uD83D\uDE48",
                        "see-no-evil monkey",
                        "see_no_evil"
                },
                {
                        "🙉",
                        "\uD83D\uDE49",
                        "hear-no-evil monkey",
                        "hear_no_evil"
                },
                {
                        "🙊",
                        "\uD83D\uDE4A",
                        "speak-no-evil monkey",
                        "speak_no_evil"
                },
                {
                        "💀",
                        "\uD83D\uDC80",
                        "skull",
                        "skull"
                },
                {
                        "👽",
                        "\uD83D\uDC7D",
                        "extraterrestrial alien",
                        "alien"
                },
                {
                        "💩",
                        "\uD83D\uDCA9",
                        "pile of poo",
                        "hankey",
                },
                {
                        "🔥",
                        "\uD83D\uDD25",
                        "fire",
                        "fire"
                }

        };

        fillEmojisHashMap(emojisarray);

        return emojiHashMap;
    }

        /**
         * fills the emojiHashMap with all the emojis as Emojis object
         * @param emojisArray an array with all of emojis object
         */
    public void fillEmojisHashMap(String[]... emojisArray) {
            for (String[] anEmojisArray : emojisArray) {
                    emojiHashMap.put(anEmojisArray[3], new Emoji(anEmojisArray[0], anEmojisArray[1], anEmojisArray[2], anEmojisArray[3]));
            }
    }
}


