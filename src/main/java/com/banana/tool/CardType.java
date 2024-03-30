package com.banana.tool;

import java.util.Objects;

public enum CardType {
    YEAR("年卡", 1),
    HALF_YEAR("半年卡", 2),
    SEASON("季卡", 3),
    MONTH("月卡", 4),
    TERM("学期卡", 5);

    private String str;
    private int index;

    CardType(String str, int index) {
        this.str = str;
        this.index = index;
    }

    public static String getStr(int index) {
        for (CardType type : CardType.values()) {
            if (type.getIndex() == index) {
                return type.getStr();
            }
        }
        return null;
    }

    public String getStr() {
        return str;
    }

    public static int getIndex(String str) {
        for (CardType type : CardType.values()) {
            if (Objects.equals(type.getStr(), str)) {
                return type.getIndex();
            }
        }
        return 0;
    }

    public int getIndex() {
        return index;
    }
}
