package net.rrm.ehour.it.scenario;

import net.rrm.ehour.it.AbstractScenario;
import net.rrm.ehour.it.WicketBy;
import org.junit.Test;

import java.sql.SQLException;

import static net.rrm.ehour.it.driver.EhourApplicationDriver.login;
import static net.rrm.ehour.it.driver.TimesheetApplicationDriver.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimesheetScenario extends AbstractScenario {
    @Test
    public void should_book_hours() {
        createUserAndAssign();

        login("thies", "a");

        assertInOverviewPage();

        book8Hours();
    }

    @Test
    public void should_not_delete_previous_day_comment_when_cancelling_edit_comment() throws SQLException {
        createUserAndAssign();

        login("thies", "a");

        clickInWeek(1);
        String comment = "this is a comment";
        addDayComment(2, comment);
        openDayCommentModal(2);
        cancelDayCommentModal(2);

        String base = openDayCommentModal(2);// now this should be filled by the previous booking
        assertEquals(comment, Driver.findElement(WicketBy.wicketPath(base + "_dayWin_content_comment")).getText());
    }

    private void book8Hours() {
        clickInWeek(1);

        bookHours(2, 8f);

        addDayComment(2, "some comment");

        submitTimesheet();

        assertTrue(getServerMessage().startsWith("8 hours booked"));
    }
}
