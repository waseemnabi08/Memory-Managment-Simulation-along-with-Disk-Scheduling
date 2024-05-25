import java.util.*;
public class DiskScheduling {
    private List<Integer> requests;
    private int headPosition;
    private int numCylinders;
    private List<Integer> headMovements;

    public DiskScheduling(List<Integer> requests, int headPosition, int numCylinders) {
        this.requests = new ArrayList<>(requests);
        this.headPosition = headPosition;
        this.numCylinders = numCylinders;
        this.headMovements = new ArrayList<>();
    }

    public int fcfs() {
        int totalHeadMovement = 0;
        int currentPosition = headPosition;
        headMovements.add(currentPosition);

        for (int request : requests) {
            totalHeadMovement += Math.abs(currentPosition - request);
            currentPosition = request;
            headMovements.add(currentPosition);
        }

        return totalHeadMovement;
    }

    public int sstf() {
        int totalHeadMovement = 0;
        int currentPosition = headPosition;
        headMovements.add(currentPosition);
        List<Integer> pendingRequests = new ArrayList<>(requests);

        while (!pendingRequests.isEmpty()) {
            int closestRequest = findClosestRequest(currentPosition, pendingRequests);
            totalHeadMovement += Math.abs(currentPosition - closestRequest);
            currentPosition = closestRequest;
            headMovements.add(currentPosition);
            pendingRequests.remove((Integer) closestRequest);
        }

        return totalHeadMovement;
    }

    private int findClosestRequest(int currentPosition, List<Integer> pendingRequests) {
        int closestRequest = pendingRequests.get(0);
        int minDistance = Math.abs(currentPosition - closestRequest);

        for (int request : pendingRequests) {
            int distance = Math.abs(currentPosition - request);
            if (distance < minDistance) {
                closestRequest = request;
                minDistance = distance;
            }
        }

        return closestRequest;
    }

    public int scan(String direction) {
        return scanOrCscan(direction, true);
    }

    public int cscan(String direction) {
        return scanOrCscan(direction, false);
    }

    private int scanOrCscan(String direction, boolean scan) {
        int totalHeadMovement = 0;
        int currentPosition = headPosition;
        headMovements.add(currentPosition);
        List<Integer> sortedRequests = new ArrayList<>(requests);
        sortedRequests.add(currentPosition);
        sortedRequests.sort(Integer::compareTo);

        int currentIndex = sortedRequests.indexOf(currentPosition);
        if ("up".equalsIgnoreCase(direction)) {
            for (int i = currentIndex + 1; i < sortedRequests.size(); i++) {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                currentPosition = sortedRequests.get(i);
                headMovements.add(currentPosition);
            }

            if (scan) {
                for (int i = currentIndex - 1; i >= 0; i--) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            } else {
                totalHeadMovement += Math.abs(currentPosition - (numCylinders - 1));
                currentPosition = 0;
                headMovements.add(currentPosition);
                for (int i = 0; i < currentIndex; i++) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            }
        } else {
            for (int i = currentIndex - 1; i >= 0; i--) {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                currentPosition = sortedRequests.get(i);
                headMovements.add(currentPosition);
            }

            if (scan) {
                for (int i = currentIndex + 1; i < sortedRequests.size(); i++) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            } else {
                totalHeadMovement += Math.abs(currentPosition - 0);
                currentPosition = numCylinders - 1;
                headMovements.add(currentPosition);
                for (int i = sortedRequests.size() - 1; i > currentIndex; i--) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            }
        }

        return totalHeadMovement;
    }

    public int look(String direction) {
        return lookOrClook(direction, true);
    }

    public int clook(String direction) {
        return lookOrClook(direction, false);
    }

    private int lookOrClook(String direction, boolean look) {
        int totalHeadMovement = 0;
        int currentPosition = headPosition;
        headMovements.add(currentPosition);
        List<Integer> sortedRequests = new ArrayList<>(requests);
        sortedRequests.add(currentPosition);
        sortedRequests.sort(Integer::compareTo);

        int currentIndex = sortedRequests.indexOf(currentPosition);
        if ("up".equalsIgnoreCase(direction)) {
            for (int i = currentIndex + 1; i < sortedRequests.size(); i++) {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                currentPosition = sortedRequests.get(i);
                headMovements.add(currentPosition);
            }

            if (look) {
                for (int i = currentIndex - 1; i >= 0; i--) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            } else {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(0));
                currentPosition = sortedRequests.get(0);
                headMovements.add(currentPosition);
                for (int i = 1; i < currentIndex; i++) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            }
        } else {
            for (int i = currentIndex - 1; i >= 0; i--) {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                currentPosition = sortedRequests.get(i);
                headMovements.add(currentPosition);
            }

            if (look) {
                for (int i = currentIndex + 1; i < sortedRequests.size(); i++) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            } else {
                totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(sortedRequests.size() - 1));
                currentPosition = sortedRequests.get(sortedRequests.size() - 1);
                headMovements.add(currentPosition);
                for (int i = sortedRequests.size() - 2; i > currentIndex; i--) {
                    totalHeadMovement += Math.abs(currentPosition - sortedRequests.get(i));
                    currentPosition = sortedRequests.get(i);
                    headMovements.add(currentPosition);
                }
            }
        }

        return totalHeadMovement;
    }

    public List<Integer> getHeadMovements() {
        return headMovements;
    }
}
