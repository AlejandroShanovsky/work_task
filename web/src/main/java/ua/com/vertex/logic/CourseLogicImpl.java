package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.List;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;
    private static final Logger LOGGER = LogManager.getLogger(CourseLogicImpl.class);

    @Override
    public List<Course> getAllCoursesWithDept() {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");
        return courseDaoInf.getAllCoursesWithDept();
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf) {
        this.courseDaoInf = courseDaoInf;
    }
}
