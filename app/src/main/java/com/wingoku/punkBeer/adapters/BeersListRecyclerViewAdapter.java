package com.wingoku.punkBeer.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wingoku.punkBeer.R;
import com.wingoku.punkBeer.eventBus.OnBeerListCardClickedEvent;
import com.wingoku.punkBeer.fragments.BeerListFragment;
import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.models.db.Beers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Umer on 6/14/2017.
 */

public class BeersListRecyclerViewAdapter extends RecyclerView.Adapter<BeersListRecyclerViewAdapter.BeersListRecyclerViewHolder>{

    private Context mContext;
    private int mLayoutFileID;

    private List<Beer> mBeerList;

    private Picasso mPicasso;

    /**
     * {@link BeersListRecyclerViewAdapter} constructor
     * @param context Application/Activity Context
     * @param cellLayoutFileID Resource ID of the layout that will be used by the recyclerView
     */
    public BeersListRecyclerViewAdapter(Context context, int cellLayoutFileID, Picasso picasso){
        mContext = context;
        mBeerList = new ArrayList<>();
        mLayoutFileID = cellLayoutFileID;
        mPicasso = picasso;
    }

    @Override
    public BeersListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(mLayoutFileID, parent, false);

        return new BeersListRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BeersListRecyclerViewHolder holder, int position) {
        String beerName = mBeerList.get(position).getName();
        holder.mBeerNameTextView.setText(beerName);
        String ph = mBeerList.get(position).getPH().toString();
        holder.mBeerPhTextView.setText(ph);
        mPicasso.load(mBeerList.get(position).getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.mBeerThumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return mBeerList.size();
    }

    class BeersListRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_beerPic)
        ImageView mBeerThumbnailImageView;

        @BindView(R.id.tv_beerName)
        TextView mBeerNameTextView;

        @BindView(R.id.tv_beerPh)
        TextView mBeerPhTextView;

        @BindView(R.id.card_view)
        CardView mCardView;

        public BeersListRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // in case the user taps on image, we should be able to recieve that click event
        @OnClick({R.id.card_view, R.id.imageView_beerPic})
        public void onClick(View v) {
            /**
             * The event bus will relay this event to {@link BeerListFragment} that will in turn send this to MainActivity
             */
            EventBus.getDefault().post(new OnBeerListCardClickedEvent(this.getAdapterPosition()));
        }
    }

    public void updateDataSet(Beers beer) {
        mBeerList.clear();
        mBeerList.addAll(beer.getBeerList());
        notifyDataSetChanged();
    }
}