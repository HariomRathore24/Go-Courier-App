package com.example.courierserviceapp;// CardAdapter.java
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final List<CardData> dataSet;
    private Context context ;

    public CardAdapter(List<CardData> dataSet , Context context ) {
        this.dataSet = dataSet;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconImageView;
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            descriptionTextView = itemView.findViewById(R.id.textDescription);
        }

        public void bindData(CardData data, Context context, int position) {
            iconImageView.setImageResource(data.getIconResId());
            titleTextView.setText(data.getTitle());
            descriptionTextView.setText(data.getDescription());

            // Set OnClickListener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0: // first card clicked
                        case 1: // second card clicked
                            Intent secondCardIntent = new Intent(context, IntercityScreen.class);
                            context.startActivity(secondCardIntent);
                            break;
                        default:
                            // For other cards, open the default CalculateShipping activity
                            Intent defaultIntent = new Intent(context, CalculateShipping.class);
                            context.startActivity(defaultIntent);
                            break;
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(dataSet.get(position), context, position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

