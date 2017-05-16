package com.example.mamton.testapp.gui.core;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class DictionaryCursorAdapter1 extends
        CursorRecyclerViewAdapter1<DictionaryCursorAdapter1.ViewHolder> {

    public DictionaryCursorAdapter1(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //todo mamton implement
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        MyListItem myListItem = MyListItem.fromCursor(cursor);
        //todo mamton implement
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);

        }
    }

    public static class MyListItem {

        private String name;

        public static MyListItem fromCursor(Cursor cursor) {
            //TODO mamton implement.
            return null;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}