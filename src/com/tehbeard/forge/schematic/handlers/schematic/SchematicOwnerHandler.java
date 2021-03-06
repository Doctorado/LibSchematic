package com.tehbeard.forge.schematic.handlers.schematic;

import net.minecraft.nbt.NBTTagCompound;

import com.tehbeard.forge.schematic.SchematicFile;
import com.tehbeard.forge.schematic.handlers.SchematicDataHandler;

/**
 * Handlers that implement this can change the owner of a block. This is
 * primarily for mod blocks that have a concept of ownership (safes, Forestry
 * machines)
 * 
 * @author James
 * 
 */
public interface SchematicOwnerHandler extends SchematicDataHandler {

    /**
     * Changes the owner of a block to the one specified This is useful for
     * pasting in say a collection of safes, or lockable machinery and changing
     * the owner to the person the paste is being done for.
     * 
     * @param schematic
     * @param x
     * @param y
     * @param z
     * @param blockId
     * @param metadata
     * @param newOwner
     */
    public void setOwner(SchematicFile schematic, int x, int y, int z,
            int blockId, int metadata, NBTTagCompound entity, String newOwner);

}
