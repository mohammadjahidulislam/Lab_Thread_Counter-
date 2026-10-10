\# Lab: Thread Counter



\*\*Problem ID:\*\* SEPM-001  

\*\*Title:\*\* Comparison of Static and Non-Static Variables in Multithreading  

\*\*Language:\*\* Java



\## 1. Objective



The objective of this experiment is to compare static and non-static counters in a multithreaded Java program. It also demonstrates how race conditions affect an unsynchronized shared counter.



\## 2. Experimental Design



Each thread increments:

\- A shared static counter.

\- Its own non-static instance counter.



Two modes are tested:



\- \*\*Thread-safe mode:\*\* Uses `AtomicLong` for the shared counter.

\- \*\*Unsafe mode:\*\* Uses an ordinary `static long` without synchronization.



The `join()` method ensures that all threads finish before the final results are calculated.



\## 3. Formula



Expected count:



`Expected = Number of threads × Increments per thread`



Absolute difference:



`Difference = |Static count − Non-static total|`



Percentage difference:



`Percentage difference = (Difference / Non-static total) × 100`



If both counts are zero, the percentage difference is defined as 0%. If the non-static total is zero but the difference is nonzero, it is undefined.



\## 4. Test Inputs



| Test | Threads | Increments per thread |

|---|---:|---:|

| TC1 | 1 | 1,000 |

| TC2 | 2 | 10,000 |

| TC3 | 5 | 10,000 |

| TC4 | 10 | 50,000 |

| TC5 | 20 | 50,000 |

| TC6 | 50 | 50,000 |

| TC7 | 100 | 50,000 |



Both modes are executed for all test cases. The unsafe experiment is repeated at least five times for every thread count.



\## 5. Result Analysis



Record actual results in the following table after executing the program.



| Threads | Expected | Static count (unsafe) | Non-static total | Absolute difference | Difference (%) |

|---:|---:|---:|---:|---:|---:|

| 1 | 1,000 | — | 1,000 | — | — |

| 2 | 20,000 | — | 20,000 | — | — |

| 5 | 50,000 | — | 50,000 | — | — |

| 10 | 500,000 | — | 500,000 | — | — |

| 20 | 1,000,000 | — | 1,000,000 | — | — |

| 50 | 2,500,000 | — | 2,500,000 | — | — |

| 100 | 5,000,000 | — | 5,000,000 | — | — |



Replace the dashes with actual measured values. Keep the results for the thread-safe mode separately if necessary.



\## 6. Observations



1\. A static variable belongs to the class and is shared by its instances.

2\. A non-static variable belongs to an object. Each thread has its own object in this experiment.

3\. `join()` ensures that all threads complete before the totals are read.

4\. An unsynchronized static counter may lose updates when threads increment it concurrently.

5\. `AtomicLong` makes each increment operation thread-safe, so the static count should match the non-static total.

6\. Repeated unsafe runs may produce different results because thread scheduling and execution conditions can vary.

7\. Increasing the number of threads does not guarantee a steadily increasing percentage difference.



\## 7. Answers to Analysis Questions



\*\*Q1. What is the difference between static and non-static variables?\*\*  

A static variable belongs to the class, whereas a non-static variable belongs to an individual object.



\*\*Q2. Why do all threads share the static counter?\*\*  

Because all instances access the same class-level variable.



\*\*Q3. Why does each thread have its own non-static counter?\*\*  

Each thread creates and uses a separate `CounterTask` object.



\*\*Q4. Why is `join()` required?\*\*  

It waits for the thread to finish before the final count is calculated.



\*\*Q5. Why can the unsafe static count be lower than expected?\*\*  

Concurrent increments can overwrite each other, causing lost updates.



\*\*Q6. Does increasing thread count always increase the percentage difference?\*\*  

No. The difference depends on thread scheduling, hardware, and runtime conditions.



\*\*Q7. Why can repeated runs produce different results?\*\*  

The timing and interleaving of concurrent operations may change between runs.



\*\*Q8. What changes when `AtomicLong` is used?\*\*  

Its increment operation is atomic, preventing lost updates to the shared counter.



\*\*Q9. How can all threads share one instance counter?\*\*  

Create one shared counter object and pass that same object to every thread. Protect its shared counter with synchronization or an atomic variable.



\## 8. Conclusion



This experiment demonstrates that static and non-static variables differ in ownership, not inherently in thread safety. The thread-safe experiment should produce equal static and non-static totals. The unsynchronized experiment may produce a smaller static count because of race conditions and lost updates. However, the percentage difference is not guaranteed to increase monotonically with thread count.







