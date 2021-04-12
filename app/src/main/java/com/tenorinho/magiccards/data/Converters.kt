package com.tenorinho.magiccards.data

import androidx.room.TypeConverter

class Converters{
    @TypeConverter fun stringToArray(s:String?):Array<String>{
        if(s.isNullOrEmpty()){
            return arrayOf("")
        }
        val list = s.split(":")
        return Array(list.size){
            list[it]
        }
    }
    @TypeConverter fun arrayToString(array:Array<String>?):String{
        var s:String = ""
        if(array == null || array.isEmpty()){
            return s
        }
        if(array.size == 1){
            s = array[0]
        }
        else if(array.size > 1){
            for(i in array.indices){
                if(i == array.size-1){
                    s += array[i]
                }
                else{
                    s +="${array[i]}:"
                }
            }
        }
        return s
    }
}