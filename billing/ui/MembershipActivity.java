package com.myinappbilling.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myinappbilling.R;
import com.myinappbilling.model.MembershipPlan;
import com.myinappbilling.repository.MembershipRepository;
import java.util.List;

/**
 * MembershipActivity displays available membership plans to the user.
 * It interacts with the MembershipRepository to fetch and manage plans.
 */
public class MembershipActivity extends AppCompatActivity {

    private static final String TAG = "MembershipActivity";
    private RecyclerView recyclerView;
    private MembershipAdapter adapter;
    private MembershipRepository membershipRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        recyclerView = findViewById(R.id.recyclerViewMembership);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        membershipRepository = new MembershipRepository();
        initializeMembershipPlans();
        displayMembershipPlans();
        logActivePlansCount();
        filterAndDisplaySubscriptions();
    }

    private void initializeMembershipPlans() {
        membershipRepository.addMembershipPlan(new MembershipPlan("Basic Plan", "$4.99", false, true));
        membershipRepository.addMembershipPlan(new MembershipPlan("Premium Plan", "$9.99", true, true));
        membershipRepository.addMembershipPlan(new MembershipPlan("Pro Plan", "$14.99", true, false));
    }

    private void displayMembershipPlans() {
        List<MembershipPlan> plans = membershipRepository.getAllMembershipPlans();
        if (plans.isEmpty()) {
            Toast.makeText(this, "No membership plans available.", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "No membership plans found in repository.");
            return;
        }

        adapter = new MembershipAdapter(plans, plan -> {
            Toast.makeText(this, "Selected: " + plan.getPlanName(), Toast.LENGTH_SHORT).show();
            // TODO: Handle user selecting a membership plan, possibly triggering a purchase flow
            activateSelectedPlan(plan);
        });

        recyclerView.setAdapter(adapter);
    }

    private void activateSelectedPlan(MembershipPlan selectedPlan) {
        try {
            membershipRepository.activateMembershipPlan(selectedPlan.getPlanName());
            Toast.makeText(this, selectedPlan.getPlanName() + " activated.", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Activation failed: " + e.getMessage());
            Toast.makeText(this, "Failed to activate plan.", Toast.LENGTH_SHORT).show();
        }
    }

    private void logActivePlansCount() {
        int activeCount = membershipRepository.getActiveMembershipPlans().size();
        Log.d(TAG, "Number of active membership plans: " + activeCount);
    }

    private void filterAndDisplaySubscriptions() {
        List<MembershipPlan> subscriptions = membershipRepository.getMembershipPlansBySubscriptionType(true);
        for (MembershipPlan plan : subscriptions) {
            Log.d(TAG, "Subscription Plan: " + plan.getPlanName());
        }
    }
}
