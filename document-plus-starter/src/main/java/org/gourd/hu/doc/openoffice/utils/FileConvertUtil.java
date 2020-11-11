package org.gourd.hu.doc.openoffice.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gourd
 */
@Slf4j
public class FileConvertUtil {

	@Value("${openOffice.windowsFileTempLoc}")
	private String windowsFileT;

	@Value("${openOffice.linuxFileTempLoc}")
	private String linuxFileT;

	private static String windowsFileTempLoc;
	private static String linuxFileTempLoc;

	@PostConstruct
	public void init(){
		windowsFileTempLoc = windowsFileT;
		linuxFileTempLoc = linuxFileT;
	}


	public static String getOutputPath() {
		String saveFilePath = windowsFileTempLoc;
		if(CommonUtil.isLinux()){
			saveFilePath = linuxFileTempLoc;
		}
		String fileUrl  = saveFilePath + System.currentTimeMillis() + "." + CommonUtil.OFFICE_TO_PDF;
		return fileUrl;
	}

	public static String getOutHtmlName(){
		String htmlName =  System.currentTimeMillis()+ CommonUtil.OUTHTML ;
		return htmlName ;
	}
	public static File[] traverseFolder2(String path) {
		List<String> folder2 = new ArrayList() ;
		File[] files = null;
		File file = new File(path);
		if (file.exists()) {
			files = file.listFiles();
			if (files.length == 0) {
				log.info("文件夹是空的!");
			} else {
				for (File file2 : files) {
					// 如果该文件夹下 还有文件夹  就将文件夹的路径先保存到list  完成本文件夹的操作后再进行其他文件夹的操作
					if (file2.isDirectory()) {
						log.info("文件夹:" + file2.getAbsolutePath());
						folder2.add(file2.getAbsolutePath()) ;
					} else {
						log.info("文件:" + file2.getAbsolutePath());
					}
				}

			}
			//递归回调
			if(CollectionUtils.isNotEmpty(folder2)){
				for(String folderPath : folder2) {
					traverseFolder2(folderPath);
				}

			}
		} else {
			log.info("文件不存在!");
		}
		return  files ;
	}

	public static String htmlToString(String path){
		String htmlContent = "";
		try {
			BufferedReader br=new BufferedReader(
					new InputStreamReader(new FileInputStream(path), "gb2312"));
			String line;

			while ((line = br.readLine()) != null) {
				htmlContent += line +"\n";
			}
			log.info(htmlContent);

		}catch (Exception e){
			e.printStackTrace();
		}
		return  htmlContent ;
	}
}
