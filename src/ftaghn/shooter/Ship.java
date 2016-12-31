package ftaghn.shooter;

//import game.test.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ship extends Entity{

  Bitmap[] shipSprite;
  Bitmap[] engineFire;

  private Paint mBitmapPaint = new Paint();
  Weapon shoot;
  int sizeX=39;
  int sizeY=36;
  int speed=6;
  int shotTimer;
  int fc;

  public Ship(int x, int y, String type, Context mContext)
  {
    super(x, y, type, x+39, y+36, mContext);
    MAX_SHIELDS = 180;
    shields = MAX_SHIELDS;
    shipSprite = new Bitmap[]{
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_ship),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_shipleft),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_shipright)
    };
    engineFire = new Bitmap[]{
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_fire0),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_fire1),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_fire0),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_firestrafe0),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_firestrafe1),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_firestrafe0)
    };
  }

  public void update()
  {
    shotTimer++;
    if (getShields()<=0) { die(); }

    if (!(x<=8) && myGame.left)
    {
      x-=speed;
      bounds.left=(int)x;
      bounds.right=(int)x+39;
    }
    if (!(x>=280) && myGame.right)
    {
      x+=speed;
      bounds.left=(int)x;
      bounds.right=(int)x+39;
    }
    if (!(y<=0) && myGame.up)
    {
      y-=speed;
      bounds.top=(int)y;
      bounds.bottom=(int)y+36;
    }
    if (!(y>=380) && myGame.down)
    {
      y+=speed;
      bounds.top=(int)y;
      bounds.bottom=(int)y+36;
    }
    if (myGame.action1)
    {      
      if (shotTimer>5)
      {
        if (!(myGame.weaponHeat>=200))
          myGame.weaponHeat+=10;
        if (!(myGame.weaponHeat>=180))
        {
          if (!silentMode)mp.start();
          myGame.entities.add(shoot = new Weapon((int)x, (int)y, "shoot", this, mContext));
          shotTimer=0;
        }
      }
    }
  }

  public void die()
  {

  }

  public boolean collidedWith(Entity entity) {
    if (entity instanceof Enemy)
    {      
      myGame.entities.remove(entity);
      myGame.enemyCount-=1;
      setShields(shields-=10);    
    }
    if (entity instanceof Weapon)
    {
      myGame.entities.remove(entity);
      setShields(shields-=10);
    }
    return true;
  }

  public void paint(Canvas g)
  {
    if (myGame.left)
    {
      g.drawBitmap(shipSprite[1], x, y, mBitmapPaint);
      if (fc<3||fc>4) { fc=3; } fc++;
      g.drawBitmap(engineFire[fc], x+4, y+34, mBitmapPaint);
    }
    else if (myGame.right)
    {
      g.drawBitmap(shipSprite[2], x, y, mBitmapPaint);
      if (fc<3||fc>4) { fc=3; } fc++;
      g.drawBitmap(engineFire[fc], x+4, y+34, mBitmapPaint);
    }
    else
    {
      g.drawBitmap(shipSprite[0], x, y, mBitmapPaint);
      if (fc>=2) { fc=0; } fc++;
      g.drawBitmap(engineFire[fc], x+4, y+34, mBitmapPaint);
    }

    if (debugMode)
    {
      mBitmapPaint.setStyle(Paint.Style.STROKE);
      mBitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, mBitmapPaint);
    }
  }  
}
