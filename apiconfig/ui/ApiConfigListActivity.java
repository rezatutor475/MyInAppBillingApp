package com.myinappbilling.apiconfig.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myinappbilling.R;
import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.viewmodel.ApiConfigViewModel;
import com.myinappbilling.apiconfig.ui.adapter.ApiConfigAdapter;

import java.util.Comparator;

/**
 * Activity to display the list of API Configurations.
 */
public class ApiConfigListActivity extends AppCompatActivity implements ApiConfigAdapter.OnItemClickListener {

    private ApiConfigViewModel viewModel;
    private ApiConfigAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_config_list);

        viewModel = new ViewModelProvider(this).get(ApiConfigViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewApiConfigs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApiConfigAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getApiConfigurations().observe(this, apiConfigurations -> {
            adapter.submitList(apiConfigurations);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_api_config_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search API Configurations...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchConfigurations(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchConfigurations(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_name:
                sortByName();
                return true;
            case R.id.action_sort_date:
                sortByDate();
                return true;
            case R.id.action_refresh:
                viewModel.refreshConfigurations();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByName() {
        viewModel.getApiConfigurations().getValue().sort(Comparator.comparing(ApiConfiguration::getName));
        adapter.notifyDataSetChanged();
    }

    private void sortByDate() {
        viewModel.getApiConfigurations().getValue().sort(Comparator.comparing(ApiConfiguration::getCreatedAt));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(ApiConfiguration configuration) {
        Toast.makeText(this, "Selected: " + configuration.getName(), Toast.LENGTH_SHORT).show();
        // Optionally navigate to ApiConfigDetailsActivity
    }
}
