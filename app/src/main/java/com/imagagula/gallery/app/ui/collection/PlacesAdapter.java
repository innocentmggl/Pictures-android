package com.imagagula.gallery.app.ui.collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imagagula.gallery.app.R;
import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlacesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<PictureResponse.Photo.Photos> photosList;

    public PlacesAdapter(List<PictureResponse.Photo.Photos> blogResponseList) {
        photosList = blogResponseList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapater_places_grid, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (photosList != null && photosList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (photosList != null && photosList.size() > 0) {
            return photosList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<PictureResponse.Photo.Photos> blogList) {
        photosList.addAll(blogList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onItemClicked(final int position);
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener{

        @BindView(R.id.picture)
        ImageView coverImageView;

        @BindView(R.id.caption)
        TextView caption;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            caption.setText("");
        }

        public void onBind(final int position) {
            super.onBind(position);

            final PictureResponse.Photo.Photos photo = photosList.get(position);


            if (photo.getUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(photo.getUrl())
                        .asBitmap()
                        .centerCrop()
                        .into(coverImageView);
            }

            if (photo.getTitle() != null) {
                caption.setText(photo.getTitle());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mCallback != null)
                        mCallback.onItemClicked(position);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.empty_message_tv)
        TextView empty;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }
    }
}
