package nsu.trushkov;


import nsu.trushkov.service.SystemMonitoring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    void main_correctWork() {
        try(MockedStatic mockedStatic = Mockito.mockStatic(Main.class, Mockito.CALLS_REAL_METHODS)) {

            //when
            Main.main(new String[]{});

            //then
            mockedStatic.verify(() -> Main.main(new String[]{}));
        }
    }
}