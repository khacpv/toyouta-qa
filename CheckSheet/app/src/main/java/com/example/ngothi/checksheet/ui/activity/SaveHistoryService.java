package com.example.ngothi.checksheet.ui.activity;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.example.ngothi.checksheet.ui.model.Step;
import java.util.Queue;

/**
 * Created by eo_cuong on 3/16/17.
 */

public class SaveHistoryService extends IntentService {

    private Queue<Step> mStepQueue;


    public SaveHistoryService() {
        super("SaveHistoryService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {



    }
}
