package com.assetco.hotspots.optimization;

import com.assetco.hotspots.optimization.*;
import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.*;
import static org.junit.jupiter.api.Assertions.*;

public class BugsTests {
    private final int maximumShowcaseItems = 5;
    private SearchResults searchResults;
    private AssetVendor partnerVendor;
    private SearchResultHotspotOptimizer optimizer;

    @BeforeEach
    public void setUp() {
        optimizer = new SearchResultHotspotOptimizer();
        searchResults = new SearchResults();
        partnerVendor = makeVendor(Partner);
    }

    @Test
    public void precedingPartnerWithLongTrailingAssetsWins() {
        var previouslyMissing = givenAssetInResultsWithVendor(partnerVendor);
        givenAssetInResultsWithVendor(makeVendor(Partner));
        var temporary = givenAssetsInResultsWithVendor(maximumShowcaseItems - 1, partnerVendor);

        var expected = new ArrayList<Asset>();
        expected.add(previouslyMissing);
        expected.addAll(temporary);

        whenOptimize();

        thenHotspotHasExactly(Showcase, expected);
    }


    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return new AssetVendor("anything", "anything", relationshipLevel, 1);
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
        Asset result = getAsset(vendor);
        searchResults.addFound(result);
        return result;
    }

    private Asset getAsset(AssetVendor vendor) {
        return new Asset("anything", "anything", null, null, getPurchaseInfo(), getPurchaseInfo(), new ArrayList<>(), vendor);
    }

    private AssetPurchaseInfo getPurchaseInfo() {
        return new AssetPurchaseInfo(0, 0,
                new Money(new BigDecimal("0")),
                new Money(new BigDecimal("0")));
    }

    private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {
        Assertions.assertArrayEquals(expected.toArray(), searchResults.getHotspot(hotspotKey).getMembers().toArray());
    }

    private ArrayList<Asset> givenAssetsInResultsWithVendor(int count, AssetVendor vendor) {
        var result = new ArrayList<Asset>();
        for (var i = 0; i < count; ++i) {
            result.add(givenAssetInResultsWithVendor(vendor));
        }
        return result;
    }

    private void whenOptimize() {
        optimizer.optimize(searchResults);
    }

    private void thenHotspotDoesNotHave(HotspotKey key, Asset... forbidden) {
        for (var asset : forbidden)
            assertFalse(searchResults.getHotspot(key).getMembers().contains(asset));
    }
}
