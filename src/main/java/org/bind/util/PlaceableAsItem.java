package org.bind.util;

public interface PlaceableAsItem {
	/**
	 * All of these orientation values are from the perspective of when the item is set into the floor.
	 * For example, "Vertical offset" means how much to shift the rendering upward/downward when set into the floor.
	 * "Horizontal offset" means forward/backward offset when set into the floor.
	 *
	 */

	default float bind$getVisualVerticalOffsetPixels() {
		return 0;
	}

	default float bind$getVisualHorizontalOffsetPixels() {
		return 0;
	}

	default float bind$getVisualPitchDegrees() {
		return 0;
	}

	default float bind$getBlockBoundingBoxHeightPixels() {
		return 16f;
	}

	default float bind$getBlockBoundingBoxWidthPixels() {
		return 16f;
	}
}