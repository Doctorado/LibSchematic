package com.tehbeard.forge.schematic.compat;

import java.lang.reflect.Field;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import appeng.api.me.tiles.IGridTileEntity;
import appeng.common.AppEng;
import appeng.common.AutoID;
import appeng.common.base.AppEngTile;
import appeng.me.basetiles.TileME;
import appeng.me.tile.TileController;
import appeng.me.tile.TileCraftingTerminal;
import appeng.me.tile.TileTerminal;

import com.tehbeard.forge.schematic.SchematicDataRegistry;
import com.tehbeard.forge.schematic.SchematicFile;
import com.tehbeard.forge.schematic.data.SchematicRotationHandler;
import com.tehbeard.forge.schematic.data.SimpleRotationHandler;
import com.tehbeard.forge.schematic.data.VanillaRotations;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "libschematic.compat.appeng",name="LibSchematic::AppliedEnergistics",version="1.0",dependencies="after:AppliedEnergistics")
public class LibSchematicAppEng {
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		if(Loader.isModLoaded("AppliedEnergistics")){
			SchematicDataRegistry.logger().info("Installing AppliedEnergistics handler");
			
			SimpleRotationHandler rotatron = new SimpleRotationHandler() {

				@Override
				public int rotateData(SchematicFile schematic, int x, int y, int z,
						int blockId, int metadata, int rotations) {

					return metadata;
				}

				@Override
				public void rotateTileEntity(SchematicFile schematic, int x, int y,
						int z, int blockId, int metadata, TileEntity tileEntity,int rotations) {

					if(tileEntity == null){return;}
					if(tileEntity instanceof AppEngTile){
						try{
							SchematicDataRegistry.logger().info("AE: " + tileEntity.getClass().getName());
							Field f = tileEntity.getClass().getField("orientation");
							f.setAccessible(true);
							ForgeDirection dir = (ForgeDirection) f.get(tileEntity);
							dir = fdRotate(dir, rotations);
							f.set(tileEntity, dir);
						}catch(NoSuchFieldException e){
							SchematicDataRegistry.logger().info("NO FIELD FOUND");
						} 
						catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}


					//DO NOT USE UNLESS ABOVE CODE BREAKS
					/*
                	//ME Controller
                	if(tileEntity instanceof TileController){
                		((TileController)tileEntity).orientation = fdRotate(((TileController)tileEntity).orientation,rotations);
                	}

                	//ME Access Terminal
                	if(tileEntity instanceof TileTerminal){
                		((TileTerminal)tileEntity).
                		//= fdRotate(((TileTerminal)tileEntity).orientation,rotations);
                	}
					 */



					//facing = VanillaRotations.CONTAINER_PISTON.rotateData(schematic, x, y, z, blockId, facing, rotations);
					//teic.setFacing((byte) (facing & 0xFF));

				}


			};
			
			SchematicDataRegistry.setHandler(AppEng.getInstance().config.blockMulti.get(),rotatron);
			SchematicDataRegistry.setHandler(AppEng.getInstance().config.blockMulti2.get(),rotatron);
			SchematicDataRegistry.setHandler(AppEng.getInstance().config.blockMulti3.get(),rotatron);
		}
	}
}
