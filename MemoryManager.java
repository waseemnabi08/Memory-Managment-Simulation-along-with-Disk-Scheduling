import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MemoryManager {

    private JFrame mainFrame;
    private JComboBox<String> allocationTypeComboBox;
    private JTextField processSizeTextField;
    private JTextArea logTextArea;
    private MemAllocation memAllocation;
    private Segmentation segmentation;
    private Paging paging;
    private DiskScheduling diskScheduling;

    public MemoryManager(int [] blockSize, Segment [] segments) {
        memAllocation = new MemAllocation(blockSize);
        segmentation = new Segmentation(5);
        segmentation.intialize(segments);
        paging = new Paging();
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Memory Management");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
    
        // Create a header panel with a background color and title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204)); // Blue background color
        JLabel headerLabel = new JLabel("Memory Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE); // White text color
        headerPanel.add(headerLabel);
    
        // Add the header panel to the top of the main frame
        mainFrame.add(headerPanel, BorderLayout.NORTH);
    
        // Create a tabbed pane for different memory management techniques
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 16));
    
        // Add tabs with descriptive names
        tabbedPane.addTab("Memory Allocation", createMemoryAllocationPanel());
        tabbedPane.addTab("Segmentation", createSegmentationPanel());
        tabbedPane.addTab("Paging", createPagingPanel());
        tabbedPane.addTab("Disk Scheduling", createDiskSchedulingPanel());
    
        // Add the tabbed pane to the center of the main frame
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
    
        // Create an exit button with a red background color at the bottom
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Verdana", Font.BOLD, 18));
        exitButton.setBackground(new Color(204, 0, 0)); // Red background color
        exitButton.setForeground(Color.RED); // White text color
        exitButton.setToolTipText("Click to exit the application");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    
        // Add the exit button to the bottom of the main frame
        mainFrame.add(exitButton, BorderLayout.SOUTH);
    
        // Center the main frame on the screen
        mainFrame.setLocationRelativeTo(null);
    
        // Make the main frame visible
        mainFrame.setVisible(true);
    }
    
    

    private JPanel createMemoryAllocationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Memory Allocation Manager");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(titleLabel, gbc);

        JLabel allocationTypeLabel = new JLabel("Allocation Type:");
        allocationTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(allocationTypeLabel, gbc);

        String[] allocationTypes = {"Select", "First-Fit", "Best-Fit", "Worst-Fit"};
        allocationTypeComboBox = new JComboBox<>(allocationTypes);
        allocationTypeComboBox.setSelectedIndex(0);
        allocationTypeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel.add(allocationTypeComboBox, gbc);

        JLabel processSizeLabel = new JLabel("Process Size:");
        processSizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(processSizeLabel, gbc);

        processSizeTextField = new JTextField(10);
        processSizeTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel.add(processSizeTextField, gbc);

        JButton allocateButton = new JButton("Allocate");
        allocateButton.setFont(new Font("Verdana", Font.BOLD, 16));
        allocateButton.setBackground(new Color(0, 153, 76));
        allocateButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 10, 0);
        gbc.gridwidth = 2;
        panel.add(allocateButton, gbc);

      /*  JButton deallocateButton = new JButton("Deallocate");
        deallocateButton.setFont(new Font("Arial", Font.BOLD, 16));
        deallocateButton.setBackground(new Color(204, 0, 0));
        deallocateButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.gridwidth = 2;
        panel.add(deallocateButton, gbc);*/

        logTextArea = new JTextArea(8, 45);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        logTextArea.setEditable(false);
        logTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        allocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allocateMemory();
            }
        });

        /*deallocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deallocateMemory();
            }
        });*/

        return panel;
    }



    
    private JPanel createSegmentationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
    
        JLabel titleLabel = new JLabel("Segmentation Memory Manager");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(titleLabel, gbc);
    
        JLabel segmentLabel = new JLabel("Segment Number:");
        segmentLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(segmentLabel, gbc);
    
        JTextField segmentField = new JTextField(10);
        segmentField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(segmentField, gbc);
    
        JLabel offsetLabel = new JLabel("Offset:");
        offsetLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(offsetLabel, gbc);
    
        JTextField offsetField = new JTextField(10);
        offsetField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(offsetField, gbc);
    
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Verdana", Font.BOLD, 14));
        calculateButton.setBackground(new Color(0, 102, 204));
        calculateButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(calculateButton, gbc);
    
        JTextArea resultArea = new JTextArea(5, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);
    
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int segmentNumber = Integer.parseInt(segmentField.getText());
                    int offset = Integer.parseInt(offsetField.getText());
                    int physicalAddress = segmentation.getPhysicalAddress(segmentNumber, offset);
                    segmentField.setText("");
                    offsetField.setText("");
                    resultArea.setText("");
                    if (physicalAddress != -1) {
                        resultArea.append(String.format("Segment Number: %d\nOffset: %d\nPhysical Address: %d\n\n", segmentNumber, offset, physicalAddress));
                    }

                    else{
                        JOptionPane.showMessageDialog(panel, "Segmentation Fault / Offset out of Bound", "Segmentation Fault", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid input. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        return panel;
    }
    
    
    private JPanel createPagingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
    
        JLabel titleLabel = new JLabel("Paging Manager");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(titleLabel, gbc);
    
        JLabel pageNumberLabel = new JLabel("Physical Address:");
        pageNumberLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(pageNumberLabel, gbc);
    
        JTextField pageNumberTextField = new JTextField(10);
        pageNumberTextField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pageNumberTextField, gbc);
    
        JButton getAddressButton = new JButton("Get Physical Address");
        getAddressButton.setFont(new Font("Verdana", Font.BOLD, 14));
        getAddressButton.setBackground(new Color(0, 102, 204));
        getAddressButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(getAddressButton, gbc);
    
        JTextArea logTextArea = new JTextArea(5, 40);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logTextArea.setEditable(false);
        logTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);
    
        getAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int virtualAddress = Integer.parseInt(pageNumberTextField.getText());
                    int physicalAddress = paging.Translator(virtualAddress);
                    pageNumberTextField.setText("");
                    logTextArea.setText("");
                    if (physicalAddress != -1) {
                        logTextArea.append(String.format("Virtual Address: %d\nPhysical Address: %d\n\n", virtualAddress, physicalAddress));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number for the page number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        return panel;
    }
    

    private static JPanel createDiskSchedulingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
    
        JLabel titleLabel = new JLabel("Disk Scheduling Manager");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(titleLabel, gbc);
    
        JLabel requestsLabel = new JLabel("Requests(Separated by comas):");
        requestsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(requestsLabel, gbc);
    
        JTextField requestsTextField = new JTextField(20);
        requestsTextField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(requestsTextField, gbc);
    
        JLabel headPositionLabel = new JLabel("Head Position:");
        headPositionLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(headPositionLabel, gbc);
    
        JTextField headPositionTextField = new JTextField(10);
        headPositionTextField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(headPositionTextField, gbc);
    
        JLabel numCylindersLabel = new JLabel("Number of tracks:");
        numCylindersLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(numCylindersLabel, gbc);
    
        JTextField numCylindersTextField = new JTextField(10);
        numCylindersTextField.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(numCylindersTextField, gbc);
    
        JLabel directionLabel = new JLabel("Direction:");
        directionLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(directionLabel, gbc);
    
        JComboBox<String> directionComboBox = new JComboBox<>(new String[]{"Select","Up", "Down"});
        directionComboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(directionComboBox, gbc);
    
        JLabel algorithmLabel = new JLabel("Scheduling Algorithm:");
        algorithmLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(algorithmLabel, gbc);
    
        JComboBox<String> algorithmComboBox = new JComboBox<>(new String[]{"Select", "FCFS", "SSTF", "SCAN", "C-SCAN", "LOOK", "C-LOOK"});
        algorithmComboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(algorithmComboBox, gbc);
    
        JButton calculateButton = new JButton("Simulate");
        calculateButton.setFont(new Font("Verdana", Font.BOLD, 14));
        calculateButton.setBackground(new Color(0, 102, 204));
        calculateButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(calculateButton, gbc);
    
        JTextArea resultArea = new JTextArea(10, 50); // Adjusted size of result area
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);
    
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                List<Integer> requests = Arrays.stream(requestsTextField.getText().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
                int headPosition = Integer.parseInt(headPositionTextField.getText());
                int numCylinders = Integer.parseInt(numCylindersTextField.getText());
                String direction = (String) directionComboBox.getSelectedItem();
                String algorithm = (String) algorithmComboBox.getSelectedItem();

                // Create DiskScheduling object
                DiskScheduling diskScheduling = new DiskScheduling(requests, headPosition, numCylinders);

                // Calculate based on selected algorithm
                int totalHeadMovement = 0;
                switch (algorithm) {
                case "FCFS":
                totalHeadMovement = diskScheduling.fcfs();
                break;
                case "SSTF":
                totalHeadMovement = diskScheduling.sstf();
                break;
                case "SCAN":
                totalHeadMovement = diskScheduling.scan(direction);
                break;
                case "C-SCAN":
                totalHeadMovement = diskScheduling.cscan(direction);
                break;
                case "LOOK":
                totalHeadMovement = diskScheduling.look(direction);
                break;
                case "C-LOOK":
                totalHeadMovement = diskScheduling.clook(direction);
                break;
                default:
                break;
                }


                resultArea.setText("");
                resultArea.append("Total head movements: " + totalHeadMovement + "\n\n");
                resultArea.append("Algorithm: " + algorithm + "\n\n");
                resultArea.append("Head movement sequence:\n");
                // Format and append head movement sequence
            List<Integer> headMovements = diskScheduling.getHeadMovements();
            for (int i = 0; i < headMovements.size(); i++) {
                if (i % 10 == 0 && i != 0) {
                    resultArea.append("\n"); // Add newline every 10 entries
                }
                resultArea.append(headMovements.get(i) + "\t");
            }

                } 
                catch(Exception E){
                    JOptionPane.showMessageDialog(panel, "Invalid input. Please input valid entries", "Input Error", JOptionPane.ERROR_MESSAGE);

                }     
        }
    });
    
        return panel;
    }
    

    private void allocateMemory() {
        try {
            int processSize = Integer.parseInt(processSizeTextField.getText());
            String allocationType = (String) allocationTypeComboBox.getSelectedItem();
            MemBlock allocatedBlock = memAllocation.allocate(processSize, allocationType);
            logTextArea.setText("");
            // Display allocated block information
            logTextArea.append("Allocated block:" + "\n" + "Process ID: " + allocatedBlock.getProcessId() + ", Size: " +
                    allocatedBlock.getSize() + ", Block: " + allocatedBlock.getStartAddress() + " - " +
                    (allocatedBlock.getStartAddress() + allocatedBlock.getSize()) + "\n");
             processSizeTextField.setText("");
             allocationTypeComboBox.setSelectedIndex(0);       
            
            // Get memory statistics
            int allocatedMemory = memAllocation.getAllocatedMemory();
            int freeMemory = memAllocation.getFreeMemory();
            int leftover = memAllocation.getLeftover(allocatedBlock, processSize);
            
            // Display memory statistics
            logTextArea.append("Allocated Memory: " + allocatedMemory + "\n");
            logTextArea.append("Free Memory: " + freeMemory + "\n");
            logTextArea.append("Leftover Memory: " + leftover + "\n");
            
            // Clear input fields
            processSizeTextField.setText("");
            allocationTypeComboBox.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid process size.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Allocation Error", JOptionPane.ERROR_MESSAGE);
        }  
        catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Allocation Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Allocation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deallocateMemory() {
        try {
            int processId = Integer.parseInt(processSizeTextField.getText());
             MemBlock blockToDeallocate = memAllocation.findBlock(processId); // assuming such a method exists
            memAllocation.deallocate(blockToDeallocate);
            logTextArea.append("Process " + processId + " deallocated.\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid process ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Deallocation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int[] blockSize = {100, 200, 50, 300, 150};
            Segment[] segments = new Segment[5];
            segments[0] = new Segment(219, 600);
            segments[1] = new Segment(2300, 14);
            segments[2] = new Segment(90, 100);
            segments[3] = new Segment(1327, 580);
            segments[4] = new Segment(1952, 96);
            MemoryManager gui = new MemoryManager(blockSize, segments);
            gui.mainFrame.setVisible(true);
        });
    }
}

