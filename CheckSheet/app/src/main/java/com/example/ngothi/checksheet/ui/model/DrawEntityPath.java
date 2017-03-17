package com.example.ngothi.checksheet.ui.model;

/**
 * Created by FRAMGIA\hoang.van.cuong on 17/03/2017.
 */

public class DrawEntityPath {

    public static final String ACTION_MOVE_TO = "move_to";
    public static final String ACTION_LINE_TO = "line_to";
    public static final String ACTION_QUAD_TO = "quad_to";
    public static final String ACTION_RESET = "reset";
    public static final String ACTION_DRAW = "draw";

    public String action = "";
    public String data = "";

    public DrawEntityPath(String action, String data) {
        this.action = action;
        this.data = data;
    }

    public static class Builder {

        public String action;
        public String data;

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder data(float x, float y) {
            this.data = x + "," + y;
            return this;
        }

        public Builder data(float x1, float y1, float x2, float y2) {
            this.data = x1 + "," + y1 + "," + x2 + "," + y2;
            return this;
        }

        public DrawEntityPath build() {
            return new DrawEntityPath(action, data);
        }
    }
}
