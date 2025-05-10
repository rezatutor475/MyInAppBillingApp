package com.myinappbilling.creditcardreceipt.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.model.ReceiptItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for handling operations related to Receipts.
 */
public class ReceiptUtils {

    private static final String TAG = "ReceiptUtils";

    /**
     * Formats a given amount to the local currency format.
     *
     * @param amount the amount to format
     * @return formatted currency string
     */
    public static String formatCurrency(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return format.format(amount);
    }

    /**
     * Formats a Date object to a readable date string.
     *
     * @param date the date to format
     * @return formatted date string
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Creates a shareable file containing receipt details.
     *
     * @param context the application context
     * @param receipt the receipt to share
     * @return Uri of the created file
     */
    public static Uri createShareableReceiptFile(Context context, Receipt receipt) {
        File file = new File(context.getCacheDir(), "shared_receipt.txt");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            String content = buildReceiptContent(receipt);
            fos.write(content.getBytes());
            fos.flush();
        } catch (IOException e) {
            Log.e(TAG, "Error writing receipt to file", e);
            return null;
        }

        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }

    /**
     * Builds a string representation of a receipt.
     *
     * @param receipt the receipt
     * @return string content
     */
    private static String buildReceiptContent(Receipt receipt) {
        StringBuilder builder = new StringBuilder();
        builder.append("Merchant: ").append(receipt.getMerchantName()).append("\n");
        builder.append("Date: ").append(formatDate(receipt.getDate())).append("\n");
        builder.append("Total: ").append(formatCurrency(receipt.getTotalAmount())).append("\n");
        builder.append("Items:\n");
        for (ReceiptItem item : receipt.getItems()) {
            builder.append("- ")
                    .append(item.getName())
                    .append(" (x")
                    .append(item.getQuantity())
                    .append("): ")
                    .append(formatCurrency(item.getPrice()))
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Shares the receipt via available apps.
     *
     * @param context the context
     * @param receipt the receipt
     */
    public static void shareReceipt(Context context, Receipt receipt) {
        Uri fileUri = createShareableReceiptFile(context, receipt);
        if (fileUri != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share Receipt via"));
        }
    }

    /**
     * Sorts a list of receipts by date.
     *
     * @param receipts the list of receipts
     * @param ascending true for ascending, false for descending
     */
    public static void sortReceiptsByDate(List<Receipt> receipts, boolean ascending) {
        Collections.sort(receipts, (r1, r2) -> ascending ? r1.getDate().compareTo(r2.getDate()) : r2.getDate().compareTo(r1.getDate()));
    }

    /**
     * Sorts a list of receipts by total amount.
     *
     * @param receipts the list of receipts
     * @param ascending true for ascending, false for descending
     */
    public static void sortReceiptsByAmount(List<Receipt> receipts, boolean ascending) {
        Collections.sort(receipts, (r1, r2) -> ascending ? Double.compare(r1.getTotalAmount(), r2.getTotalAmount()) : Double.compare(r2.getTotalAmount(), r1.getTotalAmount()));
    }

    /**
     * Filters receipts by merchant name.
     *
     * @param receipts the list of receipts
     * @param query the merchant name query
     * @return filtered list of receipts
     */
    public static List<Receipt> filterReceiptsByMerchant(List<Receipt> receipts, String query) {
        if (query == null || query.isEmpty()) {
            return receipts;
        }
        query = query.toLowerCase();
        List<Receipt> filtered = new java.util.ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.getMerchantName().toLowerCase().contains(query)) {
                filtered.add(receipt);
            }
        }
        return filtered;
    }
}
