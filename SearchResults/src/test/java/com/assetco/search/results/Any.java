package com.assetco.search.results;

import java.math.*;
import java.net.*;
import java.util.*;

class Any {

    private static Random random = new Random();

    static AssetVendorRelationshipLevel relationshipLevel() {
        return anyEnumerationValue(AssetVendorRelationshipLevel.class);
    }

    private static <T> T anyEnumerationValue(Class<T> clazz) {
        var values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

    static AssetTopic anyTopic() {
        return new AssetTopic(string(), string());
    }

    static AssetPurchaseInfo assetPurchaseInfo() {
        return new AssetPurchaseInfo(anyLong(), anyLong(), money(), money());
    }

    static Money money() {
        return new Money(new BigDecimal(anyLong()));
    }

    static URI URI() {
        return URI.create("https://" + string());
    }

    static String string() {
        return UUID.randomUUID().toString();
    }

    static long anyLong() {
        return random.nextInt();
    }

    static AssetVendor vendor() {
        return new AssetVendor(string(), string(), relationshipLevel(), anyLong());
    }

    public static Asset asset() {
        return new Asset(string(), string(), URI(), URI(), assetPurchaseInfo(), assetPurchaseInfo(), setOfTopics(), vendor());
    }

    private static List<AssetTopic> setOfTopics() {
        var result = new ArrayList<AssetTopic>();
        for (var count = 1 + random.nextInt(4); count > 0; --count)
            result.add(anyTopic());

        return result;
    }

    public static HotspotKey hotspotKey() {
        return anyEnumerationValue(HotspotKey.class);
    }
}
