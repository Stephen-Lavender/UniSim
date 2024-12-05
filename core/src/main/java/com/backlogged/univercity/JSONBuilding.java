package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSONBuilding {
    Set<BuildingType> type;

    int level;
    String atlasRegion;
    List<Coord> tileCoverageOffsets;
    String info;


    public JSONBuilding(){}

    public void setType(BuildingType type){
        this.type.add(type);
    }

    public void setAtlasRegion(String atlasRegion){
        this.atlasRegion = atlasRegion;
    }

    public void setTileCoverageOffsets(List<Coord> tileCoverageOffsets){
        this.tileCoverageOffsets = tileCoverageOffsets;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public int getCapacity(){
        return level * 80;
    }
}
