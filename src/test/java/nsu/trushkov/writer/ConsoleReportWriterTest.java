package nsu.trushkov.writer;

import nsu.trushkov.builder.ReportCreator;
import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.enumeration.IncorrectTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleReportWriterTest {

    @Mock
    ReportCreator reportCreator;

    @InjectMocks
    ConsoleReportWriter consoleReportWriter;

    @Test
    void write() {
        //given
        ArgumentCaptor<DataForReport> argumentCaptor = ArgumentCaptor.forClass(DataForReport.class);
        doReturn("report").when(reportCreator).buildReport(argumentCaptor.capture());

        //when
        DataForReport data = null;
        consoleReportWriter.write(data);

        //then
        verify(reportCreator, times(1)).buildReport(argumentCaptor.getValue());
    }
}