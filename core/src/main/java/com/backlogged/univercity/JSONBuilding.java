package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class JSONBuilding {
    BuildingType type;
    String atlasRegion;
    List<Coord> tileCoverageOffsets;
    String info;

    Sprite sprite;

    public JSONBuilding(){}

    public void setType(BuildingType type){
        this.type = type;
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
}
