package hu.ait.android.penz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import hu.ait.android.penz.StatisticsActivity;
import hu.ait.android.penz.CreateItemActivity;
import hu.ait.android.penz.ItemListActivity;
import hu.ait.android.penz.MainActivity;
import hu.ait.android.penz.R;
import hu.ait.android.penz.data.Item;
import io.realm.Realm;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tvItem;
        public TextView tvPrice;
        public Button btnDelete;
        public Button btnEdit;
        public int total;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);

        }
    }


    private List<Item> itemsList;
    private Context context;
    private int lastPosition = -1;

    public ItemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvItem.setText(itemsList.get(position).getItemName());
        viewHolder.tvPrice.setText(itemsList.get(position).getPrice());
        viewHolder.ivIcon.setImageResource(
                itemsList.get(position).getItemType().getIconId());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ItemListActivity) context).showEditItemActivity(
                        itemsList.get(viewHolder.getAdapterPosition()).getItemID(),
                        viewHolder.getAdapterPosition());
            }
        });

        setAnimation(viewHolder.itemView, position);
    }

    public void counting() {
        int size = itemsList.size();

        for (int i = 0; i < size; i++) {
            int itemID = itemsList.get(i).getItemType().getValue();

            if (itemID == 0) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.groceries = y + MyPreferences.groceries;
            }

            if (itemID == 1) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.entertainment = y + MyPreferences.entertainment;
            }

            if (itemID == 2) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.clothing = y + MyPreferences.clothing;
            }

            if (itemID == 3) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.transportation = y + MyPreferences.transportation;
            }

            if (itemID == 4) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.misc = y + MyPreferences.misc;
            }
        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

//    public int getItemType(int item) {
//
//        itemsList.get()
//
//        return value;
//    }

    public int calculate(){
        int length = itemsList.size();
        int total = 0;
        for (int i = 0; i < length; i++) {
            String add = itemsList.get(i).getPrice();
            int combine = Integer.parseInt(add);
            total = total + combine;
        }
        return total;
    }

    public void addItem(Item item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, Item item) {
        itemsList.set(index, item);
        notifyItemChanged(index);

    }

    public void removeItem(int index) {

        int itemID = itemsList.get(index).getItemType().getIconId();

        if (itemID == 0) {
            String x = itemsList.get(index).getPrice();
            int y = Integer.parseInt(x);
            MyPreferences.groceries = MyPreferences.groceries - y;
        }

        if (itemID == 1) {
            String x = itemsList.get(index).getPrice();
            int y = Integer.parseInt(x);
            MyPreferences.entertainment = MyPreferences.entertainment - y;
        }

        if (itemID == 2) {
            String x = itemsList.get(index).getPrice();
            int y = Integer.parseInt(x);
            MyPreferences.clothing = MyPreferences.clothing - y;
        }

        if (itemID == 3) {
            String x = itemsList.get(index).getPrice();
            int y = Integer.parseInt(x);
            MyPreferences.transportation = MyPreferences.transportation - y;
        }

        if (itemID == 4) {
            String x = itemsList.get(index).getPrice();
            int y = Integer.parseInt(x);
            MyPreferences.misc = y;
        }

        ((ItemListActivity)context).deleteItem(itemsList.get(index));
        itemsList.remove(index);
        notifyItemRemoved(index);
    }

    public void removeAllItem() {
        ((ItemListActivity)context).deleteAllItem();
        notifyItemRangeRemoved(0, itemsList.size());
        MyPreferences.groceries = 0;
        MyPreferences.entertainment = 0;
        MyPreferences.clothing = 0;
        MyPreferences.transportation = 0;
        MyPreferences.misc = 0;
        itemsList.clear();
    }

    public void removeAllItemMain() {
        ((MainActivity)context).deleteAllItem();
        notifyItemRangeRemoved(0, itemsList.size());
        MyPreferences.groceries = 0;
        MyPreferences.entertainment = 0;
        MyPreferences.clothing = 0;
        MyPreferences.transportation = 0;
        MyPreferences.misc = 0;
        itemsList.clear();
    }


    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(itemsList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(itemsList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Item getItem(int i) {
        return itemsList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }



}
