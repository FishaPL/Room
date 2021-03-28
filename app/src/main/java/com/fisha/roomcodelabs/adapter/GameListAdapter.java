package com.fisha.roomcodelabs.adapter;

import android.view.ViewGroup;

import com.fisha.roomcodelabs.roomdatabase.Game;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class GameListAdapter extends ListAdapter<Game, GameViewHolder>
{
    private final GameAdapterListener listener;

    public GameListAdapter(@NonNull DiffUtil.ItemCallback<Game> diffCallback, GameAdapterListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return GameViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game current = getItem(position);
        holder.bind(current, listener);
    }

    public static class GameDiff extends DiffUtil.ItemCallback<Game>
    {
        @Override
        public boolean areItemsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }

}
