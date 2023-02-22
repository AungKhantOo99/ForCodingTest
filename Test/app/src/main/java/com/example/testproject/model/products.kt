package com.example.testproject.model

import java.io.Serializable

class products(val name:String,
                    val image:Int,
                    val price:Int,
                    val stock:Int,
                    val brand:String,
                    val tag:Tag): Serializable