package aspi.myclass.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aspi.myclass.R;
import aspi.myclass.activity.BazarActivity;
import aspi.myclass.model.BazarModel;

public class BazarAdapter extends RecyclerView.Adapter<BazarAdapter.cvh> {

    List<BazarModel> bazarModels;
    Context contexts;


    public BazarAdapter(List<BazarModel> contents, Context context) {
        this.bazarModels = contents;
        this.contexts = context;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bazar, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {

        final BazarModel bazarModel = bazarModels.get(position);

        holder.title.setText(bazarModel.title);
        holder.description.setText(bazarModel.description);
        holder.price.setText(contexts.getResources().getString(R.string.price_) + ": " + bazarModel.price);


        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BazarActivity.Buy_App(bazarModel.Sku,contexts);
            }
        });

    }

    public int getItemCount() {
        return bazarModels.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        TextView title, description, price;
        ImageView image;
        Button buy;


        public cvh(View iv) {
            super(iv);

            title = iv.findViewById(R.id.adapter_bazar_title);
            description = iv.findViewById(R.id.adapter_bazar_description);
            price = iv.findViewById(R.id.adapter_bazar_price);
            image = iv.findViewById(R.id.adapter_bazar_image);
            buy = iv.findViewById(R.id.adapter_bazar_buy);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    BazarModel model = Content_student.get(getPosition());
                }
            });
        }

    }

}
