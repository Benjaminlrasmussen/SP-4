a): Because they run one-by-one on the main thread.

b): Because it already has a run() method, it just needs the @Override annotation.

c): The big difference is that they're processed in parallel. They will be empty or null
because they're called in the main thread before another thread has not finished assigning the values
they call upon.

d): The parallel processing is faster.