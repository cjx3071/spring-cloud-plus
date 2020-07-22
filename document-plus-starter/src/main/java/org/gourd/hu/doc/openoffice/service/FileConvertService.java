package org.gourd.hu.doc.openoffice.service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Administrator on 2017/2/15.
 */
public interface FileConvertService {
    void officeConvertToPdf(File[] files, String outputPath, HttpServletResponse response) throws  Exception ;
}
