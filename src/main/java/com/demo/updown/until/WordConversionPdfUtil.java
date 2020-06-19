package com.demo.updown.until;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToFoConverter;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WordConversionPdfUtil {
	Logger logger = LoggerFactory.getLogger(WordConversionPdfUtil.class);
	public static void main(String[] args) throws Exception {
		WordConversionPdfUtil foToPdf = new WordConversionPdfUtil();
		String word = "E:/zhihui/fillIndictmentUnit.doc";
		word = "E:/zhihui/fillIndictmentNatural.doc";
		String fo = foToPdf.wordToFo(word);
//		String pdf = foToPdf.foToPdf("E:\\eclipse-workspace\\pdfDemo\\target\\classes\\foFile.fo");
		String pdf = foToPdf.foToPdf(fo);
		System.out.println(pdf);
	}
	
	/**
	 * 传入一个Word文件路径返回一个Fo文件路径
	 * @param word 文件路径
	 * @return
	 */
	public String wordToFo(String word) {
		InputStream inputStream;
		String targetFileName = null;
		try {
			inputStream = new  FileInputStream(word);
			targetFileName = this.getClass().getClassLoader().getResource("foFile.fo").getPath();
			logger.info("fo文件路径：" + targetFileName);
			HWPFDocument  xwpfDocument = new HWPFDocument (inputStream);
			Range range = xwpfDocument.getHeaderStoryRange();
			range.insertAfter("ASD");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			WordToFoConverter wordToFoConverter = new WordToFoConverter(document);
			wordToFoConverter.processDocument(xwpfDocument); 
			
			Document htmlDocument = wordToFoConverter.getDocument(); 
			DOMSource domSource = new DOMSource(htmlDocument); 
			StreamResult streamResult = new StreamResult(new File(targetFileName));
			
			TransformerFactory tf = TransformerFactory.newInstance(); 
		    Transformer serializer = tf.newTransformer(); 
		    serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8"); 
		    serializer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		    serializer.setOutputProperty(OutputKeys.METHOD, "html"); 
		    
		    serializer.transform(domSource, streamResult); 
		    
		    logger.info("word转换Fo成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	    return targetFileName;
	}
	
	/**
	 * Fo转换PDF
	 * @param fo 传入的fo文件路径
	 * @param pdf 输出的Pdf文件路径
	 */
	public String foToPdf(String fo) {
		FopFactory fopFactory;
		OutputStream out = null;
		String pdf = this.getClass().getClassLoader().getResource("pdfFile.pdf").getPath();
		try {
			// 通过指定对配置文件的引用来构造FopFactory
			// (如果您计划呈现多个文档，请重用)
			fopFactory = FopFactory.newInstance(new File(this.getClass().getClassLoader().getResource("fop.xconf").getPath()));
			// 步骤2:设置输出流。
			// 注意:出于性能原因使用BufferedOutputStream(对FileOutputStreams很有帮助)。
			out = new BufferedOutputStream(new FileOutputStream(new File(pdf)));
		    // 步骤3:使用所需的输出格式构造fop
		    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
		    // 步骤4:使用标识转换器设置JAXP
		    TransformerFactory factory = TransformerFactory.newInstance();
		    Transformer transformer = factory.newTransformer();
		    // 步骤5:设置XSLT转换的输入和输出
		    // 设置输入流
		    Source src = new StreamSource(new File(fo));
		    // 生成的SAX事件(生成的FO)必须通过管道传递到FOP
		    Result res = new SAXResult(fop.getDefaultHandler());
		    // 步骤6:启动XSLT转换和FOP处理
		    transformer.transform(src, res);
		    
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}finally {
		    try {
		    	if(null != out) {
		    		out.close();
		    		out = null;
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pdf;
	}
	

}
