package com.example.mamton.testapp.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mamton.testapp.R;
import com.example.mamton.testapp.gui.DictFragment.OnListFragmentInteractionListener;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ?? todo mamton использовать com.mikepenz.fastadapter.items ?
 */
public class MyDictRecyclerViewAdapter extends
        RecyclerView.Adapter<MyDictRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    public MyDictRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dict, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = DBMetaInfo.Tables.values()[position];

        holder.mIdView.setText(holder.mItem.getMetaInfo().getTable().getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DBMetaInfo.Tables.values().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        @BindView(R.id.id)
        public TextView mIdView;

        public DBMetaInfo.Tables mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

    }
}
