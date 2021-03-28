package com.fisha.roomcodelabs.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fisha.roomcodelabs.R;
import com.fisha.roomcodelabs.databinding.GameItemBinding;
import com.fisha.roomcodelabs.roomdatabase.Game;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GameViewHolder extends RecyclerView.ViewHolder
{
    private final GameItemBinding binding;

    public GameViewHolder(@NonNull GameItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Game game, GameAdapterListener listener)
    {
        binding.textViewGameTitle.setText(game.getTitle());
        Glide.with(binding.imageViewGameCover.getContext())
                .load(game.getCoverUrl())
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imageViewGameCover);

        binding.cardView.setOnClickListener(view -> {
            listener.onGameClick(game);
        });
        //binding.executePendingBindings();
    }

    static GameViewHolder create(ViewGroup parent) {
        GameItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.game_item, parent, false);
        //binding.setCallback(gameClickCallback);
        //binding.setViewModel()
        return new GameViewHolder(binding);
    }
}
