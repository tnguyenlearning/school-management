import { apiCallNoPage } from '../apiService';

export const updatePatchSession = (sessionId, session) => apiCallNoPage(`/class-session/${sessionId}`, 'PATCH', session);

export const markListAttendances = (sessionId, attendancesData) => apiCallNoPage(`/api/v2/batch/sessions/${sessionId}/mark-bulk`, 'PUT', attendancesData);
