package com.example.groups_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tab_accessor extends FragmentPagerAdapter {

    public tab_accessor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                chatFrag Chats_Fragment = new chatFrag();
                return Chats_Fragment;
            case 1:
                groupFrag Groups_Fragment = new groupFrag();
                return Groups_Fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {

        switch (i)
        {
            case 0:
                return "Chats";
            case 1:
                groupFrag Groups_Fragment = new groupFrag();
                return "Groups";

            default:
                return null;
        }

    }
}
