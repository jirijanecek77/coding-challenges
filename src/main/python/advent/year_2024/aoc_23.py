from collections import defaultdict

from utils import line_generator

filename = "data/test.txt"


def find_triplets_with_repeating_items(edges):
    # Create an adjacency list to represent the graph
    graph = defaultdict(set)
    for u, v in edges:
        graph[u].add(v)
        graph[v].add(u)

    triples = set()
    nodes = list(graph.keys())
    for index, a in enumerate(graph):
        for b in graph[a]:
            for c in nodes[index + 1 :]:
                if {a, b} <= graph[c]:
                    triples.add(frozenset((a, b, c)))

    return triples


def solve_01():
    with open(filename) as file:
        pairs = list(
            map(lambda line: tuple(line.split("-")), line_generator(file.readlines()))
        )

        result = find_triplets_with_repeating_items(pairs)
        for triplet in result:
            print(triplet, end="\n")
        print(
            len(
                list(
                    filter(
                        lambda triplet: any(e.startswith("t") for e in triplet), result
                    )
                )
            )
        )


if __name__ == "__main__":
    solve_01()
