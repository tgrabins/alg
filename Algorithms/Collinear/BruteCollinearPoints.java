import java.util.Arrays;

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] segments;
	private int segmentsNumber = 0;

	public BruteCollinearPoints(Point[] points) {
		if (points==null){
			throw new NullPointerException("missing points");
		}
		this.points = points;
		for (Point point : points) {
			if (point == null){
				throw new NullPointerException("missing points");
			}
		}

	} // finds all line segments containing 4 points

	public int numberOfSegments() {
		if (segments==null){
			execute();
		}
		return segmentsNumber;
	} 

	public LineSegment[] segments() {
		if (segments==null){
			execute();
		}
		return segments;
	}

	private void execute() {
		segments = new LineSegment[points.length*points.length*points.length];
		Arrays.sort(points);
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
								segments[segmentsNumber++]= new LineSegment(points[i], points[m]);
							}
						}
					}
				}
			}
		}
	} 
}
