/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * eHour is sponsored by TE-CON  - http://www.te-con.nl/
 */

package net.rrm.ehour.ui.timesheet.export.excel;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.rrm.ehour.config.dao.BinaryConfigurationDAO;
import net.rrm.ehour.config.service.ConfigurationServiceImpl;
import net.rrm.ehour.data.DateRange;
import net.rrm.ehour.report.criteria.ReportCriteria;
import net.rrm.ehour.report.criteria.UserCriteria;
import net.rrm.ehour.report.reports.ReportData;
import net.rrm.ehour.report.reports.element.FlatReportElement;
import net.rrm.ehour.report.service.DetailedReportService;
import net.rrm.ehour.ui.common.AbstractSpringWebAppTester;
import net.rrm.ehour.ui.common.report.Report;
import net.rrm.ehour.ui.report.trend.PrintReport;
import net.rrm.ehour.util.DateUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * Created on Apr 10, 2009, 2:05:13 PM
 * @author Thies Edeling (thies@te-con.nl) 
 *
 */
public class ExportReportExcelTest extends AbstractSpringWebAppTester
{
	private ConfigurationServiceImpl configService;
	private DetailedReportService detailedReportService;

	@Before
	public void setUp() throws Exception
	{
		super.setUp();

		getConfig().setFirstDayOfWeek(Calendar.MONDAY);
		
		detailedReportService = createMock(DetailedReportService.class);
		getMockContext().putBean("detailedReportService", detailedReportService);
		
		configService = new ConfigurationServiceImpl();
		getMockContext().putBean("configurationService", configService);
		
		BinaryConfigurationDAO binConfigfDao = createMock(BinaryConfigurationDAO.class);
		configService.setBinConfigDAO(binConfigfDao);
	}
	
	@Test
	public void produceExcelReport() throws IOException
	{
		List<FlatReportElement> elements = createMonthData();
		
		ReportData data = new ReportData(elements, getRangeForCurrentMonth());

		UserCriteria userCriteria = new UserCriteria();
		userCriteria.setReportRange(getRangeForCurrentMonth());
		ReportCriteria criteria = new ReportCriteria(userCriteria);
		Report report = new PrintReport(criteria);
		
		expect(detailedReportService.getDetailedReportData(criteria))
			.andReturn(data);
		
		replay(detailedReportService);
		byte[] excelData = new ExportReportExcel().getExcelData(report);
		assertTrue(excelData.length > 0);
		writeByteData(excelData);
		
		verify(detailedReportService);
	}
	
	private List<FlatReportElement> createMonthData()
	{
		List<FlatReportElement>	elements = new ArrayList<FlatReportElement>();
		
		DateRange range = getRangeForCurrentMonth();
		
		List<Date> month = DateUtil.createDateSequence(range, getConfig());
		
		for (Date date : month)
		{
			if (Math.random() >= 0.2)
			{
				elements.add(createElement(date));	
			}
		}
		return elements;
	}

	private DateRange getRangeForCurrentMonth()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		
		DateRange range = DateUtil.getDateRangeForMonth(cal);
		return range;
	}

	private FlatReportElement createElement(Date date)
	{
		FlatReportElement element = new FlatReportElement();
		element.setCustomerCode("TE1");
		element.setCustomerName("TEST #1");
		element.setProjectName("Project #1");
		element.setDayDate(date);
		element.setHours(Math.random() * 8);
		element.setTotalHours(element.getHours());

		return element;
	}
	
	
	@Test
	public void produceForEmptyMonth() throws IOException
	{
		List<FlatReportElement>	elements = new ArrayList<FlatReportElement>();
		
		ReportData data = new ReportData(elements, DateUtil.getDateRangeForMonth(new Date()));
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.setReportRange(getRangeForCurrentMonth());
		ReportCriteria criteria = new ReportCriteria(userCriteria);
		Report report = new PrintReport(criteria);
		
		expect(detailedReportService.getDetailedReportData(criteria))
			.andReturn(data);
		
		replay(detailedReportService);
		byte[] excelData = new ExportReportExcel().getExcelData(report);
		assertTrue(excelData.length > 0);
//		writeByteData(excelData);
		
		verify(detailedReportService);
	}

	/**
	 * @param excelData
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeByteData(byte[] excelData) throws FileNotFoundException, IOException
	{
		File outfile = new File("/home/thies/test.xls");
		FileOutputStream fos = new FileOutputStream(outfile);
		fos.write(excelData);
		fos.close();
	}
}