# Memory Management Simulation

This project is a Java Swing application that simulates various memory management techniques, including Memory Allocation, Segmentation, Paging, and Disk Scheduling. The user interface is designed to be user-friendly and visually appealing, with an organized layout and interactive elements.

## Features

- **Memory Allocation**: Demonstrates different memory allocation techniques.
- **Segmentation**: Illustrates the concept of memory segmentation.
- **Paging**: Provides an example of the paging memory management scheme.
- **Disk Scheduling**: Simulates various disk scheduling algorithms.
- **Exit Button**: A convenient way to exit the application.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or NetBeans

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/waseemnabi08/memory-management-simulation-along-with-disk-scheduling.git
   ```
2. **Navigate to the project directory**:
   ```sh
   cd memory-management-simulation
   ```

3. **Open the project in your IDE**:
   - Import the project as a Maven project if your IDE supports it.
   - Ensure all dependencies are resolved.

### Running the Application

1. **Compile and run the application**:
   - In your IDE, locate the `MemoryManagement.java` file.
   - Run the `main` method in the `MemoryManagement` class.
   - Alternatively, use the following command in the terminal:
     ```sh
     javac MemoryManagement.java
     java MemoryManagement
     ```

## Project Structure

- **MemoryManagement.java**: The main class that initializes and sets up the user interface.
- **Panels**: Classes that create and manage individual panels for different memory management techniques.
  - `MemoryAllocation.java`
  - `Segmentation.java`
  - `Paging.java`
  - `DiskScheduling.java`

## User Interface

### Main Frame

The main frame of the application is titled "Memory Management". It features a gradient header, a tabbed pane for different memory management techniques, and an exit button at the bottom.

### Tabbed Pane

The tabbed pane includes four tabs:

- **Memory Allocation**
- **Segmentation**
- **Paging**
- **Disk Scheduling**

Each tab contains a panel that demonstrates the corresponding memory management technique.

### Exit Button

The exit button is styled with a gradient background and rounded corners. Clicking the button will close the application.

## Contributing

1. **Fork the repository**.
2. **Create a new branch**:
   ```sh
   git checkout -b feature/YourFeature
   ```
3. **Commit your changes**:
   ```sh
   git commit -m 'Add some feature'
   ```
4. **Push to the branch**:
   ```sh
   git push origin feature/YourFeature
   ```
5. **Open a pull request**.


## Acknowledgements

- Java Swing for the user interface components.
- Open-source libraries and tools that facilitated the development of this project.

