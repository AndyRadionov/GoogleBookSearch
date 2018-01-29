package io.github.andyradionov.googlebookssearch.model.dto;

/**
 * @author Andrey Radionov
 */

public class Book {

    private BookSaleInfo saleInfo;
    private BookVolumeInfo volumeInfo;

    public BookSaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(BookSaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public BookVolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(BookVolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", volumeInfo=" + volumeInfo +
                '}';
    }
}
