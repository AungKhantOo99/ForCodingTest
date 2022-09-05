package com.ako.taypad.responceImage

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class RequestBody (val file:File,val content:String)
//    : RequestBody() {
//    override fun contentType() = MediaType.parse("$content/*")
//
//    override fun writeTo(sink: BufferedSink) {
//        val buffer= ByteArray(1048)
//        val fileinputSteam=FileInputStream(file)
//        fileinputSteam.use {
//            var read:Int
//            while (fileinputSteam.read(buffer).also { read=it }!= -1){
//                sink.write(buffer,0,read)
//            }
//        }
//    }
//}