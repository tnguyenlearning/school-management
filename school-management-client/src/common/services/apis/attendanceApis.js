import { apiCallNoPage } from '../apiService';

export const markAttendance = (sessionId, attendanceData) => apiCallNoPage(`/education/v2/attendances/sessions/${sessionId}/mark`, 'POST', attendanceData);

export const markListAttendances = (sessionId, attendancesData) => apiCallNoPage(`/education/v2/attendances/sessions/${sessionId}/mark-bulk`, 'POST', attendancesData);
