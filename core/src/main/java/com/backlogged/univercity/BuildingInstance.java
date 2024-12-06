package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashSet;
import java.util.Set;

public class BuildingInstance {
    private Set<BuildingType> type;

    public String atlasRegion;
    private Sprite sprite;

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

    public void setSprite(TextureAtlas textureAtlas, float unitScale){
        sprite = textureAtlas.createSprite(atlasRegion);
        sprite.setScale(unitScale);
        sprite.setOrigin(0, 0);
    }
    public Sprite getSprite(){
        return sprite;
    }

}
