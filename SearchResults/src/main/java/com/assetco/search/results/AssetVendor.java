package com.assetco.search.results;

public class AssetVendor {
    private final String id;
    private final String displayName;
    private final AssetVendorRelationshipLevel relationshipLevel;
    private final float royaltyRate;

    public AssetVendor(
            String id,
            String displayName,
            AssetVendorRelationshipLevel relationshipLevel,
            float royaltyRate) {
        this.id = id;
        this.displayName = displayName;
        this.relationshipLevel = relationshipLevel;
        this.royaltyRate = royaltyRate;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AssetVendorRelationshipLevel getRelationshipLevel() {
        return relationshipLevel;
    }

    public float getRoyaltyRate() {
        return royaltyRate;
    }
}
