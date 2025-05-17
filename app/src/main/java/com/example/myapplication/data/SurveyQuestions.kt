package com.example.myapplication.data

data class SurveyQuestion(
    val id: Int,
    val question: String,
    val type: String, // "single" veya "multi"
    val options: List<String>
)

val surveyQuestions = listOf(
    SurveyQuestion(1, "Evde hangi enerji tasarrufu yöntemlerini kullanıyorsunuz?", "multi", listOf("LED ampul", "Tasarruflu beyaz eşyalar", "Gereksiz ışıkları kapatma", "Hiçbiri")),
    SurveyQuestion(2, "Günlük cihaz kullanım süresi (TV, bilgisayar, telefon)?", "single", listOf("0–2 saat", "2–5 saat", "5–8 saat", "8+ saat")),
    SurveyQuestion(3, "Günlük ulaşım şekliniz nedir?", "single", listOf("Yürüyüş", "Toplu taşıma", "Özel araç", "Bisiklet / scooter")),
    SurveyQuestion(4, "Araç kullanırken çevre dostu yakıt tercih ediyor musunuz?", "single", listOf("Evet (Elektrikli / Hibrit)", "Hayır", "Araç kullanmıyorum")),
    SurveyQuestion(5, "Evde geri dönüşüm yapıyor musunuz?", "single", listOf("Evet, düzenli", "Bazen", "Hayır")),
    SurveyQuestion(6, "Hangi ürünleri düzenli olarak geri dönüştürüyorsunuz?", "multi", listOf("Kağıt / Karton", "Plastik", "Cam", "Metal", "Hiçbiri")),
    SurveyQuestion(7, "Günlük duş süreniz ortalama nedir?", "single", listOf("5 dakikadan az", "5–10 dakika", "10–15 dakika", "15 dakikadan fazla")),
    SurveyQuestion(8, "Muslukları gereksiz yere açık bırakır mısınız?", "single", listOf("Hayır, dikkat ederim", "Bazen", "Evet, farkında değilim")),
    SurveyQuestion(9, "Sürdürülebilirlik uygulaması oyunlaştırılırsa ilgini çeker mi?", "single", listOf("Evet", "Belki", "Hayır")),
    SurveyQuestion(10, "Hangi alanlarda öneri almak istersiniz?", "multi", listOf("Enerji tasarrufu", "Su kullanımı", "Geri dönüşüm", "Karbon ayak izi azaltımı"))
)
