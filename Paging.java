import java.util.*;

public class Paging {

    private static int pagesize=4;

    private int pagetable[]= {5,6,1,2};

   

    public int Translator(int virtualaddress) {
        //through exception if virtual address does not bounds to page table

        try {
            int pageIndex = virtualaddress / pagesize;
            int offset = virtualaddress % pagesize;
            int frame = pagetable[pageIndex];
            int physicaladdress = frame * pagesize + offset;
            return physicaladdress;
        } catch (Exception e) {
            //return exception if virtual address does not bounds to page table
            throw new IllegalArgumentException("Virtual address does not bounds to page table");
        }

    }
    

}
class PageTable {
    private int[] table;

    public PageTable(int size) {
        table = new int[size];
        // Initialize page table entries with invalid values
        for (int i = 0; i < size; i++) {
            table[i] = -1;
        }
    }

    public void setPageTableEntry(int pageNumber, int frameNumber) {
        if (pageNumber >= 0 && pageNumber < table.length && frameNumber >= 0 && frameNumber < table.length) {
            table[pageNumber] = frameNumber;
        } else {
            throw new IndexOutOfBoundsException("Invalid page or frame number");
        }
    }

    public int getFrameNumber(int pageNumber) {
        if (pageNumber >= 0 && pageNumber < table.length) {
            return table[pageNumber];
        } else {
            throw new IndexOutOfBoundsException("Invalid page number");
        }
    }
}