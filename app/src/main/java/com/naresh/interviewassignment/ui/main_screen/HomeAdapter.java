package com.naresh.interviewassignment.ui.main_screen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.HomeListBinding;
import com.naresh.interviewassignment.ui.details_screen.DetailsActivity;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;
import com.naresh.interviewassignment.util.NetworkState;

public class HomeAdapter extends PagedListAdapter<HomeModel, HomeAdapter.ViewHolder> {
    private NetworkState networkState;
    private Context context;

    HomeAdapter(Context context) {
        super(HomeModel.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        HomeListBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.home_list, viewGroup, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeModel homeModel = getItem(position);
        holder.homeListBinding.setModel(homeModel);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            if (homeModel != null) {
                intent.putExtra("project_url", homeModel.getHtmlUrl());
                intent.putExtra("description", homeModel.getDescription());
                intent.putExtra("name", homeModel.getName());
                intent.putExtra("contributors_url", homeModel.getContributorsUrl());
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return 1;
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private HomeListBinding homeListBinding;

        ViewHolder(HomeListBinding homeListBinding) {
            super(homeListBinding.getRoot());
            this.homeListBinding = homeListBinding;
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

}
