# Computer-architecture-project
## Java Simulation of Von Neumann Architecture with Pipeline and Hazard Handling
The objective of this project is to develop a computer architecture simulator using Java that emulates the Von Neumann architecture. The simulation will incorporate a pipeline design and implement mechanisms to handle various hazards that can arise during the execution of assembly code.

The Von Neumann architecture is a fundamental computer architecture design that features a central processing unit (CPU), memory, and input/output devices. In this project, the architecture will be simulated in Java to provide a virtual environment for executing assembly code.

One key aspect of this simulation is the implementation of a pipeline. A pipeline allows for the concurrent execution of multiple instructions, breaking them down into different stages such as fetch, decode, execute, and write back. By incorporating a pipeline design, the simulation will demonstrate the benefits of parallel processing and instruction-level concurrency, leading to improved performance and throughput.

Additionally, the project will address the challenges posed by hazards in pipeline execution. Hazards are situations that can impede the smooth progress of instructions in a pipeline, such as data hazards, control hazards, and structural hazards. To ensure accurate and correct execution of assembly code, the simulator will implement techniques to detect and handle these hazards, such as forwarding data, stalling the pipeline, or reordering instructions.

The simulator will read input assembly code, emulate the fetch-decode-execute cycle, and maintain the pipeline's flow by managing dependencies, resolving hazards, and forwarding data when necessary. It will track the progress of instructions through the pipeline stages, updating the CPU's state accordingly.

Throughout the project, emphasis will be placed on code organization, modularity, and readability. Object-oriented programming principles will be utilized to design classes representing components of the Von Neumann architecture, such as the CPU, memory, pipeline stages, and instructions.

By developing this simulation, the project aims to provide a practical understanding of the Von Neumann architecture, pipeline design, and hazard handling techniques. The final result will be a functional Java program capable of executing assembly code within a simulated computer architecture, accurately emulating the pipeline stages and handling hazards to ensure proper instruction execution.
