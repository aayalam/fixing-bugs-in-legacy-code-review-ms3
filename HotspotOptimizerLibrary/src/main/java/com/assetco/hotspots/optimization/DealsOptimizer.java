package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import java.math.*;

import static com.assetco.search.results.HotspotKey.*;

class DealsOptimizer {
    public void optimize(SearchResults results, AssetAssessments assessments) {
        var highestRelationshipLevelOptional = results.getFound().stream().map(a -> a.getVendor().getRelationshipLevel())
                .max(Enum::compareTo);

        if (highestRelationshipLevelOptional.isPresent()) {
            var highestRelationshipLevel = highestRelationshipLevelOptional.get();

            for (var asset : results.getFound()) {
                switch (highestRelationshipLevel) {
                    case Silver:
                        switch (asset.getVendor().getRelationshipLevel()) {
                            case Silver:
                                if (assessments.isAssetDealEligible(asset) || hasProfitMargin(asset, "1", "2"))
                                    if (hasProfitMargin(asset, "7", "10") && hasTopLine(asset, "1500"))
                                        results.getHotspot(Deals).addMember(asset);
                                break;
                        }
                    case Gold:
                        switch (asset.getVendor().getRelationshipLevel()) {
                            case Gold:
                                if (assessments.isAssetDealEligible(asset) || hasProfitMargin(asset, "1", "2"))
                                    if (hasProfitMargin(asset, "7", "10"))
                                        if (hasTopLine(asset, "1000.00"))
                                            results.getHotspot(Deals).addMember(asset);
                                break;
                            case Silver:
                                if (assessments.isAssetDealEligible(asset) && hasProfitMargin(asset, "1", "2") && hasTopLine(asset, "1500"))
                                    results.getHotspot(Deals).addMember(asset);
                                break;
                        }
                        break;
                    case Partner:
                        switch (asset.getVendor().getRelationshipLevel()) {
                            case Partner:
                                if (hasProfitMargin(asset, "7", "10"))
                                    results.getHotspot(Deals).addMember(asset);
                                break;
                            case Gold:
                                if (assessments.isAssetDealEligible(asset) && hasProfitMargin(asset, "1", "2"))
                                    if (hasTopLine(asset, "1000"))
                                        results.getHotspot(Deals).addMember(asset);
                                break;
                            case Silver:
                                if (assessments.isAssetDealEligible(asset))
                                    if (hasTopLine(asset, "10000"))
                                        if (hasProfitMargin(asset, "1", "4"))
                                            results.getHotspot(Deals).addMember(asset);
                                break;
                        }
                        break;
                }
            }
        }
    }

    private boolean hasTopLine(Asset asset, String amount) {
        return asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount().compareTo(new BigDecimal(amount)) >= 0;
    }

    private boolean hasProfitMargin(Asset asset, String denominator, String numerator) {
        var scaledRevenue = asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount().multiply(new BigDecimal(denominator));
        var scaledRoyalties = asset.getPurchaseInfoLast30Days().getTotalRoyaltiesOwed().getAmount().multiply(new BigDecimal(numerator));
        return scaledRevenue.compareTo(scaledRoyalties) >= 0;
    }
}
