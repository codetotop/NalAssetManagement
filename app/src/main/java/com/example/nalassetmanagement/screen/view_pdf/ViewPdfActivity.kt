package com.example.nalassetmanagement.screen.view_pdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.databinding.ActivityViewPdfBinding
import com.example.nalassetmanagement.screen.inventory.InventoryActivity
import com.example.nalassetmanagement.screen.inventory.InventoryPresenter
import com.example.nalassetmanagement.screen.inventory.inventory_detail.InventorySessionDetailActivity
import com.example.nalassetmanagement.view.custom.ActionBarView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ViewPdfActivity : AppCompatActivity(), ViewPdf.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: ViewPdf.Presenter
    private lateinit var binding: ActivityViewPdfBinding

    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var parcelFileDescriptor: ParcelFileDescriptor
    private var currentPage: PdfRenderer.Page? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ViewPdfPresenter(this)
        val pdfFile = intent.getStringExtra(InventorySessionDetailActivity.TYPE) ?: ""

        try {
            openRenderer(pdfFile)
            showPage(0)
        } catch (e: IOException) {
            Toast.makeText(this, "Không thể mở tệp PDF", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

        if (pdfFile == InventorySessionDetailActivity.BIEN_BAN_KIEM_KE_TAI_SAN) {
            binding.toolbar.setTitle("Biên bản kiểm kê tài sản")
        }
        else {
            binding.toolbar.setTitle("Khấu hao tài sản")
        }

        binding.toolbar.setActionBarViewListener(this)
    }

    @Throws(IOException::class)
    private fun openRenderer(fileName: String) {
        val file = File(cacheDir, fileName)
        if (!file.exists()) {
            assets.open(fileName).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                }
            }
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(parcelFileDescriptor)
    }

    private fun showPage(index: Int) {
        try {
            if (pdfRenderer.pageCount <= index) { return }
            currentPage?.close()
            currentPage = pdfRenderer.openPage(index).apply {
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                binding.imageView.setImageBitmap(bitmap)
            }
        }
        catch (_ : Exception) {}
    }

    companion object {
        const val BASE_PATH = "file:///android_asset/"
    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }

}
