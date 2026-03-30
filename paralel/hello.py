# mpirun -np 6 python3 hello.py
from mpi4py import MPI

def main():
    # MPI ortamı mpi4py içe aktarıldığında otomatik başlar (MPI_Init)
    comm = MPI.COMM_WORLD

    # Toplam işlemci sayısını alır (MPI_Comm_size)
    world_size = comm.Get_size()

    # Mevcut işlemcinin numarasını alır (MPI_Comm_rank)
    world_rank = comm.Get_rank()

    # İşlemci adını alır (MPI_Get_processor_name)
    processor_name = MPI.Get_processor_name()

    # Sonucu ekrana yazdır
    print(f"Merhaba! İşlemci: {processor_name}, Rank: {world_rank} / {world_size}")

    # MPI ortamı program bittiğinde otomatik kapanır (MPI_Finalize)

if __name__ == "__main__":
    main()
