package nsu.trushkov.service;

import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.writer.ReportWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SystemMonitoringTest {

    @Mock
    HashTableInitializer initializer;

    @Mock
    HashTableHandler handler;

    @Mock
    ReportWriter reportWriter;

    @InjectMocks
    SystemMonitoring systemMonitoring;

    @Test
    void generateReport_correctWork() {
        //when
        systemMonitoring.generateReport();

        //then
        verify(initializer, times(1)).init(any(), any());
        verify(handler, times(1)).handle(any(), any());
        verify(reportWriter, times(1)).write(any());
    }
}