package fi.example.mieli.chrome

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
<<<<<<< HEAD
<<<<<<<< HEAD:app/src/main/java/fi/example/mieli/chrome/MainActivity.kt
========
// Import the generated BuildConfig from the namespace package.
>>>>>>>> 5aff65cb55aab233763b02f6d318c8ba95958540:app/src/main/java/com/example/chrome_opener/MainActivity.kt
import fi.example.mieli.chrome.BuildConfig
=======
>>>>>>> 5aff65cb55aab233763b02f6d318c8ba95958540

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = BuildConfig.BASE_URL

        val chrome = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            setPackage("com.android.chrome")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        try {
            startActivity(chrome)
        } catch (_: ActivityNotFoundException) {
            // Fallback: open in any browser
            val fallback = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(fallback)
        }
        finish()
    }
}
