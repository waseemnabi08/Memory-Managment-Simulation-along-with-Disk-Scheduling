import java.util.ArrayList;
import java.util.List;

class MemBlock {
    private int startAddress;
    private int size;
    private boolean allocated;
    private int processId;

    public MemBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.allocated = false;
        this.processId = -1;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getSize() {
        return size;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public int getProcessId() {
        return processId;
    }

    public boolean allocate(int processSize, int processId) {
        if (!allocated && processSize <= size) {
            allocated = true;
            this.processId = processId;
            return true;
        }
        return false;
    }

    public void deallocate() {
        allocated = false;
        processId = -1;
    }

    public void merge(MemBlock block) {
        size += block.getSize();
    }
}


public class MemAllocation {
    private List<MemBlock> blocks;
    private int nextProcessId = 1;

    public MemAllocation(int[] blockSize) {
        initializeMemoryBlocks(blockSize);
    }

    private void initializeMemoryBlocks(int[] blockSize) {
        blocks = new ArrayList<>();
        int startAddress = 0;
        for (int size : blockSize) {
            blocks.add(new MemBlock(startAddress, size));
            startAddress += size;
        }
    }

    public MemBlock allocate(int processSize, String type) {
        int allocatedIndex = -1;

        if (processSize > getFreeMemory() || processSize > 300) {
            throw new IllegalArgumentException("Insufficient memory to allocate process of size: " + processSize);
        }

        switch (type) {
            case "First-Fit":
                allocatedIndex = findFirstFit(processSize);
                break;
            case "Best-Fit":
                allocatedIndex = findBestFit(processSize);
                break;
            case "Worst-Fit":
                allocatedIndex = findWorstFit(processSize);
                break;
            default:
                throw new IllegalArgumentException("Invalid allocation type: " + type);
        }

        if (allocatedIndex != -1) {
            if (blocks.get(allocatedIndex).allocate(processSize, nextProcessId)) {
                nextProcessId++;
                return blocks.get(allocatedIndex);
            } else {
                throw new IllegalStateException("Unexpected error during allocation of block: " + allocatedIndex);
            }
        } else {
            throw new IllegalArgumentException("Insufficient memory to allocate process of size: " + processSize);
        }
    }

    public int findFirstFit(int processSize) {
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isAllocated() && blocks.get(i).getSize() >= processSize) {
                return i;
            }
        }
        return -1;
    }

    public int findBestFit(int processSize) {
        int bestIndex = -1;
        int bestSize = Integer.MAX_VALUE;
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isAllocated() && blocks.get(i).getSize() >= processSize && blocks.get(i).getSize() < bestSize) {
                bestIndex = i;
                bestSize = blocks.get(i).getSize();
            }
        }
        return bestIndex;
    }

    public int findWorstFit(int processSize) {
        int worstIndex = -1;
        int worstSize = Integer.MIN_VALUE;
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isAllocated() && blocks.get(i).getSize() >= processSize && blocks.get(i).getSize() > worstSize) {
                worstIndex = i;
                worstSize = blocks.get(i).getSize();
            }
        }
        return worstIndex;
    }

    public void deallocate(MemBlock block) {
        block.deallocate();
        mergeAdjacentFreeBlocks();
    }

    public MemBlock findBlock(int processId) {
        for (MemBlock block : blocks) {
            if (block.isAllocated() && block.getProcessId() == processId) {
                return block;
            }
        }
        return null;
    }

    public void mergeAdjacentFreeBlocks() {
        for (int i = 0; i < blocks.size() - 1; i++) {
            if (!blocks.get(i).isAllocated() && !blocks.get(i + 1).isAllocated()) {
                blocks.get(i).merge(blocks.get(i + 1));
                blocks.remove(i + 1);
                i--; // Move back one index to check the merged block with the next block
            }
        }
    }

    public int getAllocatedMemory() {
        int allocatedMemory = 0;
        for (MemBlock block : blocks) {
            if (block.isAllocated()) {
                allocatedMemory += block.getSize();
            }
        }
        return allocatedMemory;
    }

    public int getFreeMemory() {
        int freeMemory = 0;
        for (MemBlock block : blocks) {
            if (!block.isAllocated()) {
                freeMemory += block.getSize();
            }
        }
        return freeMemory;
    }

    public int getLeftover(MemBlock block, int processSize) {
        return block.getSize() - processSize;
    }


}


