package ftaghn.shooter;

//import game.test.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Weapon extends Entity{

  Bitmap laserSprite;
  private Paint mBitmapPaint = new Paint();

  int sizeX=25;
  int sizeY=9;
  int speed=10;
  Entity shooter;

  public Weapon(int x, int y, String type, Entity bleh, Context mContext)
  {
    super(x+6, y, type, x+25, y+9, mContext);
    shooter=bleh;

    if (shooter instanceof Ship)
    {
      laserSprite = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.lasers);
    }
    if (shooter instanceof Enemy)
    {
      laserSprite = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.lasers2);
    }
    if (shooter instanceof Boss)
    {
      laserSprite = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.lasers2);
    }
  }

  public void update()
  {
    if (shooter instanceof Ship)
    {
      y-=speed;
      bounds.left=(int)x;
      bounds.right=(int)x+25;
      bounds.top=(int)y;
      bounds.bottom=(int)y+9;
      if (y<=0)//||myGame.processCollisions(this))
      {
        myGame.entities.remove(this);
      }
    }
    if (shooter instanceof Enemy)
    {
      type="enemyshoot";
      y+=speed/2;
      bounds.left=(int)x;
      bounds.right=(int)x+25;
      bounds.top=(int)y;
      bounds.bottom=(int)y+9;
      if (y>=420)
      {
        myGame.entities.remove(this);
      }
    }
    if (shooter instanceof Boss)
    {
      y+=speed;
      bounds.left=(int)x;
      bounds.right=(int)x+25;
      bounds.top=(int)y;
      bounds.bottom=(int)y+9;
      if (y>=420)
      {
        myGame.entities.remove(this);
      }
    }
  }

  public void die(Entity bleh)
  {
    myGame.entities.remove(bleh);
    //bleh.mPlayer.release();
    //mPlayer=null;
  }

  public void paint(Canvas g)
  {
    g.drawBitmap(laserSprite, x, y, mBitmapPaint);
    if (debugMode)
    {
      mBitmapPaint.setStyle(Paint.Style.STROKE);
      mBitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, mBitmapPaint);
    }
  }

}
