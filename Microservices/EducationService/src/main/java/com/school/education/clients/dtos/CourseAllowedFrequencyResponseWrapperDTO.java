package com.school.education.clients.dtos;

import java.util.List;

import com.school.education.clients.dto.CourseAllowedFrequencyResponseDTO;

public class CourseAllowedFrequencyResponseWrapperDTO {
    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class Embedded {
        private List<CourseAllowedFrequencyResponseDTO> courseAllowedFrequency;

        public List<CourseAllowedFrequencyResponseDTO> getCourseAllowedFrequency() {
            return courseAllowedFrequency;
        }

        public void setCourseAllowedFrequency(List<CourseAllowedFrequencyResponseDTO> courseAllowedFrequency) {
            this.courseAllowedFrequency = courseAllowedFrequency;
        }
    }
}

