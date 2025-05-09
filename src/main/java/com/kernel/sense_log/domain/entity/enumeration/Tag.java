package com.kernel.sense_log.domain.entity.enumeration;

public enum Tag {
    슬픔,
    우울,
    보통,
    기쁨,
    행복;
    public static Tag fromString(String value) {
        for (Tag tag : Tag.values()) {
            if (tag.name().equals(value)) {
                return tag;
            }
        }
        return null;
    }

}