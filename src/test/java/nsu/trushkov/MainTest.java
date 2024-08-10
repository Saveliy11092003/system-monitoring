package nsu.trushkov;


import nsu.trushkov.service.SystemMonitoring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    void main_correctWork() {
        Main.main(new String[]{});
    }

    @Test
    void run_correctWork() {
        SystemMonitoring systemMonitoringMock = Mockito.mock(SystemMonitoring.class);
        Main main = new Main(systemMonitoringMock);
        main.run();
        Mockito.verify(systemMonitoringMock).generateReport();
    }
}