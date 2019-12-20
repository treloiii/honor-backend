package utils

import java.util.HashMap

class Translit {
    final val UPPER:Int=1
    final val LOWER:Int=2
    private val map:Map<String,String>

    constructor(isReverse:Boolean) {
        map = if(isReverse)
            makeReverseTranslitMap()
        else
            makeTranslitMap();
    }

    private fun makeReverseTranslitMap(): Map<String, String> {
        val map:Map<String,String> = mapOf(
                "a" to "а",
                "b" to "б",
                "v" to "в",
                "g" to "г",
                "d" to "д",
                "e" to "е",
                "yo" to "ё",
                "zh" to "ж",
                "z" to "з",
                "i" to "и",
                "j" to "й",
                "k" to "к",
                "l" to "л",
                "m" to "м",
                "n" to "н",
                "o" to "о",
                "p" to "п",
                "r" to "р",
                "s" to "с",
                "t" to "т",
                "u" to "у",
                "f" to "ф",
                "h" to "х",
                "ts" to "ц",
                "ch" to "ч",
                "sh" to "ш",
                "sch" to "щ",
                "\$i" to "ы",
                "`" to "ъ",
                "y" to "у",
                "'" to "ь",
                "yu" to "ю",
                "ya" to "я",
                "x" to "кс",
                "w" to "в",
                "q" to "к"
        )
        return map
    }

    private fun makeTranslitMap():Map<String,String>{
        val map = HashMap<String, String>()
        map["а"] = "a"
        map["б"] = "b"
        map["в"] = "v"
        map["г"] = "g"
        map["д"] = "d"
        map["е"] = "e"
        map["ё"] = "yo"
        map["ж"] = "zh"
        map["з"] = "z"
        map["и"] = "i"
        map["й"] = "j"
        map["л"] = "l"
        map["м"] = "m"
        map["н"] = "n"
        map["о"] = "o"
        map["п"] = "p"
        map["р"] = "r"
        map["с"] = "s"
        map["т"] = "t"
        map["у"] = "u"
        map["ф"] = "f"
        map["х"] = "h"
        map["ц"] = "ts"
        map["ч"] = "ch"
        map["ш"] = "sh"
        map["щ"] = "sch"
        map["ы"] = "\$i"
        map["ъ"] = "`"
        map["ь"] = "'"
        map["ю"] = "yu"
        map["я"] = "ya"
        map["кс"] = "x"
        map["к"] = "q"
        map["ий"] = "iy"
        return map
    }
    private fun charClass(c:Char):Int{
        return if(c.isUpperCase())
            UPPER
        else
            LOWER;
    }

    private fun get(s:String):String{
        val charClass:Int=charClass(s[0])
        val result= map[s.toLowerCase()]
        if(result==null)
            return ""
        else
    }


}