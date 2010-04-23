package net.jalmus;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import java.awt.Polygon;

public class Piano {

  Vector blackKeys = new Vector();
  Vector keys = new Vector();
  Vector whiteKeys = new Vector();
  Color jfcBlue = new Color(204, 204, 255);
  Color red = new Color(242, 179, 112);
  Color pink = new Color(255, 175, 175);

  net.jalmus.Key prevKey;
  final int kw = 16, kh = 80;
  final int haut = 315;
  int marge = 40;
  int length = 73;

  public Piano(int l, int m) {

    int octavanumber;
    int transpose;

    this.marge = m;
    this.length = l;

    if (length == 61) {
      octavanumber = 5;
      transpose = 36;
    }
    else {
      octavanumber = 6;
      transpose = 24;
    }

    keys.removeAll(blackKeys);
    keys.removeAll(whiteKeys);

    int whiteIDs[] = {
        0, 2, 4, 5, 7, 9, 11};

    for (int i = 0, x = 3; i < octavanumber; i++) {
      for (int j = 0; j < 7; j++, x += kw) {
        int keyNum = i * 12 + whiteIDs[j] + transpose;
        whiteKeys.add(new net.jalmus.Key(x + marge, haut, kw, kh, keyNum));
      }
    }

    whiteKeys.add(new net.jalmus.Key(7 * octavanumber * kw + marge + 3, haut, kw, kh,
                          octavanumber * 12 + transpose));

    for (int i = 0, x = 3; i < octavanumber; i++, x += kw) {
      int keyNum = i * 12 + transpose;
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 1));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 3));
      x += kw;
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 6));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 8));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 10));
    }
    keys.addAll(blackKeys);
    keys.addAll(whiteKeys);

  }

  public boolean is73keys() {
    return this.length == 73;
  }

  public boolean is61keys() {
    return this.length == 61;
  }



  public void Setprevkey(net.jalmus.Key k) {
    this.prevKey = k;
  }

  public net.jalmus.Key Getprevkey() {
    return this.prevKey;
  }



  /**
     * To return Key of the Piano where the mouse aim
     * @return Key of the piano where the mouse aim
     * @param Point of the mouse pointer
   */
  public net.jalmus.Key getKey(Point point) {
    Point p = new Point();

    p.setLocation(point.getX() - 4, point.getY() - 100);

    for (int i = 0; i < keys.size(); i++) {
      if ( ( (net.jalmus.Key) keys.get(i)).contains(p)) {
        return (net.jalmus.Key) keys.get(i);
      }
    }
    return null;
  }
  
  public boolean rightbuttonpressed(Point point){
	  
	  Rectangle rec = new Rectangle(740, 440, 30 ,30); 
  	return (rec.contains(point));  
  }
  
  public boolean leftbuttonpressed(Point point){
	  
	  Rectangle rec = new Rectangle(5, 440, 30 ,30); 
  	return (rec.contains(point));  
  }


  /**
     * To play or stop the midi sound of a note when key of piano is pressed
     *
     * @param ChannelData where the note should be play,
     *  boolean indicate if the midi channel work and if sound is activate,
     *  int represent pitch of the note,
     *  int onoff if the note is already play or not.
   */
  public void notejouee(net.jalmus.ChannelData cc, boolean midiok, int pitch, int onoff) {
    // System.out.println("note jouee");

    for (int i = 0; i < whiteKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) whiteKeys.get(i);
      if (onoff == 1 & key.kNum == pitch)
        key.on(cc, midiok);
      else if (onoff == 0 & key.kNum == pitch)
        key.off(cc, midiok);
    }
    for (int i = 0; i < blackKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) blackKeys.get(i);
      if (onoff == 1 & key.kNum == pitch)
        key.on(cc, midiok);
      else if (onoff == 0 & key.kNum == pitch)
        key.off(cc, midiok);
    }
  }



  /**
   * Paint keyboard on screen with  keys in blue when played, in green for notes to train
   *
   * @param  g Graphics to paint 
   * @param  basenotepitch1 pitch of the first note to train
   * @param  basenotepitch2 pitch of the last note to train
   * @return      void
   * @see         Image
   */

  public void paint(Graphics g, boolean parti, int basenotepitch1,  int basenotepitch2,  int basenotepitchb1,  int basenotepitchb2,
		  int pitchcourant0, int pitchcourant1,
                    int pitchcourant2) {
    Graphics2D g2 = (Graphics2D) g;
    //  Dimension d = getSize();
    float f1, f2, f3 = 1;
    Color c = new Color(255, 235, 235);
    Color cg = new Color(152, 251, 152);

    //paint bouton to move notes to train
    g2.setColor(cg);
    g2.fill3DRect(740, 340, 30,30, true) ;
    g2.fill3DRect(5, 340, 30,30, true) ;
    g2.setColor(Color.black);
    g2.fillRect(745, 351, 12, 8);
    g2.fillRect(15, 351, 12, 8);

    int[]x = new int[3];
    int[]y = new int[3]; 
//     Make a triangle
    x[0]=756; x[1]=766; x[2]=756;
    y[0]=345; y[1]=355; y[2]=365;
    Polygon myTri = new Polygon(x, y, 3); 
    g2.fillPolygon(myTri);
    x[0]=18; x[1]=8; x[2]=18;
    y[0]=345; y[1]=355; y[2]=365;
    myTri = new Polygon(x, y, 3); 
    g2.fillPolygon(myTri);
 


    //System.out.println (basenotepitch1);
    //System.out.println (basenotepitch2);
    g2.setColor(Color.black);
    // g2.drawRect(marge+3,haut,42*kw,kh);
    for (int i = 0; i < whiteKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) whiteKeys.get(i);
      if (key.isNoteOn()) {
        g2.setColor(jfcBlue);
        g2.fill(key);
      }
      else if (!parti & ((key.kNum <= basenotepitch1  & key.kNum >= basenotepitch2) | (key.kNum <= basenotepitchb1  & key.kNum >= basenotepitchb2))){
    	  g2.setColor(cg);
      g2.fill(key);
      }
      else if (key.kNum == pitchcourant0 | key.kNum == pitchcourant1 |
               key.kNum == pitchcourant2) {
        g2.setColor(red);
        g2.fill(key);
      }

      else if (key.kNum == 60 & !key.isNoteOn()) {
        g2.setColor(c);
        g2.fill(key);
        g2.setColor(Color.white);
      }

      g2.setColor(Color.black);
      g2.draw(key);
    }
    for (int i = 0; i < blackKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) blackKeys.get(i);
      if (key.isNoteOn()) {
        g2.setColor(jfcBlue);
        g2.fill(key);
        g2.setColor(Color.black);
        g2.draw(key);
      }
      else if (key.kNum == pitchcourant0 | key.kNum == pitchcourant1 |
               key.kNum == pitchcourant2) {
        g2.setColor(red);
        g2.fill(key);
        g2.setColor(Color.black);
        g2.draw(key);
      }
      else {
        g2.setColor(Color.black);
        g2.fill(key);
      }
    }

  }
} // End class Piano

