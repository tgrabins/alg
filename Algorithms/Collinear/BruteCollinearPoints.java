import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private Point[] points;
	private ArrayList<LineSegment> segments;

	public BruteCollinearPoints(Point[] incomingPoints) {
		if (incomingPoints==null){
			throw new NullPointerException("missing points");
		}
		this.points = new Point[incomingPoints.length];
		for (int i = 0; i < incomingPoints.length; i++) {
			if (incomingPoints[i] == null){
				throw new NullPointerException("missing points");
			}
			points[i]=incomingPoints[i];
		}
		Arrays.sort(points);
		for (int i = 0; i < points.length-1; i++) {
			if (points[i].compareTo(points[i+1])==0){
				throw new java.lang.IllegalArgumentException("Duplicated points");
			}
		}
		execute();
	} // finds all line segments containing 4 points

	public int numberOfSegments() {
		return segments.size();
	} 

	public LineSegment[] segments() {
		return segments.toArray(new LineSegment[segments.size()]);
	}

	private void execute() {
		segments  = new ArrayList<LineSegment>();
		for (int i = 0; i < points.length; i++) {
			for (int j = i+1; j < points.length; j++) {
				double ijSlope = points[j].slopeTo(points[i]);
				//System.out.println(j + " ijSlope " +ijSlope);
				for (int k = j+1; k < points.length; k++) {
					double ikSlope = points[k].slopeTo(points[i]);
					//System.out.println(k + " ikSlope " +ikSlope);
					if (ijSlope != ikSlope){
						continue;
					} else {
						for (int m = k+1; m < points.length; m++) {
							double imSlope = points[m].slopeTo(points[i]);
							//System.out.println(m + " imSlope " +imSlope);
							if (ijSlope != imSlope){
								continue;
							} else {
								//System.out.println("added");
								segments.add(new LineSegment(points[i], points[m]));
							}
						}
					}
				}
			}
		}
	} 
}
