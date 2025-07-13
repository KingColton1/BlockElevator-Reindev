package com.kingcolton1.blockelevator.mixins;

import com.kingcolton1.blockelevator.BlockElevator;
import net.minecraft.common.entity.EntityLiving;
import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.common.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kingcolton1.blockelevator.RunElevator;

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
	protected int cooldown = 10; // To avoid going up an elevator on join
	@Unique
	protected EntityPlayer thisAs = (EntityPlayer)(Object)this;
	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Inject(method= "onLivingUpdate()V", at = @At("TAIL"))
	private void elevatorTick(CallbackInfo ci) {
		if (!BlockElevator.config.enableElevator || worldObj == null)
			return;

		double dy = posY-py;
		py = posY;

		if (cooldown > 0) {
			--cooldown;
			return;
		}

		int plrX;
		int plrY;
		int plrZ;

		if (worldObj.isServer) {
			plrX = (int) posX - 1;
			plrY = (int) posY - 1;
			plrZ = (int) posZ - 1;

			final int minX = (int)Math.floor(posX - 0.5d);
			final int minZ = (int)Math.floor(posZ - 0.5d);

			searchLoop:
			for (int x = minX; x <= minX + 1; x++){
				for (int z = minZ; z <= minZ + 1; z++){
					final int blockID = worldObj.getBlockId(x, plrY, z);

					if (BlockElevator.config.elevatorBlockIDs.contains(blockID)){
						stoodOnElevator = true;
						plrX = x;
						plrZ = z;
						break searchLoop;
					}
				}
			}
		}
		else {
			plrX = (int) boundingBox.minX - 1;
			plrY = (int) boundingBox.minY - 1;
			plrZ = (int) boundingBox.minZ;
		}

		final int blockIdUnderPlr = worldObj.getBlockId(plrX, plrY, plrZ);

		// Assigned block is found, otherwise keep looking for it
		if (BlockElevator.config.elevatorBlockIDs.contains(blockIdUnderPlr)) {
			stoodOnElevator = true;
			elevatorBlockX = plrX;
			elevatorBlockY = plrY;
			elevatorBlockZ = plrZ;
		} else {
			stoodOnElevator = false;
		}

		// Cooldown after use of elevator (jump or sneak)
		if (isSneaking() && stoodOnElevator) {
			if (RunElevator.checkServerOrClient(thisAs).equals("server")) {
				RunElevator.sneak(worldObj, elevatorBlockX, elevatorBlockY, elevatorBlockZ, thisAs);
			} else if (RunElevator.checkServerOrClient(thisAs).equals("client") && !worldObj.isServer) {
				RunElevator.sneak(worldObj, elevatorBlockX, elevatorBlockY, elevatorBlockZ, thisAs);
			}
			stoodOnElevator = false;
			cooldown = BlockElevator.config.coolDownTicks;
		} else if (dy > BlockElevator.config.dYRequiredForJump && stoodOnElevator && Math.abs(posX - (elevatorBlockX+0.5f)) < 0.5f && Math.abs(posZ - (elevatorBlockZ+0.5f)) < 0.5f && posY - elevatorBlockY > 0) { // Jumping detection
			RunElevator.jump(worldObj, elevatorBlockX, elevatorBlockY, elevatorBlockZ, thisAs);
			stoodOnElevator = false;
			cooldown = BlockElevator.config.coolDownTicks;
		}
	}
}