package com.example.ngothi.checksheet.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by FRAMGIA\hoang.van.cuong on 17/03/2017.
 */

public class DrawEntityPath implements Serializable {

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

    @Expose
    public Action action;
    @Expose
    public String data = "";

    public DrawEntityPath(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public void setScale(float ratio) {

        switch (action) {
            case ACTION_LINE_TO:
                if (data == null) {
                    return;
                }
                String[] dataLine = data.split(",");
                data = Float.parseFloat(dataLine[0]) * ratio
                        + ","
                        + Float.parseFloat(dataLine[1]) * ratio;

                break;

            case ACTION_MOVE_TO:
                if (data == null) {
                    return;
                }
                String[] dataMove = data.split(",");
                data = Float.parseFloat(dataMove[0]) * ratio
                        + ","
                        + Float.parseFloat(dataMove[1]) * ratio;

                break;

            case ACTION_QUAD_TO:
                if (data == null) {
                    return;
                }
                String[] dataQuad = data.split(",");
                data = Float.parseFloat(dataQuad[0]) * ratio
                        + ","
                        + Float.parseFloat(dataQuad[1]) * ratio
                        + ","
                        + Float.parseFloat(dataQuad[2]) * ratio
                        + ","
                        + Float.parseFloat(dataQuad[3]) * ratio;

                break;
            default:
        }
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
