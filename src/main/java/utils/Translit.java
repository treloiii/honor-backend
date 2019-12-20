package utils;

import java.util.HashMap;
import java.util.Map;

public class Translit {

    final int UPPER = 1;

    final int LOWER = 2;

    private Map<String, String> map;

    Translit(boolean isReverse){
        map=isReverse?makeReverseTranslitMap():makeTranslitMap();
    }

    private Map<String, String> makeTranslitMap() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "а");
        map.put("b", "б");
        map.put("v", "в");
        map.put("g", "г");
        map.put("d", "д");
        map.put("e", "е");
        map.put("yo", "ё");
        map.put("zh", "ж");
        map.put("z", "з");
        map.put("i", "и");
        map.put("j", "й");
        map.put("k", "к");
        map.put("l", "л");
        map.put("m", "м");
        map.put("n", "н");
        map.put("o", "о");
        map.put("p", "п");
        map.put("r", "р");
        map.put("s", "с");
        map.put("t", "т");
        map.put("u", "у");
        map.put("f", "ф");
        map.put("h", "х");
        map.put("ts", "ц");
        map.put("ch", "ч");
        map.put("sh", "ш");
        map.put("sch","щ");
        map.put("$i","ы");
        map.put("`", "ъ");
        map.put("y", "у");
        map.put("'", "ь");
        map.put("yu", "ю");
        map.put("ya", "я");
        map.put("x", "кс");
        map.put("w", "в");
        map.put("q", "к");
        map.put("iy", "ий");
        return map;
    }

    private Map<String, String> makeReverseTranslitMap() {
        Map<String, String> map = new HashMap<>();
        map.put("а","a");
        map.put("б","b");
        map.put("в","v");
        map.put("г","g");
        map.put("д","d");
        map.put("е","e");
        map.put("ё","yo");
        map.put("ж","zh");
        map.put("з","z");
        map.put("и","i");
        map.put("й","j");
        map.put("л","l");
        map.put("м","m");
        map.put("н","n");
        map.put("о","o");
        map.put("п","p");
        map.put("р","r");
        map.put("с","s");
        map.put("т","t");
        map.put("у","u");
        map.put("ф","f");
        map.put("х","h");
        map.put("ц","ts");
        map.put("ч","ch");
        map.put("ш","sh");
        map.put("щ","sch");
        map.put("ы","$i");
        map.put("ъ","`");
        map.put("ь","'");
        map.put("ю","yu");
        map.put("я","ya");
        map.put("кс","x");
        map.put("к","q");
        map.put("ий","iy");
        return map;
    }
    private int charClass(char c) {
        return Character.isUpperCase(c) ? UPPER : LOWER;
    }

    private String get(String s) {
        int charClass = charClass(s.charAt(0));
        String result = map.get(s.toLowerCase());
        return result == null ? "" : (charClass == UPPER ? (result.charAt(0) + "").toUpperCase() +
                (result.length() > 1 ? result.substring(1) : "") : result);
    }

    public String translit(String text) {
        int len = text.length();
        if (len == 0) {
            return text;
        }
        if (len == 1) {
            return get(text);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ) {
            // get next 2 symbols
            String toTranslate = text.substring(i, i <= len - 2 ? i + 2 : i + 1);
            // trying to translate
            String translated = get(toTranslate);
            // if these 2 symbols are not connected try to translate one by one
            if (translated.isEmpty()) {
                translated = get(toTranslate.charAt(0) + "");
                sb.append(translated.isEmpty() ? toTranslate.charAt(0) : translated);
                i++;
            } else {
                sb.append(translated.isEmpty() ? toTranslate : translated);
                i += 2;
            }
        }
        return sb.toString();
    }
}
