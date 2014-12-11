package firstvan.viewer;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import java.io.ByteArrayOutputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class Viewer
{
	final static private JXMapKit jmk = new JXMapKit();
	final static private List<GeoPosition> csm = new ArrayList<>();
	final static private Set<Waypoint> wayPoints = new HashSet<>();
	static private javax.swing.JButton startButton;
	static GeoPosition p1 = new GeoPosition(0, 0);
	static GeoPosition p2 = new GeoPosition(0, 0);	
	static JFrame frame = new JFrame("Route");
	
	public static void savePoint(int i, GeoPosition gp)
	{
		if(i == 1)
		{
			p1 = gp;
		}
		else if(i == 2)
		{
			p2 = gp;
		}
		
		frissit();
	}
	
	public static void frissit()
	{

		Set<Waypoint> waypoints;
		waypoints = new HashSet<>();
				
		if(p1 != new GeoPosition(0, 0)){
			waypoints.add(new DefaultWaypoint(p1));
		}
		if(p2 != new GeoPosition(0, 0)){
			waypoints.add(new DefaultWaypoint(p2));
		}
				
		WaypointPainter<Waypoint> waypointPainter  = new WaypointPainter<>();
		waypointPainter.setWaypoints(waypoints);
		
		List<Painter<JXMapViewer>> painters;
		painters = new ArrayList<>();
		painters.add(waypointPainter);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
		jmk.getMainMap().setOverlayPainter(painter);	
	}
	
	public static void draw(){
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
		  waypointPainter.setWaypoints(wayPoints);
			  
		  RoutePainter routePainter = new RoutePainter(csm); 
			
		  List<Painter<JXMapViewer>> painters;
		  painters = new ArrayList<>();
		  painters.add(routePainter);
		  painters.add(waypointPainter);
			
		  CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
		  jmk.getMainMap().setOverlayPainter(painter);	 
		
	}
	
	public static void startB() {
		try{
		
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			String cmd = "./../osm-routing-bgl/dist/osm-routing-bgl";// ../debrecen.osm "+p1.getLatitude() + " " + p1.getLongitude() + " "+p2.getLatitude()+" "+p2.getLatitude();
			
			CommandLine cmdLine = CommandLine.parse(cmd);
			cmdLine.addArgument("../debrecen.osm");
			cmdLine.addArgument(String.valueOf(p1.getLatitude()));
			cmdLine.addArgument(String.valueOf(p1.getLongitude()));
			cmdLine.addArgument(String.valueOf(p2.getLatitude()));
			cmdLine.addArgument(String.valueOf(p2.getLongitude()));
			
			
			PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			
			executor.execute(cmdLine);
			
			
			String kiir = outputStream.toString();
			String[] ki = kiir.split("\n");
			if(ki[0].equals("Route not found")){
				JOptionPane.showMessageDialog(frame, kiir);
			}
			else{
			
			
				csm.clear();
			
				for(String a : ki )
				{
					String[] temp = a.split(" ");
					double tempLat=Double.parseDouble(temp[0]);
					double tempLong=Double.parseDouble(temp[1]);
					csm.add(new GeoPosition(tempLat, tempLong));
				}
			
			
			
	  
				wayPoints.clear();
		  		wayPoints.add(new DefaultWaypoint(csm.get(0)));
		  		wayPoints.add(new DefaultWaypoint(csm.get(csm.size()-1)));
		  		draw();
			}
	  }
	  catch(FileNotFoundException e) {System.out.println (e);
	  }
	  catch(IOException e) {System.out.println (e);
	  }
	}
	
	
  public static void main(String[] argv){
	  
      startButton = new javax.swing.JButton();
	  startButton.setText("Indít");
	  startButton.addActionListener(new java.awt.event.ActionListener(){
		  @Override
		  public void actionPerformed(java.awt.event.ActionEvent evn){
			  startB();
		  }
	  });
	  
      
      
      
        TileFactoryInfo info = new OSMTileFactoryInfo();
	DefaultTileFactory dinfo = new DefaultTileFactory(info);
	jmk.setTileFactory(dinfo);
	 
	  
	jmk.setZoom(5);
	jmk.setAddressLocation(new GeoPosition(47.532130, 21.624180));
	jmk.setAddressLocationShown(false);
	  
	jmk.getMainMap().addMouseListener(new MouseListener(){
		  @Override
		  public void mouseClicked(MouseEvent e)
		  {

			  GeoPosition click1 = jmk.getMainMap().convertPointToGeoPosition(e.getPoint());
			  if(e.getButton() == MouseEvent.BUTTON1)
				  savePoint(1, click1);
			  else if(e.getButton() == MouseEvent.BUTTON3)
				  savePoint(2, click1);
		  }
		  
		  @Override
		  public void mouseExited(MouseEvent e)
		  {
			  //pass
		  }

		  @Override
		  public void mouseEntered(MouseEvent e)
		  {
			  //pass
		  }

		  @Override
		  public void mousePressed(MouseEvent e)
		  {
			  //pass
		  }

		  @Override
		  public void mouseReleased(MouseEvent e)
		  {
			  //pass
		  }
	});
	  


	  
	frame.setLayout(new BorderLayout());	  
	
	frame.add(startButton, BorderLayout.NORTH);
	frame.add(jmk);

	frame.setSize(1280, 720);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);	
	  
	  
  }

}