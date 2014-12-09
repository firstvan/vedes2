package firstvan.viewer;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JToolTip;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.Point; 
import java.awt.Rectangle;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;


public class Viewer
{
	final static private JXMapKit jmk = new JXMapKit();
	

	
  public static void main(String[] argv){
	  
	  TileFactoryInfo info = new OSMTileFactoryInfo();
	  DefaultTileFactory dinfo = new DefaultTileFactory(info);
	  jmk.setTileFactory(dinfo);
	 
	  
	  jmk.setZoom(5);
	  jmk.setAddressLocation(new GeoPosition(47.532130, 21.624180));
	  jmk.setAddressLocationShown(false);
	  List<GeoPosition> csm = new ArrayList<GeoPosition>();
	  Set<Waypoint> wayPoints = new HashSet<Waypoint>();
	  
	  
	  try{
		  Reader r = new Reader();
		  csm = r.getArray();
	  
	  
		  wayPoints.add(new DefaultWaypoint(r.starts()));
		  wayPoints.add(new DefaultWaypoint(r.last()));
		  
	  WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
	  waypointPainter.setWaypoints(wayPoints);
		  
	  RoutePainter routePainter = new RoutePainter(csm); 
		
	  List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
	  painters.add(routePainter);
	  painters.add(waypointPainter);
		
	  CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
	  jmk.getMainMap().setOverlayPainter(painter);	 
	  
	  
	  JFrame frame = new JFrame("Route");
	  frame.getContentPane().add(jmk);
	  frame.setSize(1280, 720);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setVisible(true);
    
	  }
	  catch(FileNotFoundException e) {System.out.println (e);
	  }
	  catch(URISyntaxException e) {System.out.println (e);
	  }
  }

}