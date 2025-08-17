package com.tnsif.coursemanagementmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    // Constructor injection for CourseRepository dependency.
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Retrieves all courses
    public List<Course> getAllCourses() throws ServiceException {
        return courseRepository.findAll();
    }

    // Retrieves a course by its ID
    public Optional<Course> getCourseById(Long id) throws ServiceException {
        return courseRepository.findById(id);
    }

    // Retrieves courses with ID less than given
    public List<Course> getCoursesByIdLessThan(long id) throws ServiceException {
        return courseRepository.retrieveByIdLessThan(id);
    }

    // Retrieves course(s) by title
    public List<Course> getCourse(String title) throws ServiceException {
        return courseRepository.findByTitle(title);
    }

    // Retrieves courses by instructor (case-insensitive)
    public List<Course> getCoursesByInstructorIgnoreCase(String instructor) throws ServiceException {
        return courseRepository.findByInstructorIgnoreCase(instructor);
    }

    // Retrieves course(s) by description
    public List<Course> getCourseByDescription(String description) throws ServiceException {
        return courseRepository.findByDescription(description);
    }

    // Retrieves course(s) by start date
    public List<Course> getCourseByStartDate(Date startDate) throws ServiceException {
        return courseRepository.findByStartDate(startDate);
    }

    // Retrieves courses by start date range
    public List<Course> getCoursesByStartDateBetween(Date startDateStartOfDay, Date startDateEndOfDay) throws ServiceException {
        return courseRepository.retrieveByStartDateBetween(startDateStartOfDay, startDateEndOfDay);
    }

    // Retrieves course(s) by end date
    public List<Course> getCourseByEndDate(Date endDate) throws ServiceException {
        return courseRepository.findByEndDate(endDate);
    }

    // Retrieves courses by title containing keyword
    public List<Course> findByTitleContaining(String keyword) throws ServiceException {
        return courseRepository.findByTitleContaining(keyword);
    }

    // Retrieves courses with pagination
    public Page<Course> findAll(Pageable pageable) throws ServiceException {
        return courseRepository.findAll(pageable);
    }

    // Retrieves top N courses ordered by end date descending
    public List<Course> findTopNCoursesByEndDate(int n) throws ServiceException {
        Pageable pageable = PageRequest.of(0, n);
        return courseRepository.findTopNByOrderByEndDateDesc(pageable);
    }

    // Retrieves courses ordered by start date ascending
    public List<Course> findAllCoursesOrderByStartDateAsc() throws ServiceException {
        return courseRepository.findAllByOrderByStartDateAsc();
    }

    // Find courses by instructor and title containing a keyword
    public List<Course> findByInstructorAndTitleContaining(String instructor, String keyword) throws ServiceException {
        return courseRepository.findByInstructorAndTitleContaining(instructor, keyword);
    }

    // Find courses with pagination and sorting by title
    public Page<Course> findByTitleContaining(String keyword, Pageable pageable) throws ServiceException {
        return courseRepository.findByTitleContaining(keyword, pageable);
    }

    // Count courses by instructor
    public long countByInstructor(String instructor) throws ServiceException {
        return courseRepository.countByInstructor(instructor);
    }

    // Create a new course
    public Course createCourse(Course course) throws ServiceException {
        return courseRepository.save(course);
    }

    // Delete a course by its ID
    public void deleteCourse(Long id) throws ServiceException {
        courseRepository.deleteById(id);
    }

    // Update a course with given ID
    public Optional<Course> updateCourse(Long id, Course updatedCourse) throws ServiceException {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            // Update course attributes
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setStartDate(updatedCourse.getStartDate());
            course.setEndDate(updatedCourse.getEndDate());
            course.setInstructor(updatedCourse.getInstructor());
            // Save the updated course
            return Optional.of(courseRepository.save(course));
        } else {
            return Optional.empty(); // Course with given ID not found
        }
    }
}
