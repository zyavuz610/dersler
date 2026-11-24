import threading
import time

# İstediğiniz zaman bu sayıyı değiştirebilirsiniz (Örn: 4, 6, 8)
THREAD_SAYISI = 3

# Thread'lerin ekrana aynı anda yazıp çıktıları birbirine karıştırmasını önleyen kilit
ekran_kilidi = threading.Lock()

def asal_mi(sayi):
    """Bir sayının asal olup olmadığını kontrol eder."""
    if sayi < 2:
        return False
    for i in range(2, int(sayi ** 0.5) + 1):
        if sayi % i == 0:
            return False
    return True

def asal_bul_gorevi(thread_no, baslangic, bitis):
    """Her thread'in kendi aralığında çalıştıracağı görev fonksiyonu."""
    with ekran_kilidi:
        print(f"[Thread-{thread_no}] Başladı -> {baslangic} ile {bitis} arasındaki asalları arıyor...")

    for sayi in range(baslangic, bitis):
        if asal_mi(sayi):
            with ekran_kilidi:
                print(f"[Thread-{thread_no}] Asal sayı bulundu: {sayi}")
            time.sleep(3)  # Her asal sayı bulduğunda 1 saniye bekle

    with ekran_kilidi:
        print(f"[Thread-{thread_no}] Görevini tamamladı! ({baslangic}-{bitis})")

# --- PROGRAMIN BAŞLANGICI ---
if __name__ == "__main__":
    thread_listesi = []
    baslangic_degeri = 100
    aralik_boyutu = 100

    print(f"Toplam {THREAD_SAYISI} adet thread başlatılıyor...\n" + "-"*50)

    # Belirtilen thread sayısı kadar döngü kur ve aralıkları dinamik hesapla
    for i in range(1, THREAD_SAYISI + 1):
        alt_sinir = baslangic_degeri + (i - 1) * aralik_boyutu
        ust_sinir = alt_sinir + aralik_boyutu

        # Thread nesnesini oluştur
        t = threading.Thread(
            target=asal_bul_gorevi, 
            args=(i, alt_sinir, ust_sinir)
        )
        thread_listesi.append(t)
        t.start()

    # Ana programın, tüm thread'ler işini bitirene kadar beklemesini sağla
    for t in thread_listesi:
        t.join()

    print("-" * 50 + "\nTüm thread'ler işlemlerini tamamladı.")