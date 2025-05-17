from ortools.constraint_solver import pywrapcp
import numpy
from ortools.linear_solver import pywraplp


def main(orders, payments, payments_limits, available, points, points_limit):
    solver = pywraplp.Solver('LP_and_MIP', pywraplp.Solver.CBC_MIXED_INTEGER_PROGRAMMING)
    x, y, z = {}, {}, {}
    ord_num, pay_num = len(orders), len(payments)
    for i in range(ord_num):
        for j in range(pay_num):
            x[i, j] = solver.IntVar(0, 1, 'x[%i, %i]' % (i, j))  #PAYMENTY zwykłe full
    for i in range(ord_num):
        y[i] = solver.IntVar(0, 1, 'y[%i]' % i)  # punkty 10 %
    for i in range(ord_num):
        z[i] = solver.IntVar(0, 1, 'z[%i]' % i)  # punkty FULL

    for o in range(ord_num):
        solver.Add(
            0 <=
            (
                    solver.Sum((x[o, i] for i in range(pay_num))) + y[o] + z[o]
            )
            <= 1
        )

    for p in range(pay_num):
        solver.Add(
            (
                solver.Sum(x[i, p] * orders[i] * (1 - payments[p]) for i in range(ord_num))
            ) <= payments_limits[p]
        )
    solver.Add(
        (
                solver.Sum(y[o] * 0.1 * orders[o] for o in range(ord_num)) + solver.Sum(z[o] * (1 - points) * orders[o] for o in range(ord_num))
        ) <= points_limit
    )

    k = 10 ** 0
    total_profit = solver.NumVar(0.0, 100000.0, "total_profit")
    solver.Add(
        total_profit ==
        (
                (solver.Sum(x[o, p] * orders[o] * payments[p] for o in range(ord_num) for p in available[o])
                 + solver.Sum(y[o] * 0.1 * orders[o] for o in range(ord_num))
                 + solver.Sum(z[o] * points * orders[o] for o in range(ord_num))) * k
            # - solver.Sum(x[o, p] for o in range(n) for p in range(m))
        )
    )

    solver.Maximize(total_profit)

    status = solver.Solve()
    if status == solver.OPTIMAL:
        print("Znaleziono optymalne rozwiązanie.")
        print("Maximum profit =", solver.Objective().Value())
    elif status == solver.UNBOUNDED:
        print("Model jest nieograniczony – cel można wypchnąć do ∞.")
    elif status == solver.INFEASIBLE:
        print("Model jest sprzeczny – brak rozwiązań spełniających ograniczenia.")
    else:
        print("Inny status:", status)

    obj_val = solver.Objective().Value()
    print(f"Funkcja celu = {obj_val}")

    for (i, j), var in x.items():
        print(f"x[{i},{j}] = {var.solution_value()}", end=' ')
    print()

    for i, var in y.items():
        print(f"y[{i}] = {var.solution_value()}", end=' ')
    print()

    for i, var in z.items():
        print(f"z[{i}] = {var.solution_value()}", end=' ')

    print()


orders = [100.0, 200.0, 150.0, 50.0]
available = [[0], [1], [0, 1], []]
payments_limits = [180.0, 200.0]
payments = [0.1, 0.05]
main(orders=orders, payments=payments, payments_limits=payments_limits, available=available, points=0.15,
     points_limit=100.0)
