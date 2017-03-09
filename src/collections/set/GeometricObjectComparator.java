package collections.set;

import java.util.Comparator;

import abstract_interfaces.GeometricObject;

/**
 * 用于TreeSet的比较器
 * @author tianlong
 *
 */
public class GeometricObjectComparator
    implements Comparator<GeometricObject>, java.io.Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public int compare(GeometricObject o1, GeometricObject o2) {
    double area1 = o1.getArea();
    double area2 = o2.getArea();

    if (area1 < area2)
      return -1;
    else if (area1 == area2)
      return 0;
    else
      return 1;
  }
}
