package com.demo.updown.Test;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.util.ResourceUtils;

import java.io.*;

public class TestPDFWord {



    public static void main(String[] args) throws FileNotFoundException {


       /* String pdfFile = ResourceUtils.getFile("classpath:testPdf.pdf").getPath();
        System.out.println(pdfFile);*///获取的文件全路径
        File file =  ResourceUtils.getFile("classpath:testPdf.pdf");
        String fieName = file.getName();
        System.out.println(file.getName());//获取文件名字
        //加载PDF
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(fieName);
//保存为Word格式
        pdf.saveToFile("ToWord.docx", FileFormat.DOCX);

        //可以成功，但是格式不知想要的
        /*try {
            String pdfFile = ResourceUtils.getFile("classpath:mace.pdf").getPath();
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile + ".doc";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



}
