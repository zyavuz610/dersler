import threading
import time
import os

# Tüm thread'lerin ortak eriştiği global veriler
belge_icerigi = "Başlangıç metni."
program_calisiyor = True

# İki thread'in aynı anda ekrana basıp imleci kaydırmasını önleyen kilit (Mutex)
ekran_kilidi = threading.Lock()

# 1. THREAD GÖREVİ: Sabit Bölgeye Otomatik Kayıt Bilgisi Yazma
def otomatik_kaydet():
    kayit_sayaci = 0
    while program_calisiyor:
        time.sleep(5)  # Her 5 saniyede bir yedekleme yap
        if program_calisiyor:
            kayit_sayaci += 1
            with ekran_kilidi:
                # \033[s   : Mevcut imleç konumunu (kullanıcının yazı yazdığı yeri) kaydet
                # \033[1;1H: İmleci 1. satır, 1. sütuna taşı
                # \033[K   : O satırdaki eski yazıyı temizle
                # \033[u   : İmleci kullanıcının kaldığı yere geri getir
                print(
                    f"\033[s\033[1;1H\033[K"
                    f"[SABİT DURUM ÇUBUĞU - Kayıt #{kayit_sayaci}]: '{belge_icerigi}' diske kaydedildi."
                    f"\033[u",
                    end="",
                    flush=True
                )

# 2. THREAD GÖREVİ: Kullanıcı Etkileşimi
def kullanici_arayuzu():
    global belge_icerigi, program_calisiyor

    # Başlangıçta ekranı temizle ve sabit başlığı oluştur
    os.system('cls' if os.name == 'nt' else 'clear')
    print("[SABİT DURUM ÇUBUĞU]: Henüz otomatik kayıt yapılmadı.")
    print("-" * 70)

    while program_calisiyor:
        yeni_metin = input("Metin ekleyin (Çıkmak için 'q'): ")

        with ekran_kilidi:
            if yeni_metin.lower() == 'q':
                program_calisiyor = False
                print("Program kapatılıyor...")
            else:
                belge_icerigi += " " + yeni_metin
                print(f"-> Güncel Belge: {belge_icerigi}")

# --- PROGRAMIN BAŞLANGICI ---
if __name__ == "__main__":
    # Windows CMD/PowerShell'de ANSI renk ve imleç kodlarını aktif etmek için
    if os.name == 'nt':
        os.system('')

    kayit_thread = threading.Thread(target=otomatik_kaydet)
    kayit_thread.start()

    kullanici_arayuzu()

    kayit_thread.join()
    print("Tüm iş parçacıkları sonlandı. Çıkış yapıldı.")