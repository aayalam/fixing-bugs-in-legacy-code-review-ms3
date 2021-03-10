package com.assetco.search.results;

public class AssetTopic {
    private final String id;
    private final String displayName;

    public AssetTopic(String id, String displayName) {

        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
