package com.example.pdfview

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         val cli = findViewById<Button>(R.id.OPenpdf)
         val cli2 = findViewById<Button>(R.id.OPenbudgetreport)


        cli.setOnClickListener {
            openPdf("SahilReport.pdf")
        }
        cli2.setOnClickListener {
            openPdf("BudgetBuddyReport2.pdf")
        }



    }


    private fun openPdf(stringpdf:String) {
        // Copy the PDF from assets to internal storage
        val pdfFile = copyPdfToInternalStorage(stringpdf)

        // Get the content URI using FileProvider
        val uri: Uri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            pdfFile
        )

        // Create an Intent to view the PDF
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Attempt to open the PDF
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No PDF viewer installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyPdfToInternalStorage(pdfFileName: String): File {
        // Open the PDF file from the assets folder
        val inputStream = assets.open(pdfFileName)
        val file = File(filesDir, pdfFileName) // Create the file in internal storage

        // Copy the file contents to internal storage
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return file // Return the copied file
    }
}


