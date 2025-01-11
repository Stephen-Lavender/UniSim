import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backlogged.univercity.InGameTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


public class TimerTests {
    InGameTimer timer;
    @BeforeEach
    void setUp(){
        timer = new InGameTimer(5);
        timer.initialiseTimerValues();

    }

    @Test
    @DisplayName("Testing Pause")
    void testPause(){
        float dt1 = 0.24f;
        float dt2 = 0.28f;

        float timeLeft = timer.getTimeElapsed(dt1);
        timer.systemStopTime();
        assertEquals(timeLeft, timer.getTimeElapsed(dt2));
    }

    @Test
    @DisplayName("5 Minute Timer")
    void testTiming(){
        float timeLeft = timer.getTimeElapsed(300);
        assertEquals(0, timeLeft);
    }

    @Test
    @DisplayName("3 Years")
    void testConvertingTime(){
        for (float i=0; i < 20000; i++){
            timer.getTimeElapsed(0.015f);
        }
       assertEquals("Summer Holiday:August\n\nYear 3", timer.output());
    }



}
