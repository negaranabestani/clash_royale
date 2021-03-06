package model.Building;

import enums.CardId;
import enums.Level;
import enums.Target;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.Damage;
import model.informations.ACLevelValue;

import java.io.File;

/**
 * The type Inferno tower.
 */
public class InfernoTower extends Building{
    private int FireTimeTick=0;
   // private Line fireLine = new Line();
    private Circle fireball = new Circle();

    /**
     * Instantiates a new Inferno tower.
     */
    public InfernoTower(){
        setAvatar("inferno-tower.png");
        setHitSpeed(0.4F);
        setTarget(Target.AIR_GROUND);
        setRange(6);
        setLifeTime(40);
        setCost(5);
        setLevel1(new ACLevelValue(800.0, new Damage<>(new DamageVary(20.0, 400.0)), Level.LEVEL1));
        setLevel2(new ACLevelValue(880.0,new Damage<>(new DamageVary(22.0,440.0)),Level.LEVEL2));
        setLevel3(new ACLevelValue(968.0,new Damage<>(new DamageVary(24.0,484.0)),Level.LEVEL3));
        setLevel4(new ACLevelValue(1064.0,new Damage<>(new DamageVary(26.0,532.0)),Level.LEVEL4));
        setLevel5(new ACLevelValue(1168.0,new Damage<>(new DamageVary(29.0,584.0)),Level.LEVEL5));
        setLevelInformation(super.getLevel1());
        Image image = new Image(new File("src/main/resources/pics/Characters/InfernoTower.png").toURI().toString());
        ImagePattern imagePattern = new ImagePattern(image);
        super.getPicHandler().setFill(imagePattern);
        super.getPicHandler().setHeight(20);
        super.getPicHandler().setWidth(20);
        super.getPicHandler().setX(super.getX_Current());
        super.getPicHandler().setY(super.getY_Current());
        setId(CardId.inferno);
    }

    /**
     * Hit method
     */
    @Override
    public void Hit()
    {
        if(super.isLocked())
        {
            if(super.getLockedTarget()!=null)
            {
                if(super.targetDistance()<= this.getRange() * 20)
                {
                    incrementTimeTick();
                    setFireballPic();
                    if(getShootingTimeTick()== (super.getHitSpeed() *10))
                    {
                        FireTimeTick++;
                        Damage();
                        explosionPic();
                    }
                    double distPart= getShootingTimeTick()/(super.getHitSpeed()*10);
                    double x_Vector =super.getLockedTarget().getX_Current()-this.getPicHandler().getX();
                    double y_Vector =super.getLockedTarget().getY_Current()-this.getPicHandler().getY();
                    //*************************
                    //------------------------
                    double xMoveVector = x_Vector*distPart;
                    double yMoveVector = y_Vector*distPart;
                    if(xMoveVector>0)
                    {
                        this.fireBallRight(xMoveVector);
                    }
                    else
                    {
                        this.fireBallLeft((-1)*xMoveVector);
                    }
                    //------------------------------
                    if(yMoveVector>0)
                    {
                        this.fireBallBackWard(yMoveVector);
                    }
                    else
                    {
                        this.fireBallForward((-1)*yMoveVector);
                    }
                }
                else
                {
                    setShootingTimeTick(0);
                    FireTimeTick=0;
                    super.setLockedTarget(null);
                    super.setTowerTarget(null);
                    fireball.setCenterX(super.getX_Current());
                    fireball.setCenterY(super.getY_Current());
                }
            }
            else if(super.getTowerTarget()!=null)
            {
                if(super.towerDistance()<= this.getRange() * 20)
                {
                    incrementTimeTick();
                    setFireballPic();
                    if(getShootingTimeTick()== (super.getHitSpeed() *10))
                    {
                        FireTimeTick++;
                        Damage();
                    }
                    double distPart= getShootingTimeTick()/(super.getHitSpeed()*10);
                    double x_Vector =super.getTowerTarget().getX()-this.getPicHandler().getX();
                    double y_Vector =super.getTowerTarget().getY()-this.getPicHandler().getY();
                    //*************************
                    //------------------------
                    double xMoveVector = x_Vector*distPart;
                    double yMoveVector = y_Vector*distPart;
                    //------------------------
                    if(xMoveVector>0)
                    {
                        this.fireBallRight(xMoveVector);
                    }
                    else
                    {
                        this.fireBallLeft((-1)*xMoveVector);
                    }
                    //------------------------------
                    if(yMoveVector>0)
                    {
                        this.fireBallBackWard(yMoveVector);
                    }
                    else
                    {
                        this.fireBallForward((-1)*yMoveVector);
                    }
                }
                else
                {
                    setShootingTimeTick(0);
                    FireTimeTick=0;
                    super.setLockedTarget(null);
                    super.setTowerTarget(null);
                    fireball.setCenterX(super.getX_Current());
                    fireball.setCenterY(super.getY_Current());
                }
            }
        }
        else
        {
            setShootingTimeTick(0);
            FireTimeTick=0;
            super.setLockedTarget(null);
            super.setTowerTarget(null);
            fireball.setCenterX(super.getX_Current());
            fireball.setCenterY(super.getY_Current());
        }

    }

    /**
     * set time tick to 0
     */
    @Override
    public void resetTimeTick() {
        setShootingTimeTick(0);
        FireTimeTick=0;
    }

    /**
     * inferno tower damage range which is increased by time
     */
    private void Damage()
    {
        DamageVary damage = (DamageVary) super.getLevelInformation().getDamage().getValue();
        double  range= (damage.max- damage.min);
        double harm = (this.FireTimeTick/40.0)*range + damage.min;
        if(super.getTowerTarget()!=null)
        {
            super.getTowerTarget().Hurt(harm);
        }
        else if(super.getLockedTarget()!=null)
        {
            super.getLockedTarget().Hurt(harm);
        }
    }

    /**
     * Sets fire time tick.
     *
     * @param fireTimeTick the fire time tick
     */
    public void setFireTimeTick(int fireTimeTick) {
        FireTimeTick = fireTimeTick;
    }

    /**
     * Gets fire time tick.
     *
     * @return the fire time tick
     */
    public int getFireTimeTick() {
        return FireTimeTick;
    }

    /**
     * Gets fireball.
     *
     * @return the fireball
     */
    public Circle getFireball() {
        return fireball;
    }
    private void setFireballPic()
    {
        Image image = new Image(new File("src/main/resources/pics/Characters/FireBall.png").toURI().toString());
        ImagePattern imagePattern = new ImagePattern(image);
        this.fireball.setFill(imagePattern);
        this.fireball.setRadius(10);
        this.fireball.setCenterX(this.getX_Current());
        this.fireball.setCenterY(this.getY_Current());
    }

    /**
     * Fire ball forward.
     *
     * @param dist the dist
     */
    public void fireBallForward(double dist)
    {
        this.fireball.setCenterY(this.fireball.getCenterY()-dist);
    }

    /**
     * Fire ball back ward.
     *
     * @param dist the dist
     */
    public void fireBallBackWard(double dist)
    {
        this.fireball.setCenterY(this.fireball.getCenterY()+dist);
    }

    /**
     * Fire ball left.
     *
     * @param dist the dist
     */
    public void fireBallLeft(double dist)
    {
        this.fireball.setCenterX(this.fireball.getCenterX()-dist);
    }

    /**
     * Fire ball right.
     *
     * @param dist the dist
     */
    public void fireBallRight(double dist)
    {
        this.fireball.setCenterX(this.fireball.getCenterX()+dist);
    }
    private void explosionPic()
    {
        Image image = new Image(new File("src/main/resources/pics/Characters/Explosion.png").toURI().toString());
        ImagePattern imagePattern = new ImagePattern(image);
        fireball.setFill(imagePattern);
    }

    /**
     * rage effect
     */
    @Override
    public void rageImpact()
    {
        this.setHitSpeed(this.getHitSpeed() -0.2F);
        DamageVary damage = (DamageVary) super.getLevelInformation().getDamage().getValue();
        damage.setMax(damage.max*1.4);
    }

    /**
     * undo rage effect
     */
    @Override
    public void undoRage()
    {
        this.setHitSpeed(0.4F);
        DamageVary damage = (DamageVary) super.getLevelInformation().getDamage().getValue();
        if(this.getLevelInformation().level==Level.LEVEL1)
        {
            damage.setMax(400);
        }
        else if(this.getLevelInformation().level==Level.LEVEL2)
        {
            damage.setMax(440);
        }
        else if(this.getLevelInformation().level==Level.LEVEL3)
        {
            damage.setMax(484);
        }
        else if(this.getLevelInformation().level==Level.LEVEL4)
        {
            damage.setMax(532);
        }
        else if(this.getLevelInformation().level==Level.LEVEL5)
        {
            damage.setMax(584);
        }

    }
}

/**
 * The type Damage vary.
 */
class DamageVary {
    /**
     * The Min.
     */
    double min;
    /**
     * The Max.
     */
    double max;

    /**
     * Instantiates a new Damage vary.
     *
     * @param min the min
     * @param max the max
     */
    public DamageVary(double min,double max){
        this.min=min;
        this.max=max;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(double min) {
        this.min = min;
    }
}
