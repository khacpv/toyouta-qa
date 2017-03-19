package com.example.ngothi.checksheet.ui.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FRAMGIA\hoang.van.cuong on 17/03/2017.
 */

public class DrawEntityPath {

    public enum Action {
        @SerializedName("move_to")
        ACTION_MOVE_TO,

        @SerializedName("line_to")
        ACTION_LINE_TO,

        @SerializedName("quad_to")
        ACTION_QUAD_TO,

        @SerializedName("reset")
        ACTION_RESET,

        @SerializedName("draw")
        ACTION_DRAW,
    }

    public Action action ;
    public String data = "";

    public DrawEntityPath(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public static class Builder {

        public Action action;
        public String data;

        public Builder action(Action action) {
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
