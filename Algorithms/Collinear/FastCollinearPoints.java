import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FastCollinearPoints {
	private Point[] points;
	private ArrayList<InnerLineSegment> segments;
	private Map<Point, List<InnerLineSegment>> checkMap;

	public FastCollinearPoints(Point[] incomingPoints) {
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
		checkMap = new TreeMap<Point, List<InnerLineSegment>>();
		execute();
	} // finds all line segments containing 4 points
	public int numberOfSegments() {
		return segments.size();
	} 

	public LineSegment[] segments() {
		 LineSegment[] result = new  LineSegment[segments.size()];
		 int i=0;
		for (InnerLineSegment lineSegment : segments) {
			result[i++] = lineSegment.getLineSegment();
		}
		
		return result;
	}

	/**
	 * 
	 */
	private void execute() {
		segments  = new ArrayList<InnerLineSegment>();

		for (int i = 0; i < points.length-1; i++) {
			Point iPoint = points[i];
			Comparator<Point> slopeOrder = iPoint.slopeOrder();
			Point[] tmpPoints = new Point[points.length-i-1];
			for (int j = 0; j < tmpPoints.length; j++) {
				tmpPoints[j]=points[j+i+1];
			}
			Arrays.sort(tmpPoints, slopeOrder);
			int counter = 0;
			double currentSlope = Double.NaN;
			int firstIndex = 0;
			Point first = tmpPoints[firstIndex];
			for (int j = 0; j < tmpPoints.length; j++) {
				double jSlope = tmpPoints[j].slopeTo(iPoint);
				if (jSlope==currentSlope){
					counter++;
				} else {
					if (counter>2){
						Point last = tmpPoints[j-1];
						addSegment(first, last);
					}
					counter = 1;
					firstIndex = j;
					currentSlope = jSlope;
					
					
				}
				
				
			}
			if (counter>2){
				addSegment(first, tmpPoints[tmpPoints.length-1]);
			}
			
		}
		
		
	}
	private void addSegment(Point first, Point last) {
		if (checkMap.containsKey(first)){
			for (InnerLineSegment segment : checkMap.get(first)) {
				if (segment.getSlope()==first.slopeTo(last)){
					return;
				}
				if (segment.getSlope()==last.slopeTo(first)){
					return;
				}
			}
		}
		if (checkMap.containsKey(last)){
			for (InnerLineSegment segment : checkMap.get(last)) {
				if (segment.getSlope()==first.slopeTo(last)){
					return;
				}
				if (segment.getSlope()==last.slopeTo(first)){
					return;
				}
			}
		}
		InnerLineSegment segment = new InnerLineSegment(first, last);
		if (!checkMap.containsKey(first)){
			checkMap.put(first, new ArrayList<InnerLineSegment>());
		}
		checkMap.get(first).add(segment);
		if (!checkMap.containsKey(last)){
			checkMap.put(last, new ArrayList<InnerLineSegment>());
		}
		checkMap.get(last).add(segment);
		
		segments.add(segment);
	} 
	
	private class InnerLineSegment {
	    private final Point p;
	    private final Point q;

	    public InnerLineSegment(Point p, Point q) {
			if (p == null || q == null) {
	            throw new NullPointerException("argument is null");
	        }
	        this.p = p;
	        this.q = q;
	    }

		public LineSegment getLineSegment() {
			return new LineSegment(p, q);
		}

		public double getSlope() {
			return p.slopeTo(q);
		}
	    
	    
	}
	
	
}
