package com.myinappbilling.coordinator.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myinappbilling.R;
import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.service.CoordinatorService;
import com.myinappbilling.coordinator.viewmodel.CoordinatorViewModel;
import com.myinappbilling.coordinator.viewmodel.CoordinatorViewModelFactory;

import java.util.List;

public class CoordinatorListActivity extends AppCompatActivity {

    private CoordinatorViewModel viewModel;
    private CoordinatorAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_list);

        recyclerView = findViewById(R.id.recyclerViewCoordinators);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoordinatorAdapter();
        recyclerView.setAdapter(adapter);

        CoordinatorService coordinatorService = new CoordinatorService();
        viewModel = new ViewModelProvider(this, new CoordinatorViewModelFactory(coordinatorService)).get(CoordinatorViewModel.class);

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCoordinatorList().observe(this, coordinators -> {
            adapter.setCoordinatorList(coordinators);
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAddCoordinatorClick(View view) {
        // TODO: Launch add coordinator UI
    }

    public void onRefreshClick(View view) {
        viewModel.loadCoordinators();
    }

    private class CoordinatorAdapter extends RecyclerView.Adapter<CoordinatorViewHolder> {

        private List<Coordinator> coordinatorList;

        public void setCoordinatorList(List<Coordinator> list) {
            this.coordinatorList = list;
            notifyDataSetChanged();
        }

        @Override
        public CoordinatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_coordinator, parent, false);
            return new CoordinatorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CoordinatorViewHolder holder, int position) {
            Coordinator coordinator = coordinatorList.get(position);
            holder.bind(coordinator);
        }

        @Override
        public int getItemCount() {
            return coordinatorList != null ? coordinatorList.size() : 0;
        }
    }
} 
