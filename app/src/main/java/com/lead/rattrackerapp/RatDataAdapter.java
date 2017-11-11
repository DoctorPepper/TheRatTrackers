package com.lead.rattrackerapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lead.rattrackerapp.Model.Sightings.Sighting;

import java.util.List;

/**
 * Created by ejjac on 10/7/2017.
 *
 * Adapter for the main activity recycler view
 */

class RatDataAdapter extends RecyclerView.Adapter<RatDataAdapter.TextViewHolder>{
    private final List<Sighting> in;
    private final LayoutInflater inflater;
    private SightingClickListener listener;

    RatDataAdapter(Context context, List<Sighting> in) {
        inflater = LayoutInflater.from(context);
        this.in = in;
    }

    /**
     * Called for the creation of a new RecyclerView
     *
     * @param parent the ViewGroup into which the new View will be added
     * @param viewType the view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */
    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View label = inflater.inflate(R.layout.sighting_row, parent, false);
        return new TextViewHolder(label);
    }

    /**
     * Called by RecyclerView to display the new data
     *
     * @param holder the ViewHolder to represent the content of the item
     * @param position the position of the item
     */
    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        String sighting = in.get(position).shortLabel();
        holder.getView().setText(sighting);
    }

    /**
     * Get the number of items
     *
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return in.size();
    }

    /**
     * Get a Sighting Item
     *
     * @param index the index to retrieve the Item
     * @return the Sighting Item
     */
    public Sighting getItem(int index) {
        return in.get(index);
    }

    /**
     * Assigns the SightingClickListener in order to click on the Sighting
     *
     * @param listener the listener to be set
     */
    void setClickListener(SightingClickListener listener) {
        this.listener = listener;
    }

    interface SightingClickListener {
        void onItemClick(int p);
    }

    class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView view;

        TextViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.rat_info_row);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked
         * @param view the View that was clicked
         */
        @Override
        public void onClick(View view) {
            if (listener != null) listener.onItemClick(getAdapterPosition());
        }

        public TextView getView() {
            return view;
        }
    }
}
