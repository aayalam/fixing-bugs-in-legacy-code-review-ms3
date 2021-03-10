package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import java.math.*;

import static com.assetco.search.results.HotspotKey.HighValue;

class SalesInfoBasedOptimizer {
    public void optimize(SearchResults searchResults) {
        for (var asset : searchResults.getFound()) {
            if (searchResults.getHotspot(HighValue).getMembers().contains(asset))
                break;

            var delta = asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount()
                    .subtract(asset.getPurchaseInfoLast30Days().getTotalRoyaltiesOwed().getAmount());

            if (asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount().compareTo(new BigDecimal("5000.00")) >= 0
                    && delta.compareTo(new BigDecimal("4000.00")) >= 0)
                searchResults.getHotspot(HighValue).addMember(asset);
        }


        for (var asset : searchResults.getFound()) {
            if (searchResults.getHotspot(HighValue).getMembers().size() > 0)
                return;

            if (asset.getPurchaseInfoLast24Hours().getTimesShown() >= 1000 &&
                    asset.getPurchaseInfoLast24Hours().getTimesPurchased() * 200 >= asset.getPurchaseInfoLast24Hours().getTimesShown())
                searchResults.getHotspot(HighValue).addMember(asset);
        }

        for (var asset : searchResults.getFound()) {
            if (asset.getPurchaseInfoLast30Days().getTimesShown() >= 50000 &&
                    asset.getPurchaseInfoLast30Days().getTimesPurchased() * 125 >= asset.getPurchaseInfoLast30Days().getTimesShown())
                searchResults.getHotspot(HighValue).addMember(asset);
        }
    }
}
