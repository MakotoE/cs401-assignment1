from typing import List


def intersection(list_a: List, list_b: List) -> List:
    result = []
    for a in list_a:
        for b in list_b:
            if a == b:
                result.append(b)

    return result


def merge_sort(l: List) -> List:
    if len(l) <= 1:
        return l

    left = merge_sort(l[:int(len(l) / 2)])
    right = merge_sort(l[int(len(l) / 2):])

    result = []

    while len(left) > 0 and len(right) > 0:
        if left[0] <= right[0]:
            result.append(left[0])
            left.pop(0)
        else:
            result.append(right[0])
            right.pop(0)

    result.extend(left)
    result.extend(right)
    return result


def intersection(list_a: List, list_b: List) -> List:
    list_a = merge_sort(list_a)

    result = []
    index = 0
    for b in list_b:
        if list_a[index] == b:
            result.append(b)
            index += 1

    return result


def intersection(list_a: List, list_b: List) -> List:
    hash_set = set()
    for a in list_a:
        hash_set.add(a)

    result = []
    for b in list_b:
        if b in hash_set:
            result.append(b)

    return result


if __name__ == '__main__':
    assert merge_sort([]) == []
    assert merge_sort([0, 1]) == [0, 1]
    assert merge_sort([5, 2, 10, 4]) == [2, 4, 5, 10]
    assert intersection([], []) == []
    assert intersection([0], [1]) == []
    assert intersection([0, 3], [1, 0, 3]) == [0, 3]

"""
def intersection(list_a: List, list_b: List) -> List:
    result = []
    for a in list_a:
        for b in list_b:
            if a == b:
                result.append(b)

    return result


def merge_sort(l: List) -> List:
    if len(l) <= 1:
        return l

    left = merge_sort(l.first_half())
    right = merge_sort(l.second_half())

    result = []

    while len(left) > 0 and len(right) > 0:
        if left[0] <= right[0]:
            result.append(left[0])
            left.pop_first()
        else:
            result.append(right[0])
            right.pop_first()

    result.extend(left)
    result.extend(right)
    return result


def intersection(list_a: List, list_b: List) -> List:
    list_a = merge_sort(list_a)

    result = []
    index = 0
    for b in list_b:
        if list_a[index] == b:
            result.append(b)
            index += 1

    return result


def intersection(list_a: List, list_b: List) -> List:
    hash_set = set()
    for a in list_a:
        hash_set.add(a)

    result = []
    for b in list_b:
        if b in hash_set:
            result.append(b)

    return result
"""