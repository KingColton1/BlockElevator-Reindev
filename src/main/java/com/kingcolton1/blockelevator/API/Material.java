package com.kingcolton1.blockelevator.API;

import java.lang.reflect.Constructor;
import java.util.Map;
import com.google.common.collect.Maps;

public enum Material {
    air(0, 0),
    stone(1),
    grass(2),
    dirt(3),
    cobblestone(4),
    blockGold(41)
    ;

    private final int id;
    private final Constructor<? extends MaterialData> ctor;
    private static Material[] byId = new Material[383];
    private final static Map<String, Material> BY_NAME = Maps.newHashMap();
    private final int maxStack;
    private final short durability;

    private Material(final int id) {
        this(id, 64);
    }

    private Material(final int id, final int stack) {
        this(id, stack, MaterialData.class);
    }

    private Material(final int id, final int stack, final int durability) {
        this(id, stack, durability, MaterialData.class);
    }

    private Material(final int id, final Class<? extends MaterialData> data) {
        this(id, 64, data);
    }

    private Material(final int id, final int stack, final Class<? extends MaterialData> data) {
        this(id, stack, 0, data);
    }

    private Material(final int id, final int stack, final int durability, final Class<? extends MaterialData> data) {
        this.id = id;
        this.durability = (short) durability;
        this.maxStack = stack;

        try {
            this.ctor = data.getConstructor(int.class, byte.class);
        } catch (NoSuchMethodException ex) {
            throw new AssertionError(ex);
        } catch (SecurityException ex) {
            throw new AssertionError(ex);
        }
    }

    @Deprecated
    public int getId() {
        return id;
    }

    public int getMaxStackSize() {
        return maxStack;
    }

    public short getMaxDurability() {
        return durability;
    }

    public Class<? extends MaterialData> getData() {
        return ctor.getDeclaringClass();
    }

    @Deprecated
    public MaterialData getNewData(final byte raw) {
        try {
            return ctor.newInstance(id, raw);
        } catch (InstantiationException ex) {
            final Throwable t = ex.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw new AssertionError(t);
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    public boolean isBlock() {
        return id < 256;
    }

    @Deprecated
    public static Material getMaterial(final int id) {
        if (byId.length > id && id >= 0) {
            return byId[id];
        } else {
            return null;
        }
    }

    public static Material getMaterial(final String name) {
        return BY_NAME.get(name);
    }
    
    static {
        for (Material material : values()) {
            if (byId.length > material.id) {
                byId[material.id] = material;
            } else {
                byId = Java15Compat.Arrays_copyOfRange(byId, 0, material.id + 2);
                byId[material.id] = material;
            }
            BY_NAME.put(material.name(), material);
        }
    }

    public boolean isSolid() {
        if (!isBlock() || id == 0) {
            return false;
        }
        switch (this) {
            case stone:
            case grass:
            case dirt:
            case cobblestone:
            case blockGold:
                return true;
            default:
                return false;
        }
    }
}