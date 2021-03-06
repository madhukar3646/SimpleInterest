package com.m.aspirego.merchant_module.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m.aspirego.R;
import com.m.aspirego.merchant_module.presenter.MerchantApiUrls;
import com.m.aspirego.user_module.models.Offer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarchantOffersAdapter extends RecyclerView.Adapter<MarchantOffersAdapter.MyViewHolder> {
List<Offer> offers=null;
Context context;
    ViewOffersDetailsListerner listerner;


    public MarchantOffersAdapter( Context context,List<Offer> offers) {
        this.offers = offers;
        this.context = context;
    }

    public void setListerner(ViewOffersDetailsListerner listerner) {
        this.listerner = listerner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marchant_offers_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int pos) {

        final Offer offer=offers.get(pos);
        Picasso.with(context).load(MerchantApiUrls.OFFERS_IMAGEPATH+offer.getImage()).placeholder(R.mipmap.profile_img)
                .error(R.mipmap.profile_img)
                .into(holder.image);
        holder.offerName.setText(offer.getOfferName());
        holder.merchantName.setText(offer.getMerchantName());
        holder.offerPrice.setText(context.getString(R.string.rs)+offer.getOfferPrice());
        holder.price.setText(context.getString(R.string.rs)+offer.getPrice());
        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.precent.setText(offer.getDiscount()+" %"+" OFF");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listerner!=null){
                    listerner.onOffersClick(offers.get(pos));
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listerner!=null)
                    listerner.onEditOffer(offers.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(offers==null)
        return 0;

        return offers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.offer_name)
        TextView offerName;
        @BindView(R.id.offer_image)
        ImageView image;
        @BindView(R.id.offer_price)
        TextView offerPrice;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.offer_percent)
        TextView precent;
        @BindView(R.id.merchant_name)
        TextView merchantName;
        @BindView(R.id.edit)
        ImageView edit;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    public interface ViewOffersDetailsListerner{
        void onOffersClick(Offer offer);
        void onEditOffer(Offer offer);
    }
}
