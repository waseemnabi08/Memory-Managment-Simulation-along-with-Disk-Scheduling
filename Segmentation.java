import java.util.*;
class Segment{
    private int base;
   private int limit;

    public Segment(int base, int limit) {
        this.base = base;
        this.limit = limit;
    }

    public int getBase() {
        return base;
    }

    public int getLimit() {
        return limit;
    }
}

public class Segmentation{
    private Segment[] segmentTable;
    private int segmentCount;

    public Segmentation(int segmentCount) {
        this.segmentCount = segmentCount;
        segmentTable = new Segment[segmentCount];
    }

    public void intialize(Segment[] segments){
        for(int i = 0; i < segmentCount; i++){
            segmentTable[i] = segments[i];
        }
    }

    //virtual to physical address conversion

    public int getPhysicalAddress(int segmentNumber, int offset){
        if(segmentNumber >= segmentCount){
            System.out.println("Segmentation Fault");
            return -1;
        }
        if(offset >= segmentTable[segmentNumber].getLimit()){
            System.out.println("Offset out of bound");
            return -1;
        }
        return segmentTable[segmentNumber].getBase() + offset;
    }
}