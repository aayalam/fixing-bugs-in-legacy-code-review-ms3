package com.assetco.search.results;

import java.net.*;
import java.util.*;

public class Asset {
    private final Object id;
    private final String title;
    private final URI thumbnailURI;
    private final URI previewURI;
    private final AssetPurchaseInfo purchaseInfoLast30Days;
    private final AssetPurchaseInfo purchaseInfoLast24Hours;
    private final List<AssetTopic> topics;
    private final AssetVendor vendor;

    public Asset(
            Object id,
            String title,
            URI thumbnailURI,
            URI previewURI,
            AssetPurchaseInfo purchaseInfoLast30Days,
            AssetPurchaseInfo purchaseInfoLast24Hours,
            List<AssetTopic> topics,
            AssetVendor vendor) {
        this.id = id;
        this.title = title;
        this.thumbnailURI = thumbnailURI;
        this.previewURI = previewURI;
        this.purchaseInfoLast30Days = purchaseInfoLast30Days;
        this.purchaseInfoLast24Hours = purchaseInfoLast24Hours;
        this.topics = topics;
        this.vendor = vendor;
    }

    public Object getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public URI getThumbnailURI() {
        return thumbnailURI;
    }

    public URI getPreviewURI() {
        return previewURI;
    }

    public AssetPurchaseInfo getPurchaseInfoLast30Days() {
        return purchaseInfoLast30Days;
    }

    public AssetPurchaseInfo getPurchaseInfoLast24Hours() {
        return purchaseInfoLast24Hours;
    }

    public List<AssetTopic> getTopics() {
        return Collections.unmodifiableList(topics);
    }

    public AssetVendor getVendor() {
        return vendor;
    }
}
