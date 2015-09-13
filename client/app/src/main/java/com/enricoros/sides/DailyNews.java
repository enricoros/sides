package com.enricoros.sides;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class DailyNews extends FrameLayout implements PageProviderInterface {

    private final Context mContext;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DailyNewsAdapter mAdapter;


    public DailyNews(Context context) {
        super(context);
        mContext = context;
        setWillNotDraw(false);
    }


    @Override
    public View getViewInstance() {
        if (mRecyclerView != null)
            return this;

        // TODO: RESTORE THIS!
        //mRecyclerView = (RecyclerView) inflate(getContext(), R.layout.daily_news_page, null);
        //View root = inflate(getContext(), R.layout.daily_news_page, null);
        //mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DailyNewsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        addView(mRecyclerView);
        //addView(root);
        return this;
    }

    public static void initRv(Context context, RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setItemViewCacheSize(20);
        rv.setAdapter(new DailyNewsAdapter());
    }


    private static class DailyNewsAdapter extends RecyclerView.Adapter<DailyNewsAdapter.ViewHolder> {

        private final String[] mTopics;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView mBackground;
            public TextView mTitleView;
            public TextView mNumberView;
            public ViewPager mPager;

            public ViewHolder(View root) {
                super(root);
                mBackground = (ImageView) root.findViewById(android.R.id.background);
                mTitleView = (TextView) root.findViewById(android.R.id.text1);
                mNumberView = (TextView) root.findViewById(android.R.id.text2);
                mPager = (ViewPager) root.findViewById(R.id.pager);

                mPager.setAdapter(new InPlacePagerAdapter());
                mPager.setCurrentItem(1);
                mPager.setOffscreenPageLimit(3);
            }

            static class InPlacePagerAdapter extends android.support.v4.view.PagerAdapter {
                public Object instantiateItem(ViewGroup collection, int position) {
                    int resId = 0;
                    switch (position) {
                        case 0:
                            resId = R.id.side_one;
                            break;
                        case 1:
                            resId = R.id.side_none;
                            break;
                        case 2:
                            resId = R.id.side_two;
                            break;
                    }
                    return collection.findViewById(resId);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                }

                @Override
                public int getCount() {
                    return 3;
                }

                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {
                    return arg0 == arg1;
                }
            }
        }

        public DailyNewsAdapter() {
            mTopics = new String[]{
                    "The US makes deal with Iran – economic sanctions on Iran will be lifted if Iran’s nuclear programs are reduced",
                    "Hilary Clinton didn’t use a government issued and secured email address while serving as Secretary of State; instead used a private email address and private email server.  The server’s digital records have been deleted.",
                    "Undercover video reveals Planned Parenthood sells fetal organs from abortions to science research labs.  Republicans introduce legislation to end government money for Planned Parenthood",
                    "Local news reporter and cameraman shot to death during live broadcast.  Gunman was ex-colleague/on-air reporter.",
                    "The Affordable Care Act, AKA “Obamacare” -- Performance Review",
                    "The County Clerk in Rowan Country (Kim Davis), refused to issue marriage licenses to gay couples.  The local judge put her in jail."
            };
        }

        @Override
        public DailyNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.topic_card, parent, false);

            // create a local ViewHolder around it
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitleView.setText(mTopics[position]);
            holder.mNumberView.setText("Topic " + (position + 1));
            holder.mPager.setCurrentItem(1);
        }

        @Override
        public int getItemCount() {
            return mTopics.length;
        }
    }

}
