import com.backlogged.univercity.*;
import com.backlogged.univercity.Achievements.Achievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementTests {
    Achievement achievement;
    @BeforeEach
    void setUp(){
        achievement = new Achievement("testName", "testDescription");

    }
    @Test
    void testConstructor(){
        assertEquals("testName", achievement.getName());
        assertEquals("testDescription", achievement.getDescription());
    }

    @Test
    void testUnlock(){
        assertFalse(achievement.isUnlocked());
        achievement.unlock();
        assertTrue(achievement.isUnlocked());
    }
}
