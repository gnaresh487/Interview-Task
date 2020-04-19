package com.naresh.interviewassignment.ui.main_screen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.HomeListBinding;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;
import com.naresh.interviewassignment.util.NetworkState;

public class HomeAdapter extends PagedListAdapter<HomeModel, HomeAdapter.ViewHolder> {
    private NetworkState networkState;

    public HomeAdapter() {
        super(HomeModel.DIFF_CALLBACK);
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
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private HomeListBinding homeListBinding;

        public ViewHolder(HomeListBinding homeListBinding) {
            super(homeListBinding.getRoot());
            this.homeListBinding = homeListBinding;
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
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
