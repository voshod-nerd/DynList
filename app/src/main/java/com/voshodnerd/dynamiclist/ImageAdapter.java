package com.voshodnerd.dynamiclist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.voshodnerd.dynamiclist.model.ImageModel;
import com.voshodnerd.dynamiclist.rest.DownloadImageTask;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private Context mContext;
    private List<ImageModel> imagelist;
    PostItemListener mItemListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView idText,titleText,urltext;
        ImageView imageView;
        PostItemListener mItemListener;
        // each data item is just a string in this case

        public MyViewHolder(View view,PostItemListener postItemListener) {
            super(view);
            idText = (TextView) view.findViewById(R.id.tv1);
            titleText = (TextView) view.findViewById(R.id.tv2);
            urltext = (TextView) view.findViewById(R.id.tv3);
            imageView=(ImageView) view.findViewById(R.id.simpleImageView);
            this.mItemListener = postItemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImageModel item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getId());

            notifyDataSetChanged();

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ImageAdapter(Context context,List<ImageModel> myDataset,PostItemListener itemListener) {
        mContext = context;
        imagelist=myDataset;
        mItemListener = itemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View itemView =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView, this.mItemListener);

        return  viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ImageModel  image= imagelist.get(position);
        holder.idText.setText(image.getId().toString());
        holder.titleText.setText(image.getTitle());
        holder.urltext.setText(image.getUrl());

       DownloadImageTask img= new DownloadImageTask(holder.imageView);
       img.execute(image.getUrl());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       return imagelist.size();

    }

    public void updateImageModelList(List<ImageModel> items) {
        imagelist = items;
        notifyDataSetChanged();
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    private ImageModel getItem(int adapterPosition) {
        return imagelist.get(adapterPosition);
    }

}
