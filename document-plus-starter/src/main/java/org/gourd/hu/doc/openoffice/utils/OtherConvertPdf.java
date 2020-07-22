package org.gourd.hu.doc.openoffice.utils;
import java.io.*;

/**
 * Created by Administrator on 2017/2/16.
 */
public class OtherConvertPdf {
    public static void main(String[] args) {
        OtherConvertPdf pdf = new OtherConvertPdf();
        pdf.processFile("e:/123/", "123.pdf", "e:/test1221.gd");
    }
    public boolean processFile(String folder, String filename,String baseurl){
        boolean res = false;
        InputStreamReader isr = null;
        try{
            File f = new File(folder);
            if (f.isDirectory()) {
                f.mkdir();
            }
            String fullfilename = folder + filename;
            File file = new File(baseurl);
            byte[] b = new byte[1024] ;
            InputStream in = new FileInputStream(file) ;
            isr = new InputStreamReader(in);
            OtherConvertPdf converter = new OtherConvertPdf();

            res = converter.generatePDF(isr, fullfilename);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{isr.close();isr=null;}catch(Exception e){}
        }

        return res;

    }
    /**
     * 生成pdf
     * @param isr
     * @param pdfFilename
     * @return
     */
    public boolean generatePDF(InputStreamReader isr, String pdfFilename){
        FileOutputStream fos = null;
        boolean res = false;
        try{
            File pdfFileNmae = new File(pdfFilename);
            if(!pdfFileNmae.exists()){
                pdfFileNmae.mkdirs() ;
            }
            fos = new FileOutputStream(pdfFileNmae);
////            PD4ML pd4ml = new PD4ML();
//            pd4ml.setPageInsets(new Insets(10, 10, 10, 10));
//            pd4ml.setHtmlWidth(1000);
//            pd4ml.enableImgSplit(false);
            //Dimension format = PD4ML.A4;
            //pd4ml.setPageSize(pd4ml.changePageOrientation(format)); // landscape page orientation
//            pd4ml.useTTF("java:fonts", true );
            //pd4ml.enableDebugInfo();

            // footer if needed
            //PD4PageMark footer = new PD4PageMark();
            //footer.setPageNumberTemplate("page $[page] of $[total]");
            //footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
            //footer.setInitialPageNumber(1);
            //footer.setPagesToSkip(1);
            //footer.setFontSize(10);
            //footer.setAreaHeight(18);
            //pd4ml.setPageFooter(footer);


//            pd4ml.render(isr, fos);
            res = true;

            //res = baos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{fos.close();fos=null;}catch(Exception ee){}
        }

        return res;
    }
}
