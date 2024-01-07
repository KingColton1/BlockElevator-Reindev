package com.kingcolton1.blockelevator.server.mixins;

import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kingcolton1.blockelevator.API.AssignBlock;
import com.kingcolton1.blockelevator.API.ElevatorBlock;

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
	protected int cooldown = 5;
	@Unique
	protected EntityPlayer thisAs = (EntityPlayer)(Object)this;
	public EntityPlayerMixin(World world) {
		super(world);
	}
	//public AssignBlock api;

	@Inject(method= "onLivingUpdate()V", at = @At("TAIL"))
	private void elevatorTick(CallbackInfo ci) {
		double dy = this.posY-py;
		py = this.posY;

		int plrX = (int) posX;
		int plrY = (int) posY - 1;
		int plrZ = (int) posZ - 1;
		int blockUnderPlr = worldObj.getBlockId(plrX, plrY, plrZ);

		// Assigned block is found, otherwise keep looking for it
		if (blockUnderPlr == 41) {
			stoodOnElevator = true;
			elevatorBlockX = plrX;
			elevatorBlockY = plrY;
			elevatorBlockZ = plrZ;
		} else if (blockUnderPlr != 0 || worldObj.getBlockId(plrX, plrY, plrZ) == 0) {
			stoodOnElevator = false;
		}

		// Cooldown after use of elevator (jump or sneak)
		if (cooldown == 0) {
			// Sneaking detection
			if (isSneaking() && blockUnderPlr == 41 && stoodOnElevator) {
				ElevatorBlock.sneak(worldObj, plrX, plrY, plrZ, thisAs);
				stoodOnElevator = false;
				cooldown = 15;
			}

			// Jumping detection
			if (dy > 0.075 && stoodOnElevator && Math.abs(this.posX - (elevatorBlockX+0.5f)) < 0.5f && Math.abs(this.posZ - (elevatorBlockZ+0.5f)) < 0.5f && this.posY - elevatorBlockY > 0) {
				ElevatorBlock.jump(worldObj, elevatorBlockX, elevatorBlockY, elevatorBlockZ, thisAs);
				stoodOnElevator = false;
				cooldown = 15;
			}
		} else {
			cooldown--;
		}
	}
}