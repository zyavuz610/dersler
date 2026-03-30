/*
mpicc hello.c -o hello
mpirun -np 6 ./hello

*/

#include <mpi.h>
#include <stdio.h>

int main(int argc, char** argv) {
    // MPI ortamını başlatır
    MPI_Init(&argc, &argv);

    int world_size;
    // Toplam işlemci sayısını alır
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    int world_rank;
    // Mevcut işlemcinin numarasını (0, 1, 2...) alır
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    // İşlemci adını al (isteğe bağlı)
    char processor_name[MPI_MAX_PROCESSOR_NAME];
    int name_len;
    MPI_Get_processor_name(processor_name, &name_len);

    // Sonucu ekrana yazdır
    printf("Merhaba! İşlemci: %s, Rank: %d / %d\n",
           processor_name, world_rank, world_size);

    // MPI ortamını kapatır
    MPI_Finalize();
    return 0;
}
