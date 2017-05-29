package com.example.mamton.testapp.gui.core;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.example.mamton.testapp.R;
import com.example.mamton.testapp.db.DB;
import com.example.mamton.testapp.db.DictionaryFacadeFactory;
import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryCursorAdapter extends
        ChangebleCursorRecyclerViewAdapter<DictionaryCursorAdapter.ViewHolder, DBEntity> {

    @NonNull
    private final DBMetaInfo.Tables meta;

    @NonNull
    private final LayoutInflater inflater;

    public DictionaryCursorAdapter(
            @NonNull final Context context,
            @Nullable final Cursor cursor,
            @NonNull DBMetaInfo.Tables meta) {
        super(cursor, DB.FIELD_COMMON_ID);
        this.meta = meta;
        inflater = LayoutInflater.from(context);
    }

    public DictionaryCursorAdapter(
            @NonNull final Context context,
            @Nullable final Cursor cursor,
            @Nullable final String comparisonColumn,
            @NonNull DBMetaInfo.Tables meta) {
        super(cursor, comparisonColumn, DB.FIELD_COMMON_ID);
        this.meta = meta;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor,
            final int position) {
        cursor.moveToPosition(position);
        final DBEntity entity = DictionaryFacadeFactory.getFacade(meta,
                viewHolder.itemView.getContext()).createInstance(cursor);
        viewHolder.bindItem(entity);
        if (getOnItemClickListener() != null) {
            viewHolder.itemView.setOnClickListener((view) ->
                    getOnItemClickListener().onItemSelected(entity.getId(), position));
            viewHolder.deleteButton.setOnClickListener((view) ->
                    getOnItemClickListener().onItemDelete(entity.getId(), position));
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.dictionaryitem_list_item_content, parent, false);

        //todo mamton add fields!
        return new ViewHolder(view, meta);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        TextView idView;

        @BindView(R.id.item_content)
        TextView contentView;

        @BindView(R.id.item_delete)
        Button deleteButton;

        private DBMetaInfo.Tables meta;
        private DBEntity entity;

        private Map<ColumnMetaInfo, View> views = new HashMap<>();

        public ViewHolder(View view, DBMetaInfo.Tables meta) {
            super(view);
            ButterKnife.bind(this, view);
            this.meta = meta;

        }

        public void bindItem(@NonNull DBEntity entity) {
            this.entity = entity;
            idView.setText(String.valueOf(entity.getId()));
            StringBuilder itemContent = new StringBuilder();
            Stream.ofNullable(entity.getValues().entrySet())
                    .filter(entry -> (entry.getKey().getFlags() & ColumnMetaInfo.FLAG_MAIN) == ColumnMetaInfo.FLAG_MAIN)
                    .forEach(entry -> itemContent.append(entry.getValue().getShownValue()).append(' '));
            contentView.setText(itemContent.toString());
        }

    }
}
