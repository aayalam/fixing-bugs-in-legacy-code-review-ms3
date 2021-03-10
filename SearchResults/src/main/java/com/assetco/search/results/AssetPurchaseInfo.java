package com.assetco.search.results;

public class AssetPurchaseInfo {
    private final long timesShown;
    private final long timesPurchased;
    private final Money totalRevenue;
    private final Money totalRoyaltiesOwed;

    public AssetPurchaseInfo(
            long timesShown,
            long timesPurchased,
            Money totalRevenue,
            Money totalRoyaltiesOwed) {
        this.timesShown = timesShown;
        this.timesPurchased = timesPurchased;
        this.totalRevenue = totalRevenue;
        this.totalRoyaltiesOwed = totalRoyaltiesOwed;
    }

    public long getTimesShown() {
        return timesShown;
    }

    public long getTimesPurchased() {
        return timesPurchased;
    }

    public Money getTotalRevenue() {
        return totalRevenue;
    }

    public Money getTotalRoyaltiesOwed() {
        return totalRoyaltiesOwed;
    }
}
