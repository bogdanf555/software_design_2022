package com.foodpanda.utils;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.common.RestaurantDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;

@Component
public class PdfGenerator {

    public void generatePDF(RestaurantDTO restaurant, List<FoodDTO> foods, String path)
            throws  FileNotFoundException,
                    DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path + restaurant.getName() + ".pdf"));

        document.open();

        //title
        Font font = FontFactory.getFont(FontFactory.COURIER, 48, BaseColor.PINK);
        Chunk chunk = new Chunk("FoodPanda", font);
        document.add(chunk);
        document.add(new Paragraph("\n"));

        // name of Restaurant
        font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
        chunk = new Chunk(restaurant.getName().toUpperCase(Locale.ROOT), font);
        document.add(chunk);
        document.add(new Paragraph("\n"));

        for (FoodDTO food: foods) {
            font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
            chunk = new Chunk(food.toString(), font);
            document.add(chunk);
            document.add(new Paragraph("\n"));
        }

        document.close();
    }
}
