package com.sgtesting.datatable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.sgtesting.driver.DriverScript;

public class Datatable extends DriverScript{
	/**************************************
	 * Method Name		: CreateResultFile
	 * Purpose			: creates blank excel result file
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public void CreateResultFile()
	{
		String sFilePath=null;
		FileOutputStream fout=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row=null;
		Cell cell=null;
		int rc=0;
		try{
			wb=new XSSFWorkbook();
			sh=wb.getSheet("Results");
			if(sh==null)
			{
				sh=wb.createSheet("Results");
			}
			
			rc=sh.getPhysicalNumberOfRows();
			if(row==null)
			{
				row=sh.createRow(rc+1);
			}
			
			if(cell==null)
			{
				cell=row.createCell(0);
				cell.setCellValue("TestCaseID");
				
				cell=row.createCell(1);
				cell.setCellValue("ProjectName");
				
				cell=row.createCell(2);
				cell.setCellValue("ModuleName");
				
				cell=row.createCell(3);
				cell.setCellValue("ScriptName");
				
				cell=row.createCell(4);
				cell.setCellValue("ExpectedResult");
				
				cell=row.createCell(5);
				cell.setCellValue("ActualResult");
				
				cell=row.createCell(6);
				cell.setCellValue("Status");
				
				cell=row.createCell(7);
				cell.setCellValue("ScreenShot");
			}
			sFilePath=System.getProperty("user.dir")+"\\Results\\DetailedResults\\Results_"+appInd.getDateTime("ddMMYYYY_hhmmss")+".xlsx";
			System.setProperty("resultFile", sFilePath);
			fout=new FileOutputStream(sFilePath);
			wb.write(fout);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{
				fout.flush();
				fout.close();
				fout=null;
				wb.close();
				wb=null;
				sh=null;
				row=null;
				cell=null;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**************************************
	 * Method Name		: WriteToResult
	 * Purpose			: to write detailed results
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public void WriteResult(WebDriver oDriver, String status,String expected,String actual)
	{
		Workbook wbook;
		Sheet shresult = null;
		Row rowval = null;
		Cell cellval = null;
		FileInputStream finput=null;
		FileOutputStream foutput=null;
		try
		{
			finput=new FileInputStream(System.getProperty("resultFile"));
			wbook=new XSSFWorkbook(finput);
				
			CreationHelper createHelper = wbook.getCreationHelper();
		    //cell style for hyperlinks
		    //by default hyperlinks are blue and underlined
		    CellStyle hlink_style = wbook.createCellStyle();
			Font hlink_font = wbook.createFont();
		    hlink_font.setUnderline(Font.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
					
		    shresult=wbook.getSheet("Results");
			int rc=shresult.getPhysicalNumberOfRows();
			rowval=shresult.createRow(rc+1);
			if (cellval==null)
			{
				cellval=rowval.createCell(6);
				cellval.setCellValue(status);
				
				if (status.equalsIgnoreCase("fail"))
				{
					String path=appInd.captureScreenShot(oDriver);
					cellval=rowval.createCell(7);
					cellval.setCellValue("Error Screenshot");
					Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
				
					String FileAddress=path;
					FileAddress=FileAddress.replace("\\", "/");
					link.setAddress(FileAddress);
					cellval.setHyperlink(link);
					cellval.setCellStyle(hlink_style);
					
					
					 CellStyle style = wbook.createCellStyle();
					 style.setFillBackgroundColor(IndexedColors.RED.getIndex());
					 style.setFillPattern(CellStyle.FINE_DOTS);
				
					 Font font = wbook.createFont();
					 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					 style.setFont(font);

					cellval=rowval.createCell(6);
					cellval.setCellValue(status);
					cellval.setCellStyle(style);
				} 
				else 
				{
					CellStyle style = wbook.createCellStyle();
					 style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
					 style.setFillPattern(CellStyle.FINE_DOTS);
				
					 Font font = wbook.createFont();
					 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					 style.setFont(font);

					cellval=rowval.createCell(6);
					cellval.setCellValue(status);
					cellval.setCellStyle(style);
				}
				
				
				cellval=rowval.createCell(0);
				cellval.setCellValue(System.getProperty("TestCaseID"));
				
				cellval=rowval.createCell(1);
				cellval.setCellValue(System.getProperty("ProjectName"));
				
				cellval=rowval.createCell(2);
				cellval.setCellValue(System.getProperty("ModuleName"));
				
				cellval=rowval.createCell(3);
				cellval.setCellValue(System.getProperty("ScriptName"));
				
				cellval=rowval.createCell(4);
				cellval.setCellValue(expected);
				
				cellval=rowval.createCell(5);
				cellval.setCellValue(actual);
			
				cellval=rowval.createCell(4);
				cellval.setCellValue(expected);
				cellval=rowval.createCell(5);
				cellval.setCellValue(actual);
				/*cellval=rowval.createCell(6);
				cellval.setCellValue(status);*/
			}
			foutput=new FileOutputStream(System.getProperty("resultFile"));
			wbook.write(foutput);			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				foutput.flush();
				finput.close();
				foutput.close();
				wbook=null;
				shresult = null;
				rowval = null;
				cellval = null;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**************************************
	 * Method Name		: getObjectDetails
	 * Purpose			: to write detailed results
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public Map<String, String> getObjectDetails()
	{
		Map<String, String> oMap;
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row=null;
		int rc=0;
		try{
			oMap=new HashMap<String, String>();
			fin=new FileInputStream(System.getProperty("user.dir")+"\\ObjectMapping\\ObjectMappings.xlsx");
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet("Objects");
			rc=sh.getPhysicalNumberOfRows();
			for(int r=1;r<rc;r++)
			{
				row=sh.getRow(r);
				oMap.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue()+"#"+row.getCell(2).getStringCellValue());
			}
			
			return oMap;
		}catch(Exception e)
		{
			return null;
		}
		finally{
			try{
				fin.close();
				fin=null;
				wb.close();
				wb=null;
				sh=null;
				row=null;
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	
	/**************************************
	 * Method Name		: getDataFromExcel
	 * Purpose			: get the data from excel
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean getDataFromExcel(WebDriver oDriver, String strLogicalName, String strSheetName)
	{
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row1=null;
		Row row2=null;
		Cell cell1=null;
		Cell cell2=null;
		int rowNum=0;
		int rc=0;
		String var, val=null;
		try{
			fin=new FileInputStream(System.getProperty("user.dir")+"\\TestData\\"+System.getProperty("ModuleName")+".xlsx");
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet(strSheetName);
			if(sh!=null)
			{
				rc=sh.getPhysicalNumberOfRows();
				
				for(int r=0;r<rc;r++)
				{
					row1=sh.getRow(r);
					cell1=row1.getCell(0);
					if(cell1.getStringCellValue().equalsIgnoreCase(strLogicalName))
					{
						rowNum=r;
						break;
					}
				}
				
				if(rowNum>0)
				{
					row1=sh.getRow(0);
					row2=sh.getRow(rowNum);
					for(int c=0;c<row1.getLastCellNum();c++)
					{
						cell1=row1.getCell(c);
						var=cell1.getStringCellValue();
						cell2=row2.getCell(c);
						if(cell2==null || cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BLANK)
						{
							val="";
						}else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BOOLEAN)
						{
							val=String.valueOf(cell2.getBooleanCellValue());
						}else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().NUMERIC)
						{
							if(HSSFDateUtil.isCellDateFormatted(cell2))
							{
								double d=cell2.getNumericCellValue();
								Calendar cal=Calendar.getInstance();
								cal.setTime(HSSFDateUtil.getJavaDate(d));
								val=String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.YEAR));
							}else{
								var=String.valueOf(cell2.getNumericCellValue());
							}
						}else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().STRING)
						{
							val=cell2.getStringCellValue();
						}
						System.setProperty(var, val);
					}
					return true;
					
				}else{
					datatable.WriteResult(oDriver, "Fail", "The logical name '"+strLogicalName+"' should exist", "the logical name doesnot exist in the testdata sheet");
					return false;
				}
			}else{
				datatable.WriteResult(oDriver, "Fail", "Sheet '"+strSheetName+"' should exist", "Sheet name doesnot exist in the testdata sheet");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "executing 'getDataFromExcel' method", "Exception is :"+e.getMessage());
			return false;
		}
		finally{
			try{
				fin.close();
				fin=null;
				wb.close();
				wb=null;
				sh=null;
				row1=null;
				row2=null;
				cell1=null;
				cell2=null;
			}catch(Exception e)
			{
				datatable.WriteResult(oDriver, "Fail", "executing 'getDataFromExcel' method", "Exception is :"+e.getMessage());
				return false;
			}
		}
	}
	
	
	/**************************************
	 * Method Name		: getRowCount
	 * Purpose			: get the data from excel
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public int getRowCount(String sFilePath, String sSheetName)
	{
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		int rc=0;
		try{
			fin=new FileInputStream(sFilePath);
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet(sSheetName);
			if(sh==null) return -1;
			rc=sh.getPhysicalNumberOfRows();
			return rc-1;
		}catch(Exception e)
		{
			return -1;
		}
		finally{
			try{
				fin.close();
				fin=null;
				sh=null;
				wb.close();
				wb=null;
			}catch(Exception e)
			{
				return -1;
			}
		}
	}
	
	
	
	/**************************************
	 * Method Name		: getCellData
	 * Purpose			: get the data from excel
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public String getCellData(String sFilePath, String sSheetName, int rowNum, String colName)
	{
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row=null;
		Cell cell=null;
		int colNum=0;
		int rc=0;
		String sData=null;
		try{
			fin=new FileInputStream(sFilePath);
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet(sSheetName);
			if(sh==null) return null;
			row=sh.getRow(0);
			for(int c=0;c<row.getLastCellNum();c++)
			{
				cell=row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName))
				{
					colNum=c;
					break;
				}
			}
			
			row=sh.getRow(rowNum-1);
			cell=row.getCell(colNum);
			sData=cell.getStringCellValue();
			return sData;
		}catch(Exception e)
		{
			return null;
		}
		finally{
			try{
				fin.close();
				fin=null;
				sh=null;
				wb.close();
				wb=null;
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	
	/**************************************
	 * Method Name		: setCellData
	 * Purpose			: get the data from excel
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public void setCellData(String strFile, String strSheetName, String strColName, int rowNum, String strData)
	{
		FileInputStream fin=null;
		FileOutputStream fout=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row=null;
		Cell cell=null;
		int colNum=0;
		int rc=0;

		try{
			fin=new FileInputStream(strFile);
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet(strSheetName);
			row=sh.getRow(0);
			for(int c=0;c<row.getLastCellNum();c++)
			{
				cell=row.getCell(c);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(strColName))
				{
					colNum=c;
					break;
				}
			}
			
			row=sh.getRow(rowNum-1);
			cell=row.getCell(colNum);
			cell.setCellValue(strData);
			fout=new FileOutputStream(strFile);
			wb.write(fout);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
				fout.flush();
				fout.close();
				fout=null;
				fin.close();
				fin=null;
				wb.close();
				wb=null;
				sh=null;
				row=null;
				cell=null;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
