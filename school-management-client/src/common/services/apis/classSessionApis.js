import { apiCallNoPage } from '../apiService';

export const getSessionsByCourseId = async (courseId) => await apiCallNoPage(`/courses/${courseId}/classSessions`, 'GET');

export const getSessionById = (sessionId) => apiCallNoPage(`/class-session/${sessionId}`);

export const updateSessionStatus = (sessionId, newStatus) => apiCallNoPage(`/api/class-sessions/${sessionId}/status?status=${newStatus}`, 'PATCH');

export const updatePatchSession = (sessionId, session) => apiCallNoPage(`/class-session/${sessionId}`, 'PATCH', session);

export const addManualSession = (courseId, session) => apiCallNoPage(`/api/class-sessions/courses/${courseId}/add-session`, 'POST', session);
