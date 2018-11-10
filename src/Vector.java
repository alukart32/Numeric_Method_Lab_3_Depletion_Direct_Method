import java.util.Arrays;
import java.util.Objects;

public class Vector {
  int size;
  private double[] elements;

  public Vector(int size) {
    this.size = size;
    this.elements = new double[size];
  }

  public int getSize() {
    return size;
  }

  public double[] getVector() {
    return elements;
  }

  public double getData(int index){
    return this.elements[index];
  }

  public void setData(int index, double data) {
    this.elements[index] = data;
  }

  public void show(){
    for (int i = 1; i < this.size; i++) {
      System.out.printf("%.3f%n", this.elements[i]);//"%.3f%n",
    }
  }

  @Override
  public String toString() {
    return getClass().getName() +" {" +
            "size=" + size +
            ", elements=" + Arrays.toString(elements) +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector vector = (Vector) o;
    return size == vector.size &&
            Arrays.equals(elements, vector.elements);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(size);
    result = 31 * result + Arrays.hashCode(elements);
    return result;
  }
}
