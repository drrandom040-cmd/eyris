package com.elsewhere.eyris.utils

import android.content.Context
import android.os.Environment
import com.elsewhere.eyris.domain.model.Business
import com.opencsv.CSVWriter
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ExportUtils {

    fun exportToCSV(
        context: Context,
        businesses: List<Business>,
        filename: String = "eyris_export_${System.currentTimeMillis()}.csv"
    ): Result<File> {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, filename)

            CSVWriter(FileWriter(file)).use { writer ->
                // Write header
                writer.writeNext(arrayOf(
                    "Name",
                    "Category",
                    "Address",
                    "Phone",
                    "Email",
                    "Website",
                    "Rating",
                    "Reviews",
                    "Instagram",
                    "Facebook",
                    "TikTok",
                    "WhatsApp",
                    "Source",
                    "Saved Date"
                ))

                // Write data
                businesses.forEach { business ->
                    writer.writeNext(arrayOf(
                        business.name,
                        business.category,
                        business.address,
                        business.phone ?: "",
                        business.email ?: "",
                        business.website ?: "",
                        business.rating?.toString() ?: "",
                        business.reviewCount.toString(),
                        business.instagram ?: "",
                        business.facebook ?: "",
                        business.tiktok ?: "",
                        business.whatsapp ?: "",
                        business.source,
                        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date(business.savedAt))
                    ))
                }
            }

            Timber.d("CSV exported successfully: ${file.absolutePath}")
            Result.success(file)
        } catch (e: Exception) {
            Timber.e(e, "Error exporting to CSV")
            Result.failure(e)
        }
    }

    fun exportToPDF(
        context: Context,
        businesses: List<Business>,
        filename: String = "eyris_export_${System.currentTimeMillis()}.pdf"
    ): Result<File> {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, filename)

            val document = Document()
            PdfWriter.getInstance(document, java.io.FileOutputStream(file))
            document.open()

            // Add title
            val titleFont = Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)
            document.add(Paragraph("Eyris - Business Export", titleFont))
            document.add(Paragraph("Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())}\n"))

            // Add businesses
            val contentFont = Font(Font.FontFamily.HELVETICA, 10f)
            businesses.forEach { business ->
                document.add(Paragraph("\n${business.name}", Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)))
                document.add(Paragraph("Category: ${business.category}", contentFont))
                document.add(Paragraph("Address: ${business.address}", contentFont))
                
                if (!business.phone.isNullOrEmpty()) {
                    document.add(Paragraph("Phone: ${business.phone}", contentFont))
                }
                
                if (!business.email.isNullOrEmpty()) {
                    document.add(Paragraph("Email: ${business.email}", contentFont))
                }
                
                if (!business.website.isNullOrEmpty()) {
                    document.add(Paragraph("Website: ${business.website}", contentFont))
                }
                
                if (business.rating != null) {
                    document.add(Paragraph("Rating: ${business.rating} (${business.reviewCount} reviews)", contentFont))
                }
                
                document.add(Paragraph("Source: ${business.source}", contentFont))
            }

            document.close()

            Timber.d("PDF exported successfully: ${file.absolutePath}")
            Result.success(file)
        } catch (e: Exception) {
            Timber.e(e, "Error exporting to PDF")
            Result.failure(e)
        }
    }

    fun exportBoth(
        context: Context,
        businesses: List<Business>
    ): Result<Pair<File, File>> {
        return try {
            val csvResult = exportToCSV(context, businesses)
            val pdfResult = exportToPDF(context, businesses)

            if (csvResult.isSuccess && pdfResult.isSuccess) {
                Result.success(Pair(csvResult.getOrNull()!!, pdfResult.getOrNull()!!))
            } else {
                Result.failure(Exception("Failed to export one or both formats"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error exporting both formats")
            Result.failure(e)
        }
    }
}
