package io.github.andyradionov.googlebookssearch.model.dto;

/**
 * @author Andrey Radionov
 */

public class BookSaleInfo {

    private RetailPrice retailPrice;

    public boolean hasPrice() {
        return retailPrice != null
                && retailPrice.amount > 0
                && retailPrice.currencyCode != null;
    }

    public double getAmount() {
        return retailPrice.amount;
    }

    public String getCurrencyCode() {
        return retailPrice.currencyCode;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

    private class RetailPrice {
        private double amount;
        private String currencyCode;
    }
}
