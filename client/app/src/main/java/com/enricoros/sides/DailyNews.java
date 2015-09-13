package com.enricoros.sides;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * TODO: document your custom view class.
 */
public class DailyNews extends FrameLayout implements PageProviderInterface {

    private View mMainView;

    public DailyNews(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    @Override
    public View getViewInstance() {
        if (mMainView == null) {
            mMainView = inflate(getContext(), R.layout.dummy_linear, null);
            addView(mMainView);
        }
        return this;
    }
}

