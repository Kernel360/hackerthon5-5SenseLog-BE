package com.kernel.sense_log.domain.entity.enumeration;

public enum Tag {
    SAD,        // 슬픔
    DEPRESSED,  // 우울
    NEUTRAL,    // 보통
    HAPPY,      // 기쁨
    JOYFUL;      // 행복
    public static Tag fromString(String value) {
        for (Tag tag : Tag.values()) {
            if (tag.name().equals(value)) {
                return tag;
            }
        }
        return null;
    }

}