package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashSet;
import java.util.Set;

public class BuildingInstance {
    private Set<BuildingType> type;

    public String atlasRegion;
    public Sprite sprite;

    public BuildingInstance(String atlasRegion){
        type = new HashSet<>();

        this.atlasRegion = atlasRegion;
    }

    public boolean addType(BuildingType type){
        return this.type.add(type);
    }
    public Set<BuildingType> getType(){
        return type;
    }

    public boolean isOfType(BuildingType type){
        return this.type.contains(type);
    }

}
