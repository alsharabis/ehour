package net.rrm.ehour.ui.common.menu

import net.rrm.ehour.ui.timesheet.page.MonthOverviewPage
import net.rrm.ehour.ui.timesheet.export.TimesheetExportPage
import net.rrm.ehour.ui.report.page.GlobalReportPage
import net.rrm.ehour.ui.pm.page.ProjectManagement
import net.rrm.ehour.ui.admin.user.page.UserAdminPage
import net.rrm.ehour.ui.admin.config.page.MainConfigPage
import net.rrm.ehour.ui.audit.page.AuditReportPage
import java.util.{List => JList}
import net.rrm.ehour.ui.admin.assignment.page.AssignmentAdmin
import org.apache.wicket.request.mapper.parameter.PageParameters
import net.rrm.ehour.ui.admin.department.DepartmentAdminPage
import net.rrm.ehour.ui.admin.customer.CustomerAdminPage
import net.rrm.ehour.ui.admin.project.ProjectAdmin
import net.rrm.ehour.ui.admin.backup.BackupDbPage
import java.util
import net.rrm.ehour.ui.financial.lock.LockAdminPage

object MenuDefinition {

  def createMenuDefinition:JList[_ <: MenuItem] = {
    val params = new PageParameters()
    params.add(MonthOverviewPage.PARAM_OPEN, MonthOverviewPage.OpenPanel.TIMESHEET)

    val enterHours = LinkItem("nav.hours.enter", classOf[MonthOverviewPage], Some(params))
    val hoursOverview = LinkItem("nav.hours.overview", classOf[MonthOverviewPage])
    val monthExport = LinkItem("nav.hours.export", classOf[TimesheetExportPage])
    val hoursDropdown = DropdownMenu("nav.hours.yourHours", util.Arrays.asList(enterHours, hoursOverview, monthExport))

    val report = LinkItem("nav.report", classOf[GlobalReportPage])
    val pm = LinkItem("nav.pm", classOf[ProjectManagement])

    val lock = LinkItem("nav.lock", classOf[LockAdminPage])

    val deptAdmin = LinkItem("nav.admin.departments", classOf[DepartmentAdminPage])
    val userAdmin = LinkItem("nav.admin.users", classOf[UserAdminPage])
    val customerAdmin = LinkItem("nav.admin.customers", classOf[CustomerAdminPage])
    val projectAdmin = LinkItem("nav.admin.projects", classOf[ProjectAdmin])
    val assignmentAdmin = LinkItem("nav.admin.assignments", classOf[AssignmentAdmin])
    val manageDropdown = DropdownMenu("nav.admin.manage", util.Arrays.asList(deptAdmin, userAdmin, customerAdmin, projectAdmin, assignmentAdmin))

    val mainConfig = LinkItem("nav.admin.config", classOf[MainConfigPage])
    val auditReport = LinkItem("nav.admin.audit", classOf[AuditReportPage])
    val export = LinkItem("nav.admin.export", classOf[BackupDbPage])
    val systemDropdown = DropdownMenu("nav.admin", util.Arrays.asList(mainConfig, auditReport, export))

    util.Arrays.asList(hoursDropdown, report, pm, lock, manageDropdown, systemDropdown)
  }
}
