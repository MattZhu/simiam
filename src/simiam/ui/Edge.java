package simiam.ui;

public class Edge
{
  private Vertex start;
  private Vertex end;
  public Edge()
  {
    
  }
  public Edge(Vertex start, Vertex end)
  {
    this.start = start;
    this.end = end;
  }
  public Vertex getStart()
  {
    return start;
  }
  public void setStart(Vertex start)
  {
    this.start = start;
  }
  public Vertex getEnd()
  {
    return end;
  }
  public void setEnd(Vertex end)
  {
    this.end = end;
  }
  
  public Edge transfer(double tm[][]){
   Edge edge=new Edge();
   edge.start=this.start.transfer(tm);
   edge.end=this.end.transfer(tm);
   return edge;
  }
  
  public Vertex intersection(Edge e){
    Vertex v=null;
    
    double m_y_13 = (start.getY()-e.getStart().getY());
    double m_x_13 = (start.getX()-e.getStart().getX());
    double m_x_21 = (end.getX()-start.getX());
    double m_y_21 = (end.getY()-start.getY());    
    double m_x_43 = (e.getEnd().getX()-e.getStart().getX());
    double m_y_43 = (e.getEnd().getY()-e.getStart().getY());
    
    double n_edge_a = (m_x_43*m_y_13)-(m_y_43*m_x_13);
    double n_edge_b = (m_x_21*m_y_13)-(m_y_21*m_x_13);
    double d_edge_ab = (m_y_43*m_x_21)-(m_x_43*m_y_21);
    
    double u_a = (n_edge_a/d_edge_ab);
    double u_b = (n_edge_b/d_edge_ab);
      

    if((u_a >= 0) & (u_a <= 1) & (u_b >= 0) & (u_b <= 1)){

      double intersect_set_x = start.getX()+(m_x_21*u_a);
      double intersect_set_y = start.getY()+(m_y_21*u_a);
      v= new Vertex(intersect_set_x,intersect_set_y);
    }    
    return v;
    
  }
  
  
  
  @Override
  public String toString()
  {
    return "Edge(" + start + "=>" + end + ")";
  }

}
