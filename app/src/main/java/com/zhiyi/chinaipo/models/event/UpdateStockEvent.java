package com.zhiyi.chinaipo.models.event;

import com.zhiyi.chinaipo.models.entity.StockPriceEntity;

public class UpdateStockEvent {
    public UpdateStockEvent(String stockCode, StockPriceEntity stockPrice) {
        this.stockCode = stockCode;
        this.stockPrice = stockPrice;
    }

    private String stockCode;
    private String stockPrices;
    private StockPriceEntity stockPrice;

    public UpdateStockEvent(String stockCode, String stockPrices) {
        this.stockCode = stockCode;
        this.stockPrices = stockPrices;
    }

    public StockPriceEntity getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(StockPriceEntity stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
