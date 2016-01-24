package com.bryan.example.framework.mvp.main;

import java.util.Arrays;
import java.util.List;

import android.os.Handler;

public class MainInteractorImpl implements MainInteractor
{

    @Override
    public void findItem(final OnFinishListener finishListener)
    {
        new Handler().postAtTime(new Runnable()
        {

            @Override
            public void run()
            {
                finishListener.onFinish(createArrayList());
            }
        }, 5000);

    }

    private List<String> createArrayList()
    {
        return Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9",
                "Item 10");
    }
}
