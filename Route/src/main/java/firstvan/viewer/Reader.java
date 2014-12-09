package firstvan.viewer;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;
import org.jxmapviewer.viewer.GeoPosition;
import java.util.List;

public class Reader
{
	
	private ArrayList<GeoPosition> mL = new ArrayList<GeoPosition>();
	

	public Reader() throws URISyntaxException, FileNotFoundException
	{
		File file = new File("way.txt");
		Scanner sc = new Scanner(file);
		
		
		while(sc.hasNext())
		{
			double lat = sc.nextDouble();
			double lng = sc.nextDouble();
			mL.add(new GeoPosition(lat, lng));
		}
		
	}
	
	
	List<GeoPosition> getArray(){
		return mL;
	}
	
	GeoPosition starts(){
		return mL.get(0);
	}
	
	GeoPosition last(){
		int meret = mL.size();
		return mL.get(meret-1);
	}
	
	/*
	List<GeoPosition> getArray(String jaratnev){
	 	List<GeoPosition> temp = new ArrayList<GeoPosition>();
	 	
	 	for(Megallok m : mL)
	 	{
	 		if(m.getJarat().equals(jaratnev))
	 		{
	 		System.out.println(m.getJarat());
	 			temp.add(new GeoPosition(m.getLat(), m.getLng()));
	 		}
	 	}
	 	return temp;
	 }
	 */

}