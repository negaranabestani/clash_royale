package model.Tower;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.AttackCard;
import model.Damage;
import model.WarObject;
import model.informations.ACLevelValue;
import model.informations.LevelInformation;
import model.informations.LevelValue;

import java.io.File;

public abstract class Tower extends WarObject {
    private float hp;
    private float hitSpeed;
    private float range;
    private ImageView[][] imageViews=new ImageView[3][3];
    private double x;
    private double y;
    private Circle CanonnBall = new Circle();
    private AttackCard LockedTarget=null;
    private boolean isLocked=false;
    private Damage damage;
    private int ShootingTimeTick=0;
    private String type ;
    private boolean gotHurt = false;
    public float getHitSpeed() {
        return hitSpeed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public AttackCard getLockedTarget() {
        return LockedTarget;
    }

    public void setLockedTarget(AttackCard lockedTarget) {
        LockedTarget = lockedTarget;
    }

    public void setHitSpeed(float hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public ImageView[][] getImageViews() {
        return imageViews;
    }
    public void Hurt(double damage)
    {
        gotHurt=true;
        double temp = this.getLevelInformation().getHp();
        this.getLevelInformation().setHp(temp-damage);
    }
    public void setGotHurt(boolean gotHurt) {
        this.gotHurt = gotHurt;
    }
    public boolean isGotHurt() {
        return gotHurt;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Circle getCanonnBall() {
        return CanonnBall;
    }

    public void setCanonnBall(Circle canonnBall) {
        CanonnBall = canonnBall;
    }

    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
    public boolean isLocked() {
        if(LockedTarget==null)
        {
            setLocked(false);
            return false;
        }
        else
        {
            setLocked(true);
            return true;
        }
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public abstract void Hit();
    public double targetDistance()
    {
        double tempx = this.getLockedTarget().getX_Current() - this.getX();
        double tempy = this.getLockedTarget().getY_Current() - this.getY();
        double sum = Math.pow(tempx,2)+Math.pow(tempy,2);
        return Math.pow(sum,0.5);
    }
    public Damage getDamage() {
        return damage;
    }
    public void setDamage(Damage damage) {
        this.damage = damage;
    }
    public void CannonBallForward(double dist)
    {
        this.CanonnBall.setCenterY(this.CanonnBall.getCenterY()-dist);
    }
    public void CannonBallBackWard(double dist)
    {
        this.CanonnBall.setCenterY(this.CanonnBall.getCenterY()+dist);
    }
    public void CannonBallLeft(double dist)
    {
        this.CanonnBall.setCenterX(this.CanonnBall.getCenterX()-dist);
    }
    public void CannonBallRight(double dist)
    {
        this.CanonnBall.setCenterX(this.CanonnBall.getCenterX()+dist);
    }
    @Override
    public ACLevelValue getLevelInformation() {
        return ((ACLevelValue)super.getLevelInformation());
    }
    public void setCannonBallPic()
    {
            Image image = new Image(new File("src/main/resources/pics/Characters/CannonBall.png").toURI().toString());
            ImagePattern imagePattern = new ImagePattern(image);
            this.CanonnBall.setFill(imagePattern);
            this.CanonnBall.setRadius(5);
            this.CanonnBall.setCenterX(this.getX());
            this.CanonnBall.setCenterY(this.getY());
    }
    public void setShootingTimeTick(int shootingTimeTick) {
        ShootingTimeTick = shootingTimeTick;
    }

    public int getShootingTimeTick() {
        return ShootingTimeTick;
    }
    public void incrementTimeTick()
    {
        ShootingTimeTick++;
    }
    public void resetTimeTick()
    {
        this.CanonnBall.setCenterX(this.getX());
        this.CanonnBall.setCenterY(this.getY());
        setShootingTimeTick(0);
    }
    public void explosionPic()
    {
        Image image = new Image(new File("src/main/resources/pics/Characters/Explosion.png").toURI().toString());
        ImagePattern imagePattern = new ImagePattern(image);
        this.CanonnBall.setFill(imagePattern);
    }
}
