/*

mpicc trapez.c -o trapez
mpirun -np 6 ./trapez

*/

#include <stdio.h>
#include <mpi.h>

double f(double x) {
    return x * x;
}

double Trap(double left_end, double right_end, int trap_count, double base_len) {
    double estimate, x;
    int i;

    estimate = (f(left_end) + f(right_end)) / 2.0;
    for (i = 1; i <= trap_count - 1; i++) {
        x = left_end + i * base_len;
        estimate += f(x);
    }
    estimate = estimate * base_len;
    return estimate;
}

int main(int argc, char** argv) {
    int my_rank, comm_sz, n, local_n;
    double a, b, h, local_a, local_b;
    double local_int, total_int;

    MPI_Init(NULL, NULL);
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &comm_sz);

    if (my_rank == 0) {
        printf("Integralin başlangıç noktası (a) giriniz: ");
        fflush(stdout);
        scanf("%lf", &a);

        printf("Integralin bitiş noktası (b) giriniz: ");
        fflush(stdout);
        scanf("%lf", &b);

        printf("Yamuk sayısı (n) giriniz: ");
        fflush(stdout);
        scanf("%d", &n);
    }

    MPI_Bcast(&a, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Bcast(&b, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Bcast(&n, 1, MPI_INT,    0, MPI_COMM_WORLD);

    h       = (b - a) / n;
    local_n = n / comm_sz;

    local_a = a + my_rank * local_n * h;
    local_b = local_a + local_n * h;

    local_int = Trap(local_a, local_b, local_n, h);

    printf("Rank %d/%d → hesaplanan aralık: [%f, %f]\n", my_rank, comm_sz, local_a, local_b);

    MPI_Reduce(&local_int, &total_int, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);

    if (my_rank == 0) {
        printf("\n%d adet yamuk ile integral tahmini (%.1f'den %.1f'e) = %.15e\n", n, a, b, total_int);
    }

    MPI_Finalize();
    return 0;
}