import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class bottleneckTest {

    @Test
    void noCoursesDefined() throws PrerequisiteNotScheduledYetException {
        Curriculum testCurriculum = new Curriculum();

        Set<String> bottlenecks;
        bottlenecks = testCurriculum.bottleneckCourses();
        assertNotNull( bottlenecks );
        assertEquals( 0, bottlenecks.size(), "No courses set-up for bottleneck detection" );
    }
}