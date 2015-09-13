package com.enricoros.sides;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MagazineActivity extends AppCompatActivity {


    private DailyNews mDailyNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        // create the main data objects
        mDailyNews = new DailyNews(this);

        // Set a ToolBar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler);
        DailyNews.initRv(this, rv);


        //ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        MainPagerAdapter mainTabsAdapter = new MainPagerAdapter(this, mDailyNews);
        //viewPager.setAdapter(mainTabsAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabbar);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabsFromPagerAdapter(mainTabsAdapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_magazine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static class MainPagerAdapter extends PagerAdapter {
        private final ArrayList<Page> mPages;

        private static class Page {
            String title;
            PageProviderInterface provider;
            View pageView;
        }

        public MainPagerAdapter(Context context, DailyNews dailyNews) {
            mPages = new ArrayList<>();

            Page page1 = new Page();
            page1.title = context.getString(R.string.daily_news_tab_title);
            page1.provider = dailyNews;
            mPages.add(page1);

            Page page2 = new Page();
            page2.title = context.getString(R.string.add_sides_tab_title);
            page2.provider = null;
            mPages.add(page2);
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < 0 || position >= mPages.size())
                return null;
            return mPages.get(position).title;
        }

        @Override
        public boolean isViewFromObject(View pageView, Object page) {
            if (!(page instanceof Page))
                return false;
            return ((Page) page).pageView == pageView;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position < 0 || position >= mPages.size())
                return null;
            final Page page = mPages.get(position);
            final Context context = container.getContext();
            if (page.pageView != null || context == null)
                return page;

            if (page.provider != null) {
                // create the view
                page.pageView = page.provider.getViewInstance();

                // add the view to the container
                container.addView(page.pageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }

            // return page as the object
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (!(object instanceof Page))
                return;
            final Page page = (Page) object;
            if (page.pageView == null)
                return;
            container.removeView(page.pageView);
            page.pageView = null;
        }

    }

}
