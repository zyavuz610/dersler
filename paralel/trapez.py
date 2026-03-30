# mpirun -np 6 python3 trapez.py

from mpi4py import MPI

def f(x):
    """İntegrali alınacak fonksiyon f(x) = x^2"""
    return x * x

def trap(left_end, right_end, trap_count, base_len):
    """Yamuk Kuralı hesaplama fonksiyonu"""
    estimate = (f(left_end) + f(right_end)) / 2.0
    for i in range(1, trap_count):
        x = left_end + i * base_len
        estimate += f(x)
    return estimate * base_len

def main():
    comm = MPI.COMM_WORLD
    my_rank = comm.Get_rank()
    comm_sz = comm.Get_size()

    n = 1024          # Toplam yamuk sayısı
    a, b = 0.0, 3.0   # İntegral aralığı
    
    h = (b - a) / n   # Her bir yamuğun genişliği
    local_n = n // comm_sz  # Her işlemciye düşen yamuk sayısı

    # Her işlemcinin kendi hesaplama aralığını belirle
    local_a = a + my_rank * local_n * h
    local_b = local_a + local_n * h

    # Kendi payına düşen alanı hesapla
    local_int = trap(local_a, local_b, local_n, h)

    # Tüm yerel sonuçları 0 numaralı (Master) işlemcide topla
    if my_rank != 0:
        print(f"{my_rank}/{comm_sz} biriminde ({local_a:.2f} - {local_b:.2f}) arası integral hesaplanıyor")
        # Python nesnelerini göndermek için küçük harfli 'send' kullanılır
        comm.send(local_int, dest=0, tag=0)
    else:
        total_int = local_int
        for i in range(1, comm_sz):
            # Diğer işlemcilerden gelen veriyi al
            received_int = comm.recv(source=i, tag=0)
            total_int += received_int
        
        # Sonucu sadece Master yazdırsın
        print(f"{n} adet yamuk ile integral tahmini ({a}'den {b}'e) = {total_int:.15e}")

if __name__ == "__main__":
    main()
