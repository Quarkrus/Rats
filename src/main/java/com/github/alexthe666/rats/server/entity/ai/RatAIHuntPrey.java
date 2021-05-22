package com.github.alexthe666.rats.server.entity.ai;

import com.github.alexthe666.rats.server.entity.EntityRat;
import com.google.common.base.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.EnumSet;

public class RatAIHuntPrey<T extends LivingEntity> extends NearestAttackableTargetGoal<LivingEntity> {
    private final EntityRat rat;

    public RatAIHuntPrey(EntityRat entityIn, Predicate<LivingEntity> targetSelector) {
        super(entityIn, LivingEntity.class, 10, true, false, targetSelector);
        this.rat = entityIn;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        return !rat.isInCage() && (rat.shouldHuntAnimal() || rat.shouldHuntMonster() || rat.hasPlague()) && rat.getHeldItem(Hand.MAIN_HAND).isEmpty() && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return (rat.shouldHuntAnimal() || rat.shouldHuntMonster()) && super.shouldContinueExecuting();
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        if(rat.isPatrolCommand()){
            return this.goalOwner.getBoundingBox().grow(3.5D, 3.5D, 3.5D);
        }else{
            return this.goalOwner.getBoundingBox().grow(targetDistance, 9.0D, targetDistance);
        }
    }
}