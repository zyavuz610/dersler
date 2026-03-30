#include <mpi.h>
#include <stdio.h>

int main(int argc, char** argv) {
    // MPI ortamını başlat
    MPI_Init(&argc, &argv);

    int dunya_boyutu;
    MPI_Comm_size(MPI_COMM_WORLD, &dunya_boyutu);

    int islemci_sirasi;
    MPI_Comm_rank(MPI_COMM_WORLD, &islemci_sirasi);

    // Her bir işlemci (process) kendi sırasını ve toplam işlemci sayısını yazdırır
    printf("Merhaba! Ben %d. islemciyim. Toplam islemci sayisi: %d\n", islemci_sirasi, dunya_boyutu);

    // MPI ortamını sonlandır
    MPI_Finalize();

    return 0;
}