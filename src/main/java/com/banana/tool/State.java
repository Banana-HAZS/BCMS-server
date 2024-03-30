package com.banana.tool;

public enum State {
    NORMAL("正常",1),
    EXPIRED("已过期",2),
    REFUNDING("退款中",3),
    REFUND_SUCCESS("已退款",4);

    private String str;
    private int index;

    State(String str, int index) {
        this.str=str;
        this.index=index;
    }

    public String getStr(int index) {
        for (State state : State.values()) {
            if (state.getIndex() == index) {
                return state.getStr();
            }
        }
        return null;
    }
    public String getStr(){
        return this.str;
    }

    public int getIndex(String str) {
        for (State state : State.values()) {
            if (state.getStr() == str) {
                return state.getIndex();
            }
        }
        return 0;
    }
    public int getIndex() {
        return index;
    }
}
