import queue
import random
import threading
import time


class CoffeeShop:
    def __init__(self):
        # 1. Create a standard Lock
        self.lock = threading.Lock()

        # 2. Create a Condition variable bound to our lock
        self.coffee_condition = threading.Condition(self.lock)

        # Shared state variable
        self.coffee_ready = False
        self.waiting_customers = 0
        self.shop_open = True

    def customer_wait_for_coffee(self):
        customer_name = threading.current_thread().name
        print(f"[{customer_name}] Entering the coffee shop...")

        # Acquire the lock using the 'with' statement
        with self.lock:
            self.waiting_customers += 1
            self.coffee_condition.notify_all()

            # Loop while the coffee is NOT ready
            while not self.coffee_ready:
                print(f"[{customer_name}] Coffee is not ready yet. Going to wait...")

                self.coffee_condition.wait()

            # If we exited the while loop, it means coffee is ready!
            print(f"[{customer_name}] Perfect! My coffee is ready. *Sips coffee*")
            self.coffee_ready = False
            self.waiting_customers -= 1
            # self.coffee_condition.notify_all()

    def barista_make_coffee(self):
        with self.lock:
            while self.waiting_customers > 0 or self.shop_open:

                # PODMÍNKA A: Pokud nikdo nečeká, barista spí a odpočívá
                while self.waiting_customers == 0 and self.shop_open:
                    print("[Barista] No customers in line. Resting...")
                    self.coffee_condition.wait()

                # PODMÍNKA B: Pokud už nikdo nečeká a zavíráme, barista končí
                if self.waiting_customers == 0 and not self.shop_open:
                    break

                # PODMÍNKA C: Pokud káva už na stole stojí a nikdo ji nevzal,
                # barista musí počkat, než uvaří další, aby nepřetekl stůl
                while self.coffee_ready:
                    self.coffee_condition.wait()

                # --- JEMNÉ ZAMYKÁNÍ (Fine-grained locking) ---
                # Uvolníme zámek během vaření, aby noví zákazníci
                # mohli v klidu vstupovat do kavárny a měnit self.waiting_customers
                print(
                    f"[Barista] Brewing a fresh cup of coffee... (Remaining demand: {self.waiting_customers})"
                )
                self.lock.release()
                time.sleep(random.uniform(0.4, 0.8))  # Simulace vaření kávy
                self.lock.acquire()
                # ---------------------------------------------

                # Káva je hotová, změníme stav a probudíme čekající zákazníky
                self.coffee_ready = True
                print("[Barista] Fresh coffee is ready on the counter!")
                self.coffee_condition.notify_all()

            print("[Barista] All customers served and shop is closed. Going home.")


#
# # --- Testing the implementation ---
# if __name__ == "__main__":
#     shop = CoffeeShop()
#
#     # Customers threads
#     customer_threads = [
#         threading.Thread(
#             name="Customer " + str(i), target=shop.customer_wait_for_coffee
#         )
#         for i in range(1, 6)
#     ]
#     # Create two threads
#     barista_thread = threading.Thread(target=shop.barista_make_coffee)
#
#     for thread in customer_threads:
#         thread.start()
#     time.sleep(0.5)  # Ensure customers arrives first and goes to sleep
#     barista_thread.start()
#
#     # Wait for both to finish
#     for thread in customer_threads:
#         thread.join()
#     barista_thread.join()
#
#     print("[Main] Coffee shop is closing. Simulation finished.")
#


class HighThroughputQueueShop:
    def __init__(self, counter_capacity: int = 3):
        self.coffee_counter = queue.Queue(maxsize=counter_capacity)

        self.shop_open = True
        self.shutdown_lock = threading.Lock()

    def customer_wait_for_coffee(self, customer_name: str):
        print(f"[{customer_name}] Entering the coffee shop and waiting for coffee...")

        # .get() automaticky zablokuje (uspí) zákazníka, pokud je fronta prázdná.
        # Jakmile barista vloží kávu do fronty, .get() se odblokuje a vrátí ji.
        grabbed_coffee = self.coffee_counter.get()

        print(f"[{customer_name}] Grabbed {grabbed_coffee}! *Sips*")

        # DŮLEŽITÉ: Oznámíme frontě, že úkol (zpracování této kávy) byl dokončen
        self.coffee_counter.task_done()

    def barista_loop(self, total_customers: int):
        print("[Barista] Shop is open, preparing coffee for customers...")

        # Barista prostě uvaří přesně tolik káv, kolik je celkem zákazníků
        for i in range(1, total_customers + 1):
            print(f"[Barista] Brewing coffee cup #{i}...")
            time.sleep(random.uniform(0.3, 0.6))  # Simulace vaření

            # .put() automaticky vloží kávu do fronty.
            # Pokud by byla fronta (pult) plná, .put() baristu automaticky
            # zablokuje a počká, dokud nějaký zákazník kávu neodebere.
            self.coffee_counter.put(f"Cup-{i}")
            print(f"[Barista] Placed Cup-{i} on the counter.")

        print(
            "[Barista] All ordered coffees are brewed. Waiting for everyone to pick them up..."
        )

        # .join() na frontě počká, dokud pro každou kávu nebylo zavoláno .task_done()
        self.coffee_counter.join()
        print("[Barista] Everyone is served. Going home.")


if __name__ == "__main__":
    total_customers_count = 5
    # Pult pojme maximálně 3 kávy
    shop = HighThroughputQueueShop(counter_capacity=3)

    # Nastartujeme baristu a předáme mu celkový počet zákazníků
    barista_thread = threading.Thread(
        target=shop.barista_loop, args=(total_customers_count,)
    )
    barista_thread.start()

    time.sleep(0.2)

    # Nastartujeme 5 zákazníků
    customer_threads = []
    for i in range(1, total_customers_count + 1):
        t = threading.Thread(
            target=shop.customer_wait_for_coffee, args=(f"Customer-{i}",)
        )
        customer_threads.append(t)
        t.start()

    # Počkáme na dokončení všech vláken
    for t in customer_threads:
        t.join()

    barista_thread.join()
    print("[Main] Simulation finished. Clean and safe.")
