package com.example.kincarta.commons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kincarta.R;
import com.example.kincarta.data.model.Contact;
import com.example.kincarta.data.model.Header;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, E extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<E>> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    protected List<T> items = new ArrayList<>();
    private RecyclerView rv;
    private int viewId;

    public BaseAdapter(RecyclerView rv, int viewId) {
        this.rv = rv;
        this.viewId = viewId;
    }

    public void setData(final List<T> data) {
    if (this.items.isEmpty()) {
            this.items.addAll(data);
            notifyDataSetChanged();
      } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return items.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return compareItems(items.get(oldItemPosition), data.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return compareItemsContent(items.get(oldItemPosition), data.get(newItemPosition));
                }

            });
            diffResult.dispatchUpdatesTo(this);
            this.items.clear();
            this.items.addAll(data);
        }
    }

    @Override
    public BindingViewHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {

        //return createViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.viewId, parent, false));
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            return createViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_header, parent, false));
        } else {
            return createViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_contact, parent, false));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return items.get(position) instanceof Header;
    }


    protected BindingViewHolder<E> createViewHolder(ViewDataBinding viewDataBinding) {
        return new BindingViewHolder<E>((E) viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<E> holder, int position) {
        populateBindViewHolder(holder, items.get(position), position);
        holder.getBinding().executePendingBindings();
    }

    protected abstract void populateBindViewHolder(BindingViewHolder<E> holder, T item, int position);

    protected abstract boolean compareItems(T itemA, T itemB);


    protected abstract boolean compareItemsContent(T itemA, T itemB);

    @Override
    public int getItemCount() {
        return items.size();
    }


}