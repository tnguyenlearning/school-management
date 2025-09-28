import { apiCallNoPage, apiCallV0WithPage } from '../../apiService';

// Get leave requests with pagination and params
export const getLeaveRequests = async ({
    leaveDateFrom,
    leaveDateTo,
    status,
    refundType,
    refundStatus,
    requestedDateFrom,
    requestedDateTo,
    approvedDateFrom,
    approvedDateTo,
    rejectedDateFrom,
    rejectedDateTo,
    page = 0,
    size = 10,
}) => {
    const params = [
        leaveDateFrom ? `leaveDateFrom=${leaveDateFrom}` : '',
        leaveDateTo ? `leaveDateTo=${leaveDateTo}` : '',
        status ? `status=${status}` : '',
        refundType ? `refundType=${refundType}` : '',
        refundStatus ? `refundStatus=${refundStatus}` : '',
        requestedDateFrom ? `requestedDateFrom=${requestedDateFrom}` : '',
        requestedDateTo ? `requestedDateTo=${requestedDateTo}` : '',
        approvedDateFrom ? `approvedDateFrom=${approvedDateFrom}` : '',
        approvedDateTo ? `approvedDateTo=${approvedDateTo}` : '',
        rejectedDateFrom ? `rejectedDateFrom=${rejectedDateFrom}` : '',
        rejectedDateTo ? `rejectedDateTo=${rejectedDateTo}` : '',
        `page=${page}`,
        `size=${size}`,
    ]
        .filter(Boolean)
        .join('&');
    return await apiCallV0WithPage(`/education/v0/leave-requests/search/findLeaveRequestsByFilters?${params}`, 'GET');
};

// Create a leave request
export const createLeaveRequest = async (leaveData) => {
    return await apiCallNoPage('/education/v0/leave-requests', 'POST', leaveData);
};

// Approve or reject a leave request with remarks and date
export const decideLeaveRequest = async (id, status, { remarks, date }) => {
    // status should be 'APPROVED' or 'REJECTED'
    // date: approvedDate or rejectedDate depending on status
    const body = { status, remarks };
    if (status === 'APPROVED') body.approvedDate = date;
    if (status === 'REJECTED') body.rejectedDate = date;
    return await apiCallNoPage(`/education/v0/leave-requests/${id}`, 'PATCH', body);
};

export const updateLeaveRequest = async (id, { refundType, refundStatus, reason }) => {
    return await apiCallNoPage(`/education/v0/leave-requests/${id}`, 'PATCH', { refundType, refundStatus, reason });
};
