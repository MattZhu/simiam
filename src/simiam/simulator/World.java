
package simiam.simulator;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import simiam.app.ControlApp;
import simiam.controller.Supervisor;
import simiam.robot.Robot;
import simiam.ui.Pose2D;
import simiam.ui.Vertex;

public class World
{
  private List<Robot> robots = new ArrayList<Robot>();

  private List<Obstacle> obstacles = new ArrayList<Obstacle>();

  private List<ControlApp> apps = new ArrayList<ControlApp>();

  public World()
  {

  }

  public void buildFromFile()
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    // Get the DOM Builder
    DocumentBuilder builder;
    try
    {
      builder = factory.newDocumentBuilder();
      Document document = builder.parse(getClass().getResourceAsStream("settings.xml"));
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++)
      {

        // We have encountered an <employee> tag.
        Node node = nodeList.item(i);
        if ( node instanceof Element )
        {
          if ( node.getNodeName().equals("app") )
          {
            addApp(node);
          }
          else if ( node.getNodeName().equals("robot") )
          {
            addRobot(node);
          }
          else if ( node.getNodeName().equals("obstacle") )
          {
            addObstacle(node);
          }
        }
      }

    }
    catch (ParserConfigurationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SAXException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(this.toString());
  }

  private void addApp(Node node)
  {
    String type = "simiam.app." + node.getAttributes().getNamedItem("type").getNodeValue();
    System.out.println("app type is:" + type);
    apps.add(new ControlApp(""));
  }

  public void addObstacle(Node node)
  {
	  Node child = node.getFirstChild();
	  Pose2D pose = null;
	  List<Vertex> geometry =null;
	  while (child != null)
	    {
	      if ( child instanceof Element )
	      {
	    	  if ( child.getNodeName().equals("pose") )
	          {
	            pose = parsePose(child);
	          }else if(child.getNodeName().equals("geometry")){
	        	  geometry=  parseGeometries(child);
	          }
	      }
	      child=child.getNextSibling();
	    }
	  Obstacle obstacle = new Obstacle(null,pose,geometry);
	this.obstacles.add(obstacle );
  }

  private  List<Vertex>  parseGeometries(Node node) {
	  List<Vertex> geometry=new ArrayList<Vertex>();
	  Node child = node.getFirstChild();
	  while (child != null)
	    {
	      if ( child instanceof Element )
	      {
	    	  if ( child.getNodeName().equals("point") )
	          {
	    		  double x = Double.valueOf(child.getAttributes().getNamedItem("x").getNodeValue());
	    		  double y = Double.valueOf(child.getAttributes().getNamedItem("y").getNodeValue());
	    		  Vertex v=new Vertex(x,y);
	    		  geometry.add(v);
	          }
	      }

	      child=child.getNextSibling();
	      }
	  return geometry;
	
}

public Robot addRobot(Node node)
  {
    String type = "simiam.robot." + node.getAttributes().getNamedItem("type").getNodeValue();
    System.out.println("Robot type: " + type);
    Node child = node.getFirstChild();
    Pose2D pose = null;
    Supervisor supv = null;
    while (child != null)
    {
      if ( child instanceof Element )
      {
        System.out.println("Robot  " + child.getNodeName());
        if ( child.getNodeName().equals("supervisor") )
        {
          String supvType = "simiam.controller."
              + child.getAttributes().getNamedItem("type").getNodeValue();
          try
          {
            supv = (Supervisor) Class.forName(supvType).newInstance();
          }
          catch (InstantiationException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          catch (IllegalAccessException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          catch (ClassNotFoundException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        else if ( child.getNodeName().equals("pose") )
        {
          pose = parsePose(child);
        }
        else if ( child.getNodeName().equals("driver") )
        {

        }
      }
      child = child.getNextSibling();
    }
    try
    {
      Class<?> rClass = Class.forName(type);
      Constructor<?> constructor = rClass.getConstructor(JPanel.class, Pose2D.class);

      Robot r = (Robot) constructor.newInstance(null, pose);
      this.robots.add(r);
      r.attach_supervisor(supv);
      return r;
    }
    catch (IllegalArgumentException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (InvocationTargetException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SecurityException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (NoSuchMethodException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

private Pose2D parsePose(Node child) {
	Pose2D pose;
	double x = Double.valueOf(child.getAttributes().getNamedItem("x").getNodeValue());
	  double y = Double.valueOf(child.getAttributes().getNamedItem("y").getNodeValue());
	  double theta = Double.valueOf(child.getAttributes().getNamedItem("theta").getNodeValue());
	  pose = new Pose2D(x, y, theta);
	return pose;
}

  public List<Robot> getRobots()
  {
    return robots;
  }

  public void setRobots(List<Robot> robots)
  {
    this.robots = robots;
  }

  public List<Obstacle> getObstacles()
  {
    return obstacles;
  }

  public void setObstacles(List<Obstacle> obstacles)
  {
    this.obstacles = obstacles;
  }

  public List<ControlApp> getApps()
  {
    return apps;
  }

  public void setApps(List<ControlApp> apps)
  {
    this.apps = apps;
  }

@Override
public String toString() {
	return "World [robots=" + robots + ", obstacles=" + obstacles + ", apps="
			+ apps + "]";
}

  
}
