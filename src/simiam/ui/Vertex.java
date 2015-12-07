package simiam.ui;

public class Vertex
{
  private double x;
  private double y;
  private double z;
  public Vertex(){
    
  }
  public Vertex(Vertex v){
    this.x=v.x;
    this.y=v.y;
    this.z=v.z;
  }
  public Vertex(double x,double y){
    this.x=x;
    this.y=y;
    this.z=1;
  }
  public Vertex(double x,double y, double z){
    this.x=x;
    this.y=y;
    this.z=z;
  }
  public double getX()
  {
    return x;
  }
  public void setX(double x)
  {
    this.x = x;
  }
  public double getY()
  {
    return y;
  }
  public void setY(double y)
  {
    this.y = y;
  }
  public double getZ()
  {
    return z;
  }
  public void setZ(double z)
  {
    this.z = z;
  }
  
  public void add(Vertex v){
    this.x+=v.x;
    this.y+=v.y;
    this.z+=v.z;
  }
  
  public void div(double div){
    this.x/=div;
    this.y/=div;
    this.z/=div;
  }
  
  public double distance(Vertex v){
    return Math.sqrt((this.x-v.x)*(this.x-v.x)+(this.y-v.y)*(this.y-v.y));
  }
  
  public Vertex transfer(double tm[][]){
    Vertex v=new Vertex();
    v.x=tm[0][0]*x+tm[0][1]*y+tm[0][2]*z;
    v.y=tm[1][0]*x+tm[1][1]*y+tm[1][2]*z;
    v.z=tm[2][0]*x+tm[2][1]*y+tm[2][2]*z;
    return v;
  }
  @Override
  public String toString()
  {
    return "Vertex (" + x + "," + y +")";
  }
  
  
}
