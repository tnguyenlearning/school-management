import { apiCallNoPage } from '../apiService';

export const getCourses = async () => await apiCallNoPage('/education/v0/courses');

export const getCourseById = async (courseId) => {
    const { response, errorMessage } = await apiCallNoPage(`/education/v0/courses/${courseId}`);
    if (errorMessage) {
        return { response: null, errorMessage };
    }
    // const { response: frequencyResponse, errorMessage: frequencyError } = await apiCallNoPage(`/education/v0/courses/${courseId}/frequencyOptions`);
    // if (frequencyError) {
    //     return { response: { ...response, frequencyOptions: [] }, errorMessage: frequencyError };
    // }
    // const frequencyOptions = frequencyResponse._embedded.courseFrequencyOptions.map((detail) => ({
    //     id: detail.id,
    //     frequency: detail.frequency,
    //     totalLearningDays: detail.totalLearningDays,
    //     feeAmount: detail.feeAmount,
    // }));
    //return { response: { ...response, frequencyOptions }, errorMessage: null };
    return { response: { ...response }, errorMessage: null };
};

export const createCourse = async (courseData) => apiCallNoPage('/education/v2/courses', 'POST', courseData);
export const updateCourse = async (id, courseData) => apiCallNoPage(`/education/v2/courses/${id}`, 'PUT', courseData);
export const deleteCourse = async (id) => apiCallNoPage(`/education/v0/courses/${id}`, 'DELETE');

export const generateSessionsForCourse = (courseId, monthsAhead) => apiCallNoPage(`/education/v2/courses/${courseId}/sessions/generate?monthsAhead=${monthsAhead}`, 'POST');
