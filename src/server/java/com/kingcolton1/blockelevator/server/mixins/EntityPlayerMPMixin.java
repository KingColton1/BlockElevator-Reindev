package com.kingcolton1.blockelevator.server.mixins;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockCheeseWheel;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.other.EntityItem;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.WorldInfo;
import net.minecraft.src.server.physics.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kingcolton1.blockelevator.BlockElevatorFunc;
import com.kingcolton1.blockelevator.BlockeElvator;

import java.util.List;

@Mixin(value = EntityPlayerMP.class, remap = false)
public abstract class EntityPlayerMPMixin extends EntityPlayer {

	@Unique
	protected int elevatorBlockX;
	@Unique
	protected int elevatorBlockY;
	@Unique
	protected int elevatorBlockZ;
	@Unique
	protected double py = 0;
	@Unique
	protected int cooldown = 0;
	private float x;
	private float z;
	private int y;
	private double xd;
	public EntityPlayerMPMixin(World world) {
		super(world);
	}

	public static World world;

	@Inject(method= "onLivingUpdate()V", at = @At("TAIL"))
	private void elevatorTick(CallbackInfo ci){
		cooldown--;
		double dy = this.y-py;
		py = this.y;

		List<AxisAlignedBB> cubes = world.getCubes(this, this.boundingBox.getOffsetBoundingBox(this.xd, -1.0, 0.0));
		if (!cubes.isEmpty()){
			AxisAlignedBB cube = cubes.get(0);
			if (cube != null){

				int blockX = (int) cube.minX;
				int blockY = (int) cube.minY;
				int blockZ = (int) cube.minZ;

				if(world.getBlockId(blockX, blockY, blockZ) instanceof BlockElevatorFunc) {
					elevatorBlockX = blockX;
					elevatorBlockY = blockY;
					elevatorBlockZ = blockZ;
				}

				if(isSneaking() && cooldown <= 0 && world.getBlockId(blockX, blockY, blockZ) instanceof BlockElevatorFunc){
					BlockElevatorFunc.sneak(world, blockX, blockY, blockZ, (EntityPlayerMP)(Object)this);
					cooldown = 2; /*ElevatorsMod.config.getInt("ElevatorCooldown");*/
					return;
				}
			}
		}


		if(dy > 0.2 && cooldown <= 0 && Math.abs(this.x - (elevatorBlockX+0.5f)) < 0.5f && Math.abs(this.z - (elevatorBlockZ+0.5f)) < 0.5f && this.y - elevatorBlockY > 0){

			BlockElevatorFunc.jump(world, elevatorBlockX, elevatorBlockY, elevatorBlockZ, (EntityPlayerMP)(Object)this);
			cooldown = 2; /*ElevatorsMod.config.getInt("ElevatorCooldown");*/
			return;
		}
	}
}