package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import java.util.*;
import java.util.function.*;

import static com.assetco.search.results.HotspotKey.*;

class TopicsBasedOptimizer {
    public boolean optimize(SearchResults searchResults, AssetTopicsSource hotTopicsSource) {
        int showcased = 0;
        var hotTopics = new ArrayList<AssetTopic>();
        AssetTopic hotTopic = null;

        Iterator<Asset> iterator = searchResults.getFound().iterator();
        var showcaseAssets = new ArrayList<Asset>();

        while (iterator.hasNext()) {
            Asset asset = iterator.next();

            if (hotTopics.size() == 0)
                hotTopicsSource.getTopics().forEach(hotTopics::add);

            if (getHottestTopicIn(asset, hotTopics) != null)
                searchResults.getHotspot(Highlight).addMember(asset);

            if (hotTopic == null)
                hotTopic = getHottestTopicIn(asset, hotTopics);

            AssetTopic assetHotTopic = getHottestTopicIn(asset, hotTopics);
            if (assetHotTopic != null) {
                if (isHotterTopic(assetHotTopic, hotTopic, hotTopics)) {
                    for (var surplusAsset : showcaseAssets)
                        searchResults.getHotspot(TopPicks).addMember(surplusAsset);
                    showcaseAssets.clear();
                    hotTopic = assetHotTopic;
                    showcased = 0;
                }

                if (assetHotTopic == hotTopic)
                    showcaseAssets.add(asset);
            } else {
                continue;
            }

            if (++showcased > 2)
                break;
        }

        var showcase = searchResults.getHotspot(Showcase);
        boolean result = false;
        for (var asset : showcaseAssets) {
            showcase.addMember(asset);
            result = true;
        }

        while (iterator.hasNext()) {
            Asset asset = iterator.next();

            if (asset.getTopics().stream().anyMatch(getAssetTopicPredicate(hotTopic)))
            {
                showcase.addMember(asset);
                if (++showcased >= 5)
                    break;
            }
        }

        while (iterator.hasNext()) {
            Asset asset = iterator.next();

            if (getHottestTopicIn(asset, hotTopics) != null)
                searchResults.getHotspot(TopPicks).addMember(asset);
        }

        return result;
    }

    private boolean isHotterTopic(AssetTopic left, AssetTopic right, ArrayList<AssetTopic> hotTopics) {
        var canonicalLeft = hotTopics.stream().filter(getAssetTopicPredicate(left)).findFirst().get();
        var canonicalRight = hotTopics.stream().filter(getAssetTopicPredicate(right)).findFirst().get();

        return hotTopics.indexOf(canonicalLeft) < hotTopics.indexOf(canonicalRight);
    }

    private AssetTopic getHottestTopicIn(Asset asset, ArrayList<AssetTopic> hotTopics) {
        for (var topic : hotTopics) {
            if (asset.getTopics().stream().anyMatch(getAssetTopicPredicate(topic)))
                return topic;
        }

        return null;
    }

    private Predicate<AssetTopic> getAssetTopicPredicate(AssetTopic topic) {
        if (topic == null)
            return assetTopic -> false;

        return assetTopic -> topicsEquivalent(topic, assetTopic);
    }

    private boolean topicsEquivalent(AssetTopic topic, AssetTopic assetTopic) {
        return Objects.equals(topic.getId(), assetTopic.getId());
    }
}
