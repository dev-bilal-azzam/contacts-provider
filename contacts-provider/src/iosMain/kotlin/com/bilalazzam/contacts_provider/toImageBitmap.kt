package com.bilalazzam.contacts_provider

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import org.jetbrains.skia.*
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataGetLength
import platform.CoreFoundation.CFRelease
import platform.CoreGraphics.*
import platform.UIKit.UIImage

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.toSkiaImage(): Image? {
    val imageRef = this.CGImage ?: return null

    val width = CGImageGetWidth(imageRef).toInt()
    val height = CGImageGetHeight(imageRef).toInt()

    val bytesPerRow = CGImageGetBytesPerRow(imageRef)
    val data = CGDataProviderCopyData(CGImageGetDataProvider(imageRef))
    val bytePointer = CFDataGetBytePtr(data)
    val length = CFDataGetLength(data)

    val alphaType = when (CGImageGetAlphaInfo(imageRef)) {
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast -> ColorAlphaType.PREMUL

        CGImageAlphaInfo.kCGImageAlphaFirst,
        CGImageAlphaInfo.kCGImageAlphaLast -> ColorAlphaType.UNPREMUL

        CGImageAlphaInfo.kCGImageAlphaNone,
        CGImageAlphaInfo.kCGImageAlphaNoneSkipFirst,
        CGImageAlphaInfo.kCGImageAlphaNoneSkipLast -> ColorAlphaType.OPAQUE

        else -> ColorAlphaType.UNKNOWN
    }

    val byteArray = ByteArray(length.toInt()) { index ->
        bytePointer!![index].toByte()
    }

    CFRelease(data)
    CGImageRelease(imageRef)

    val skiaColorSpace = ColorSpace.sRGB
    val colorType = ColorType.RGBA_8888

    // Convert RGBA to BGRA
    for (i in byteArray.indices step 4) {
        val r = byteArray[i]
        val g = byteArray[i + 1]
        val b = byteArray[i + 2]
        val a = byteArray[i + 3]

        byteArray[i] = b
        byteArray[i + 2] = r
    }

    return Image.makeRaster(
        imageInfo = ImageInfo(
            width = width,
            height = height,
            colorType = colorType,
            alphaType = alphaType,
            colorSpace = skiaColorSpace
        ),
        bytes = byteArray,
        rowBytes = bytesPerRow.toInt(),
    )
}

fun UIImage.toImageBitmap(): ImageBitmap {
    val skiaImage = this.toSkiaImage() ?: return ImageBitmap(1, 1)
    return skiaImage.toComposeImageBitmap()
}