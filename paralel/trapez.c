/*
/*
mpicc trapez.c -o trapez
mpirun -np 6 ./trapez

*/
*/

#include <stdio.h>
#include <mpi.h>

// İntegrali alınacak fonksiyon (Örn: f(x) = x*x)
double f(double x) {
    return x * x;
}

// Yamuk Kuralı hesaplama fonksiyonu
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
    int my_rank, comm_sz, n = 1024, local_n;   
    double a = 0.0, b = 3.0, h, local_a, local_b;
    double local_int, total_int;

    MPI_Init(NULL, NULL);
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &comm_sz);

    h = (b - a) / n;          // Her bir yamuğun genişliği
    local_n = n / comm_sz;    // Her işlemciye düşen yamuk sayısı

    // Her işlemcinin kendi hesaplama aralığını belirle
    local_a = a + my_rank * local_n * h;
    local_b = local_a + local_n * h;
    
    // Kendi payına düşen alanı hesapla
    local_int = Trap(local_a, local_b, local_n, h);

    // Tüm yerel sonuçları 0 numaralı (Master) işlemcide topla
    if (my_rank != 0) {
        printf("%d/%d biriminde (%f - %f) arası integral hesaplanıyor\n",my_rank,comm_sz,local_a,local_b);
        MPI_Send(&local_int, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
    } else {
        total_int = local_int;
        for (int i = 1; i < comm_sz; i++) {
            MPI_Recv(&local_int, 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            total_int += local_int;
        }
    }

    // Sonucu sadece Master işlemci yazdırsın
    if (my_rank == 0) {
        printf("%d adet yamuk ile integral tahmini (%.1f'den %.1f'e) = %.15e\n", n, a, b, total_int);
    }

    MPI_Finalize();
    return 0;
}
