package com.assetco.search.results;

import java.util.*;

public class Hotspot {
    private final List<Asset> members = new ArrayList<>();

    public void addMember(Asset asset) {
        members.add(asset);
    }

    public List<Asset> getMembers() {
        return Collections.unmodifiableList(members);
    }
}
