package com.batch.spring.apache.writer;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.batch.spring.apache.model.Model;

public class CustomItemWriter implements ItemWriter<Model> {

	private static final Integer skipLine = 1;
	private static final String directory = "example.xlsx";
	
	@Override
	public void write(Chunk<? extends Model> chunk) throws Exception {
		try (FileInputStream inputStream = new FileInputStream(directory)) {
			final Workbook workbook = new XSSFWorkbook(inputStream);
			final Sheet sheet = workbook.getSheetAt(0);
			final Row row = sheet.getRow(skipLine);
			
			row.cellIterator().forEachRemaining(cell -> cell.setCellValue(-1));

			try (FileOutputStream outputStream = new FileOutputStream(directory)) {
				workbook.write(outputStream);
			}
			workbook.close();
		}
	}

}
