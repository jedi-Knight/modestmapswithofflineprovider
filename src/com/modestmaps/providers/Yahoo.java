
package com.modestmaps.providers;

import android.graphics.Bitmap;
import processing.core.*;
import com.modestmaps.core.*;
import com.modestmaps.geo.*;

public class Yahoo {

  public static final String ROAD_VERSION = "3.52";
  public static final String AERIAL_VERSION = "1.7";
  public static final String HYBRID_VERSION = "2.2";

  public static abstract class YahooProvider extends AbstractMapProvider {
  
    public YahooProvider() {
      super(new MercatorProjection(26, new Transformation(1.068070779e7f, 0.0f, 3.355443185e7f, 0.0f, -1.068070890e7f, 3.355443057e7f)));
    }
  
    public static String getZoomString(Coordinate coordinate) {
      coordinate = toYahoo(coordinate);
      return "x="+(int)coordinate.column+"&y="+(int)coordinate.row+"&z="+ (int)coordinate.zoom;
    }

    public int tileWidth() {
      return 256;
    }
    
    public int tileHeight() {
      return 256;
    }

  }

  public static class RoadProvider extends YahooProvider {
    public String[] getTileUrls(Coordinate coordinate) {
        return new String[] { "http://us.maps2.yimg.com/us.png.maps.yimg.com/png?v=" + ROAD_VERSION + "&t=m&" + getZoomString(sourceCoordinate(coordinate)) };
    }

        @Override
        public Bitmap getTile(String tileQ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
  }

  public static class AerialProvider extends YahooProvider {
    public String[] getTileUrls(Coordinate coordinate) {
        return new String[] { "http://us.maps3.yimg.com/aerial.maps.yimg.com/tile?v=" + AERIAL_VERSION + "&t=a&" + getZoomString(sourceCoordinate(coordinate)) };
    }

        @Override
        public Bitmap getTile(String tileQ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
  }

  public static class HybridProvider extends YahooProvider {
    public String[] getTileUrls(Coordinate coordinate) {
      String under = new AerialProvider().getTileUrls(coordinate)[0];
      String over = "http://us.maps3.yimg.com/aerial.maps.yimg.com/png?v="+HYBRID_VERSION+"&t=h&"+getZoomString(sourceCoordinate(coordinate));
      return new String[] { under, over };
    }

        @Override
        public Bitmap getTile(String tileQ) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
  }

  public static Coordinate fromYahoo(Coordinate coord) {
    // Return column, row, zoom for Yahoo x, y, z.
    return new Coordinate((int)PApplet.pow(2, 18 - coord.zoom - 1), coord.column , 18 - coord.zoom);
  }

  public static Coordinate toYahoo(Coordinate coord) {
    // Return x, y, z for Yahoo tile column, row, zoom.
    return new Coordinate((int)PApplet.pow(2, coord.zoom - 1) - coord.row - 1, coord.column, 18 - coord.zoom);
  }

  public static Coordinate fromYahooRoad(Coordinate coord) {
    // Return column, row, zoom for Yahoo Road tile x, y, z.
    return fromYahoo(coord);
  }

  public static Coordinate toYahooRoad(Coordinate coord) {
    // Return x, y, z for Yahoo Road tile column, row, zoom.
    return toYahoo(coord);
  }

  public static Coordinate fromYahooAerial(Coordinate coord) {
    // Return column, row, zoom for Yahoo Aerial tile x, y, z.
    return fromYahoo(coord);
  }

  public static Coordinate toYahooAerial(Coordinate coord) {
    // Return x, y, z for Yahoo Aerial tile column, row, zoom.
    return toYahoo(coord);
  }

}

