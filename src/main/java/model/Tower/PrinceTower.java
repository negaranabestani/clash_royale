package model.Tower;

import enums.Level;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Damage;
import model.informations.ACLevelValue;

import java.io.File;

public class PrinceTower extends Tower{
    public PrinceTower() {
        setHitSpeed(0.8F);
        setRange(7.5F);
        setLevel1(new ACLevelValue(1400.0,new Damage(50.0), Level.LEVEL1));
        setLevel2(new ACLevelValue(1512.0,new Damage(54.0),Level.LEVEL2));
        setLevel3(new ACLevelValue(1624.0,new Damage(58.0),Level.LEVEL3));
        setLevel4(new ACLevelValue(1750.0,new Damage(62.0),Level.LEVEL4));
        setLevel5(new ACLevelValue(1890.0,new Damage(69.0),Level.LEVEL5));
        setLevelInformation(super.getLevel1());
    }

    @Override
    public void Hit() {
        if(super.isLocked())
        {
            if(super.targetDistance()<= this.getRange()*20)
            {
                incrementTimeTick();
                super.setCannonBallPic();
                if(getShootingTimeTick()== (super.getHitSpeed()*10))
                {
                    super.getLockedTarget().Hurt((Double) super.getLevelInformation().getDamage().getValue());
                }
                double distPart= getShootingTimeTick()/(super.getHitSpeed()*10);
                double x_Vector =super.getLockedTarget().getX_Current()-super.getX();
                double y_Vector =super.getLockedTarget().getY_Current()-super.getY();
                double xMoveVector = x_Vector*distPart;
                double yMoveVector = y_Vector*distPart;
                if(xMoveVector>0)
                {
                    this.CannonBallRight(xMoveVector);
                }
                else
                {
                    this.CannonBallLeft((-1)*xMoveVector);
                }
                //------------------------------
                if(yMoveVector>0)
                {
                    this.CannonBallBackWard(yMoveVector);
                }
                else
                {
                    this.CannonBallForward((-1)*yMoveVector);
                }
                if(getShootingTimeTick()== (super.getHitSpeed() *10))
                {
                    setShootingTimeTick(0);
                }
            }
            else
            {
                resetTimeTick();
            }
        }
        else
        {
            resetTimeTick();
            super.setLockedTarget(null);
        }
    }
}
