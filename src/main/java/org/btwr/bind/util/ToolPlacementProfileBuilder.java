package org.btwr.bind.util;

public final class ToolPlacementProfileBuilder {

    private BoundingBoxData boundingBox;
    private VisualOffsetData visualOffsets;
    private OutlineOffsets outlineOffsets;

    private ToolPlacementProfileBuilder() {}

    public static ToolPlacementProfileBuilder create() {
        return new ToolPlacementProfileBuilder();
    }

    public ToolPlacementProfileBuilder boundingBox(float minHeight, float maxHeight, float minWidth, float maxWidth) {
        this.boundingBox = new BoundingBoxData(minHeight, maxHeight, minWidth, maxWidth);
        return this;
    }

    public ToolPlacementProfileBuilder visualOffsets(float pitch, float verticalOffset, float horizontalOffset) {
        this.visualOffsets = new VisualOffsetData(pitch, verticalOffset, horizontalOffset);
        return this;
    }

    public ToolPlacementProfileBuilder outlineOffsets(float wallWidth, float floorHeight, float ceilingHeight) {
        this.outlineOffsets = new OutlineOffsets(wallWidth, floorHeight, ceilingHeight);
        return this;
    }

    public ToolPlacementProfile build() {
        return new ToolPlacementProfile(boundingBox, visualOffsets, outlineOffsets);
    }

}