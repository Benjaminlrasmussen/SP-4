# When and why will we use Threads in our programs? #

- We use threads to finish a task quicker than a single thread would be able to.
It is also used when parallel processing is necessary for example when using a GUI
while also processing logic.

# What is the Race Condition Problem and how can you solve it? #

- The racing problem is one of the most common problems in multi-threaded programs.
It describes the problems that may arise when two or more threads interact with the
same resource concurrently. This may create incorrect results or corrupt data.

# Explain the Producer/Consumer-problem and how to solve it in modern Java Programs #

- Several problems, use an implementation of the BlockingQueue interface.

# Explain what Busy Waiting is and why it's a bad thing in a modern software system. #

- Busy waiting is when a thread is running but not performing any tasks. This is bad
because it eats up a lot of resources that could used for something beneficial.

# Describe Java's BlockingQueue interface, relevant implementations and methods relevant for the producer consumer problem. #

- Refer to the documentation.

### Explanation of the results ###
The slowest way to compute the fibonacci numbers was with 1 thread, the fastest was with 2 threads.
The reason it becomes slower with too many threads for the task is that it creates a lot of waiting
time when accessing the queues, especially since I use poll().