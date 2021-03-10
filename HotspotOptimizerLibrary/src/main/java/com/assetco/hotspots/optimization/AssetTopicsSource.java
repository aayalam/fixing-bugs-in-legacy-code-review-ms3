package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

public interface AssetTopicsSource {
    Iterable<AssetTopic> getTopics();
}
