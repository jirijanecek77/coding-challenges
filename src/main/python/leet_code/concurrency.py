import random
import threading
import time
from collections import deque


class Counter:
    def __init__(self):
        self.value = 0
        self.lock = threading.Lock()

    def increment(self):
        with self.lock:
            self.value += 1


def worker(counter):
    for _ in range(100000):
        counter.increment()


def test_counter():
    counter = Counter()
    t1 = threading.Thread(target=worker, args=(counter,))
    t2 = threading.Thread(target=worker, args=(counter,))

    t1.start()
    t2.start()
    t1.join()
    t2.join()

    print(f"Expected: 200000, Reality: {counter.value}")


class AzureApiGatekeeper:
    def __init__(self, max_concurrent_requests: int):
        self.max_concurrent_requests = max_concurrent_requests
        self.semaphore = threading.Semaphore(max_concurrent_requests)

    def call_azure_api(self, thread_id: int):
        print(f"[Thread {thread_id}] Wants to call the Azure API...")

        with self.semaphore:
            # --- Start of Critical Section (Simulating API call) ---
            print(f"  🔥 [Thread {thread_id}] ENTERED API - processing data...")
            time.sleep(random.uniform(0.5, 1.5))
            print(f"  ✔️ [Thread {thread_id}] LEFT API.")
            # --- End of Critical Section ---


def test_azure_api_gateway():
    gatekeeper = AzureApiGatekeeper(max_concurrent_requests=3)
    threads = [
        threading.Thread(target=gatekeeper.call_azure_api, args=(i,)) for i in range(8)
    ]

    for t in threads:
        t.start()
    for t in threads:
        t.join()


class BoundedBlockingQueue:

    def __init__(self, capacity: int):
        self.queue = deque()
        self.inbound_sem = threading.Semaphore(capacity)
        self.outbound_sem = threading.Semaphore(0)

    def enqueue(self, element: int):
        self.inbound_sem.acquire()
        self.queue.append(element)
        self.outbound_sem.release()

    def dequeue(self) -> int:
        self.outbound_sem.acquire()
        result = self.queue.popleft()
        self.inbound_sem.release()
        return result

    def size(self) -> int:
        return len(self.queue)


def test_bounded_blocking_queue():
    queue = BoundedBlockingQueue(2)

    results = []

    def producer():
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)

    def consumer():
        time.sleep(1)

        results.append(queue.dequeue())
        results.append(queue.dequeue())
        results.append(queue.dequeue())

    producer_thread = threading.Thread(target=producer)
    consumer_thread = threading.Thread(target=consumer)

    producer_thread.start()
    consumer_thread.start()

    producer_thread.join()
    consumer_thread.join()

    assert queue.size() == 0
    assert results == [1, 2, 3]
