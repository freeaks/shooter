package ftaghn.shooter;

//import game.test.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
//import android.media.MediaPlayer;

public class Backgnd extends Entity{

  int numStars   = 10;
  Bitmap[] stars = new Bitmap[numStars];
  int[] starX    = new int[numStars];
  int[] starY    = new int[numStars];
  private Paint mBitmapPaint = new Paint();
  int bg1x, bg1y, bg2x, bg2y;
  Bitmap bg1;
  Bitmap bg2;
  //MediaPlayer bgm;

  public Backgnd(int x, int y, String type, Context mContext)
  {
    super(x, y, type,  -10, -10, mContext);
    bg1 = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bluebg);
    bg2 = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bluebg);
    bg1y=-bg1.getHeight();
    for (int i=0;i<numStars;i++)
    {
      stars[i] = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.stars);
      starX[i]=((int)Math.round(Math.random()*320));
      starY[i]=((int)Math.round(Math.random()*430));
    }    
  }  

  public void update()
  {    
    if (silentMode)
      mp.stop();
    else
    {
      if (!mp.isPlaying())
      {
        mp = MediaPlayer.create(mContext, R.raw.paranoid);
        mp.start();
       //System.out.printf("**************** bleh\n"); 
      }
    }
     if (myGame.gameover)
       mp.stop();
      
    for (int i = 0; i < numStars; i++)
    {
      starY[i]+=2;
      //star movement according to ship      
      if (myGame.up)
        starY[i]+=1;  

      if (myGame.left)
        starX[i]++;

      if (myGame.right)
        starX[i]--;

      if (starX[i]<=0)
        starX[i]=315;

      if (starX[i]>=320)
        starX[i]=5;

      if (starY[i]>=430)
        starY[i]=0;
    }
    bg1y++;
    bg2y++;
    if (bg2y>=430)
      bg2y=-bg2.getHeight();
    if (bg1y>=430)
      bg1y=-bg1.getHeight();
  }

  public boolean collidedWith(Entity entity) 
  {
    return false;
  }

  public void paint(Canvas g)
  {
    g.drawBitmap(bg1,0 , bg1y, mBitmapPaint);
    g.drawBitmap(bg2,0 , bg2y, mBitmapPaint);
    for (int i = 0; i < numStars; i++)
    {
      g.drawBitmap(stars[i],starX[i] , starY[i], mBitmapPaint);
    }
  }

}
