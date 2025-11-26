Mengapa data.sql bisa langsung di generate dan disimpan di database saat running proyek ini:

Saya akan memeriksa file `data.sql` dan konfigurasi aplikasi Anda untuk menjelaskan mengapa data langsung masuk ke database. 

Read [](file:///d%3A/Fakhri/Work/Portofolio/Projek/Spring%20Boot/patient-service/src/main/resources/data.sql#1-1), lines 1 to 50

Read [](file:///d%3A/Fakhri/Work/Portofolio/Projek/Spring%20Boot/patient-service/src/main/resources/application.properties)

Read [](file:///d%3A/Fakhri/Work/Portofolio/Projek/Spring%20Boot/patient-service/pom.xml)

**Ya, benar!** Data langsung masuk ke database karena file data.sql Anda **otomatis dijalankan** oleh Spring Boot. Ini terjadi karena konfigurasi di application.properties:

```properties
spring.sql.init.mode=always
```

**Penjelasan:**

1. **`spring.sql.init.mode=always`** - Properti ini memberitahu Spring Boot untuk **selalu menjalankan** script SQL (data.sql dan `schema.sql`) setiap kali aplikasi dimulai.

2. **File data.sql** Anda berisi:
   - Pembuatan tabel `patient`
   - INSERT statements untuk data pasien (John Doe, Jane Smith, Alice Johnson, Bob Brown, dll.)

3. **Proses eksekusi:**
   - Spring Boot mendeteksi file data.sql di folder resources
   - Karena `spring.sql.init.mode=always`, script dijalankan otomatis
   - Data di-insert ke database H2 in-memory setiap kali aplikasi start

**Jika Anda TIDAK ingin data otomatis di-insert**, ada beberapa opsi:

1. **Disable auto-initialization:**
   ```properties
   spring.sql.init.mode=never
   ```

2. **Atau ubah ke `embedded`** (hanya jalan untuk embedded database seperti H2):
   ```properties
   spring.sql.init.mode=embedded
   ```

3. **Hapus atau rename file data.sql**

Apakah Anda ingin saya mengubah konfigurasi ini?