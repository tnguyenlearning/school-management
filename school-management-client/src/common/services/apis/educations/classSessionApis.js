import { apiCallNoPage, apiCallV0WithPage } from '../../apiService';

export const generateSessionsForCourse = (courseId, monthsAhead) => apiCallNoPage(`/education/v2/class-sessions/courses/${courseId}/generate?monthsAhead=${monthsAhead}`, 'POST');

export const getSessionsByCourseId = async (params) => {
    const queryParams = new URLSearchParams();
    Object.keys(params).forEach((key) => {
        if (params[key] !== undefined && params[key] !== null && params[key] !== '') {
            queryParams.append(key, params[key]);
        }
    });
    return await apiCallV0WithPage(`/education/v0/class-session/search/searchClassSessionsByCourseId?${queryParams.toString()}`, 'GET');
};

export const searchClassSessions = async (params) => {
    const queryParams = new URLSearchParams();
    Object.keys(params).forEach((key) => {
        if (params[key] !== undefined && params[key] !== null && params[key] !== '') {
            queryParams.append(key, params[key]);
        }
    });
    return await apiCallV0WithPage(`/education/v0/class-session/search/searchClassSessions?${queryParams.toString()}`, 'GET');
};

export const getSessionById = (sessionId) => apiCallNoPage(`/education/v0/class-session/${sessionId}`);

export const updateSessionStatus = (sessionId, newStatus) => apiCallNoPage(`/education/v2/class-sessions/${sessionId}/status?status=${newStatus}`, 'PATCH');

export const updatePatchSession = (sessionId, session) => apiCallNoPage(`/education/v0/class-session/${sessionId}`, 'PATCH', session);

export const addManualSession = (courseId, session) => apiCallNoPage(`/education/v2/class-sessions/courses/${courseId}/add-session`, 'POST', session);
