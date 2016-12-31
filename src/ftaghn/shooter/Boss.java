package ftaghn.shooter;

//import game.test.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.media.MediaPlayer;

public class Boss extends Entity{
  Bitmap[] shipSprite;
  private Paint mBitmapPaint = new Paint();
  int sizeX=113;
  int sizeY=104;
  int speed=2;
  Weapon shoot;
  int shotTimer;
  boolean goLeft=true; 
  boolean goRight=false;
  boolean goUp=false;
  boolean goDown=true;
  //MediaPlayer fire;

  public Boss(int x, int y, String type, Context mContext)
  {
    super(x, y, type, x+113, y+104, mContext);
    MAX_SHIELDS = 80;
    shields = MAX_SHIELDS;
    shipSprite = new Bitmap[]{
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.boss),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_shipleft),
        BitmapFactory.decodeResource(mContext.getResources(),R.drawable.sb_shipright)
    };
    //try { fire = getAudioClip(R.raw.lazer); }
    //catch (Exception e) { Tools.MessageBox(mContext, "Audio Error: " + e.toString()); }

  }
  public void update()
  {
    if (myGame.gameover)
      mp.release();

    if (x<=8)
    {
      goLeft=false;
      goRight=true;
    }
    if(x>=200)
    {
      goLeft=true;
      goRight=false;      
    }
    if (y>=100)
    {
      goDown=false;
      goUp=true;
    }
    if (y<=20)
    {
      goDown=true;
      goUp=false;
    }

    if (goDown&&!goUp)
      y+=speed;
    if (goUp&&!goDown)
      y-=speed;

    if (goLeft&&!goRight)
      x-=speed;
    if (!goLeft&&goRight)
      x+=speed;

    bounds.left=x;
    bounds.right=x+sizeX;
    bounds.top=y;
    bounds.bottom=y+sizeY;

    detectArea.left=x;
    detectArea.right=x+sizeX;
    detectArea.top=y;
    detectArea.bottom=y+400;
    //myGame.processCollisions(this);
    /*
    if (y>420)
    {
      myGame.entities.remove(this);
      myGame.enemyCount-=1;
    }
     */
    shotTimer++;
    if (detected)
    {
      if (shotTimer>20)
      {
        if (!silentMode)mp.start();
        myGame.entities.add(shoot = new Weapon(x+40, y+108, "shoot", this, mContext));
        shotTimer=0;
      }
    }
  }

  public boolean collidedWith(Entity entity) {

    if ((entity instanceof Ship))
    {      
      myGame.gameover=true;
      myGame.ingame=false;
    }
    myGame.entities.remove(entity);
    setShields(shields-=1);
    if (myGame.progress<=314)myGame.progress+=1;
    if (shields<=0)
    {
      myGame.entities.remove(this);
    }
    return true;
  }

  public void paint(Canvas g)
  {
    g.drawBitmap(shipSprite[0], x, y, mBitmapPaint);
    if (debugMode)
    {
      mBitmapPaint.setStyle(Paint.Style.STROKE);
      mBitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, mBitmapPaint);
      g.drawRect(detectArea, mBitmapPaint);
    }
  }   
}
