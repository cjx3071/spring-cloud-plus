package org.gourd.hu.doc.openoffice.utils;

/**
 *
 * @author gourd.hu
 * @date 2017/2/15
 */
public class CommonUtil {
    /**
     * office中.doc格式
     */
    public static final String OFFICE_DOC = "doc";
    /**
     * office中.docx格式
     */
    public static final String OFFICE_DOCX = "docx";
    /**
     * office中.xls格式
     */
    public static final String OFFICE_XLS = "xls";
    /**
     * office中.xlsx格式
     */
    public static final String OFFICE_XLSX = "xlsx";
    /**
     * office中.ppt格式
     */
    public static final String OFFICE_PPT = "ppt";
    /**
     * office中.pptx格式
     */
    public static final String OFFICE_PPTX = "pptx";
    /**
     * pdf格式
     */
    public static final String OFFICE_TO_PDF = "pdf";
    /**
     * gd格式
     */
    public static final String TEXT_TO_PDF = "text";

    /**
     * 书生gd格式
     */
    public static final String GD_TO_PDF = "gd";
    /**
     * 方正ceb格式
     */
    public static final String CEB_TO_PDF = "ceb";

    public static final String WINDOWS_PDFHTMLEXPATH = "D:\\Documents\\Downloads\\pdf2htmlEX-v1.0\\pdf2htmlEX.exe" ;
    public static final String LINUX_PDFHTMLEXPATH = "D:\\Documents\\Downloads\\pdf2htmlEX-v1.0\\pdf2htmlEX.exe" ;
    public static final String WINDOWS_TARGETPATH  =  "D:\\Documents\\Downloads\\pdf2htmlEX-v1.0\\HTML" ;
    public static final String LINUX_TARGETPATH  =  "D:\\Documents\\Downloads\\pdf2htmlEX-v1.0\\HTML" ;

    public static final String OUTHTML = ".html";

    public static final String WIN = "windows";

    public static boolean isLinux() {
        String osType = System.getProperties().getProperty("os.name").toLowerCase();
        if (osType.startsWith(CommonUtil.WIN)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean inTypes(String extensionName){
        return (extensionName.equals(CommonUtil.OFFICE_DOC)
                || extensionName.equals(CommonUtil.OFFICE_DOCX)
                || extensionName.equals(CommonUtil.OFFICE_XLS)
                || extensionName.equals(CommonUtil.OFFICE_XLSX)
                || extensionName.equals(CommonUtil.OFFICE_PPT)
                || extensionName.equals(CommonUtil.OFFICE_PPTX)
                ||extensionName.equals(CommonUtil.TEXT_TO_PDF));
    }

}
