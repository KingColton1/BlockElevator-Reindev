package com.kingcolton1.blockelevator.server.mixins;

import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.server.physics.AxisAlignedBB;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kingcolton1.blockelevator.API.AssignBlock;
import com.kingcolton1.blockelevator.BlockElevatorFunc;

import java.util.List;
import java.util.logging.Logger;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving {

	@Unique
	protected int elevatorBlockX;
	@Unique
	protected int elevatorBlockY;
	@Unique
	protected int elevatorBlockZ;
	@Unique
	protected boolean stoodOnElevator;
	@Unique
	protected double py = 0;
	@Unique
	protected int cooldown = 0;
	@Unique
	protected EntityPlayer thisAs = (EntityPlayer)(Object)this;
	public EntityPlayerMixin(World world) {
		super(world);
	}
    public AssignBlock api;

	@Inject(method= "onLivingUpdate()V", at = @At("TAIL"))
	private void elevatorTick(CallbackInfo ci){
		cooldown--;
		double dy = this.posY-py;
		py = this.posY;

		List<AxisAlignedBB> cubes = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(this.posX, -1.0, 0.0));
		if (!cubes.isEmpty()) {
			AxisAlignedBB cube = cubes.get(0);
			if (cube != null) {

				int blockX = (int) cube.minX;
				int blockY = (int) cube.minY;
				int blockZ = (int) cube.minZ;
				Integer blockUnderFeet = worldObj.getBlockId(blockX, blockY, blockZ);
				Logger.getLogger("blockUnderFeet").info(blockUnderFeet.toString());

				if (blockUnderFeet == api.getID()) {
					Logger.getLogger("FoundGoldBlock").info("!! Gold block FOUND !!");
					stoodOnElevator = true;
					elevatorBlockX = blockX;
					elevatorBlockY = blockY;
					elevatorBlockZ = blockZ;
				} else if (blockUnderFeet != 0 || worldObj.getBlockId(blockX, blockY, blockZ) == 0) {
					Logger.getLogger("NotFound").info("did not find it...");
					stoodOnElevator = false;
					cooldown += 1;
				}

				if (isSneaking() && cooldown <= 0 && blockUnderFeet == api.getID() && stoodOnElevator) {
					BlockElevatorFunc.sneak(worldObj, blockX, blockY, blockZ, thisAs);
					stoodOnElevator = false;
					return;
				}
			}
		}


		if (dy > 0.075 &&  cooldown <= 0  && stoodOnElevator && Math.abs(this.posX - (elevatorBlockX+0.5f)) < 0.5f && Math.abs(this.posZ - (elevatorBlockZ+0.5f)) < 0.5f && this.posY - elevatorBlockY > 0) {
			BlockElevatorFunc.jump(worldObj, elevatorBlockX, elevatorBlockY, elevatorBlockZ, thisAs);
			stoodOnElevator = false;
			return;
		}
	}
}