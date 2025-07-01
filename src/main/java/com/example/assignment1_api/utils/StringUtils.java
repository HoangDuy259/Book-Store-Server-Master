package com.example.assignment1_api.utils;

import lombok.RequiredArgsConstructor;

import java.text.Normalizer;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class StringUtils {

    public static String getExactName(String name) {
        // Bước 1: Chuẩn hóa chuỗi Unicode về dạng tổ hợp (Normalization Form D)
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);

        // Bước 2: Loại bỏ các ký tự dấu (diacritics)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");

        // Bước 3: Thay thế các ký tự đặc biệt tiếng Việt không theo chuẩn Unicode (đặc biệt là 'Đ' và 'đ')
        result = result.replaceAll("Đ", "D");
        result = result.replaceAll("đ", "d");

        result = String.join("",result.toLowerCase().trim().split(" "));

        return result;
    }
}
