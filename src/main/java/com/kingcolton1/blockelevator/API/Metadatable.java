package com.kingcolton1.blockelevator.API;

import java.util.List;

public interface Metadatable {
    /**
     * Sets a metadata value in the implementing object's metadata store.
     *
     * @param metadataKey A unique key to identify this metadata.
     * @param newMetadataValue The metadata value to apply.
     * @throws IllegalArgumentException If value is null, or the owning plugin
     *     is null
     */
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue);

    /**
     * Returns a list of previously set metadata values from the implementing
     * object's metadata store.
     *
     * @param metadataKey the unique metadata key being sought.
     * @return A list of values, one for each plugin that has set the
     *     requested value.
     */
    public List<MetadataValue> getMetadata(String metadataKey);

    /**
     * Tests to see whether the implementing object contains the given
     * metadata value in its metadata store.
     *
     * @param metadataKey the unique metadata key being queried.
     * @return the existence of the metadataKey within subject.
     */
    public boolean hasMetadata(String metadataKey);
}