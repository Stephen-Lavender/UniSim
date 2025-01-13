package com.backlogged.univercity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashSet;
import java.util.Set;

public class BuildingInstance {
    private Set<BuildingType> type;

    private Sprite sprite;

    public BuildingInstance(){
        type = new HashSet<>();

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

    public void setSprite(Texture texture, float unitScale) {
        this.sprite = new Sprite(texture);
        this.sprite.setScale(unitScale);
        this.sprite.setOrigin(0, 0);
    }

    public Sprite getSprite(){
        return sprite;
    }

}
