package com.myinappbilling.payment.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.payment.R;
import com.myinappbilling.payment.model.PaymentDetails;
import com.myinappbilling.payment.viewmodel.PaymentDetailsViewModel;

/**
 * Activity for displaying and managing payment details form.
 */
public class PaymentDetailsActivity extends AppCompatActivity {

    private PaymentDetailsViewModel viewModel;
    private EditText etFirstName, etLastName, etCardNumber, etCvv;
    private EditText etEmail, etAddress, etCity, etPostalCode, etCountry, etProvince, etIdentityCardNumber;
    private Button btnSave, btnClear, btnAutoFill;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        viewModel = new ViewModelProvider(this).get(PaymentDetailsViewModel.class);

        initViews();
        observeViewModel();
        setupListeners();
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCvv = findViewById(R.id.etCvv);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etCountry = findViewById(R.id.etCountry);
        etProvince = findViewById(R.id.etProvince);
        etIdentityCardNumber = findViewById(R.id.etIdentityCardNumber);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        btnAutoFill = findViewById(R.id.btnAutoFill);
        progressBar = findViewById(R.id.progressBar);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnSave.setEnabled(!isLoading);
            btnClear.setEnabled(!isLoading);
            btnAutoFill.setEnabled(!isLoading);
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> savePaymentDetails());
        btnClear.setOnClickListener(v -> clearForm());
        btnAutoFill.setOnClickListener(v -> autoFillDemoData());
    }

    private void savePaymentDetails() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String cardNumber = etCardNumber.getText().toString().trim();
        String cvv = etCvv.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String postalCode = etPostalCode.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String province = etProvince.getText().toString().trim();
        String identityCardNumber = etIdentityCardNumber.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty()
                || email.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty()
                || country.isEmpty() || province.isEmpty() || identityCardNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setFirstName(firstName);
        paymentDetails.setLastName(lastName);
        paymentDetails.setCardNumber(cardNumber);
        paymentDetails.setCvv(cvv);
        paymentDetails.setEmail(email);
        paymentDetails.setAddress(address);
        paymentDetails.setCity(city);
        paymentDetails.setPostalCode(postalCode);
        paymentDetails.setCountry(country);
        paymentDetails.setProvince(province);
        paymentDetails.setIdentityCardNumber(identityCardNumber);

        viewModel.savePaymentDetails(paymentDetails);
    }

    private void clearForm() {
        etFirstName.setText("");
        etLastName.setText("");
        etCardNumber.setText("");
        etCvv.setText("");
        etEmail.setText("");
        etAddress.setText("");
        etCity.setText("");
        etPostalCode.setText("");
        etCountry.setText("");
        etProvince.setText("");
        etIdentityCardNumber.setText("");
    }

    private void autoFillDemoData() {
        etFirstName.setText("John");
        etLastName.setText("Doe");
        etCardNumber.setText("4111111111111111");
        etCvv.setText("123");
        etEmail.setText("john.doe@example.com");
        etAddress.setText("123 Main Street");
        etCity.setText("Metropolis");
        etPostalCode.setText("12345");
        etCountry.setText("USA");
        etProvince.setText("NY");
        etIdentityCardNumber.setText("ID123456789");
    }
}
