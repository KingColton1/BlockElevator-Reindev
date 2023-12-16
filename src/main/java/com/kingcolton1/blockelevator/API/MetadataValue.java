package com.kingcolton1.blockelevator.API;

public interface MetadataValue {

    /**
     * Fetches the value of this metadata item.
     *
     * @return the metadata value.
     */
    public Object value();

    /**
     * Attempts to convert the value of this metadata item into an int.
     *
     * @return the value as an int.
     */
    public int asInt();

    /**
     * Attempts to convert the value of this metadata item into a float.
     *
     * @return the value as a float.
     */
    public float asFloat();

    /**
     * Attempts to convert the value of this metadata item into a double.
     *
     * @return the value as a double.
     */
    public double asDouble();

    /**
     * Attempts to convert the value of this metadata item into a long.
     *
     * @return the value as a long.
     */
    public long asLong();

    /**
     * Attempts to convert the value of this metadata item into a short.
     *
     * @return the value as a short.
     */
    public short asShort();

    /**
     * Attempts to convert the value of this metadata item into a byte.
     *
     * @return the value as a byte.
     */
    public byte asByte();

    /**
     * Attempts to convert the value of this metadata item into a boolean.
     *
     * @return the value as a boolean.
     */
    public boolean asBoolean();

    /**
     * Attempts to convert the value of this metadata item into a string.
     *
     * @return the value as a string.
     */
    public String asString();

    /**
     * Invalidates this metadata item, forcing it to recompute when next
     * accessed.
     */
    public void invalidate();
}