import React, { useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { Card, Form, Input, Button, DatePicker, Select, message, Typography } from 'antd';
import { createLeaveRequest } from '~/common/services/apis/educations/leaveRequestApis';
import dayjs from 'dayjs';

const { Title } = Typography;
const { Option } = Select;

const refundTypes = ['MONEY', 'CREDIT_SESSION', 'NONE'];
const refundStatuses = ['NOT_ELIGIBLE', 'ELIGIBLE', 'REFUNDED'];
const statusOptions = ['REQUESTED', 'APPROVED', 'REJECTED'];

const LeaveRequestPage = () => {
    const { courseId, studentId, sessionId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();

    // Get leaveDate from query string
    const queryParams = new URLSearchParams(location.search);
    const leaveDateStr = queryParams.get('leaveDate');
    const leaveDate = leaveDateStr ? dayjs(leaveDateStr) : null;

    const [form] = Form.useForm();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (values) => {
        setLoading(true);
        const payload = {
            student: `/students/${studentId}`,
            classSession: `/classSessions/${sessionId}`,
            leaveDate: leaveDate ? leaveDate.format('YYYY-MM-DD') : values.leaveDate.format('YYYY-MM-DD'),
            status: values.status,
            refundType: values.refundType,
            refundStatus: values.refundStatus,
            requestedDate: values.requestedDate.format('YYYY-MM-DD'),
            reason: values.reason,
        };
        const { response, errorMessage } = await createLeaveRequest(payload);
        setLoading(false);
        if (response) {
            message.success('Leave request submitted!');
            navigate(-1);
        } else {
            message.error('Failed: ' + errorMessage);
        }
    };

    return (
        <div style={{ background: '#f5f6fa', minHeight: '100vh', padding: '32px' }}>
            <Card style={{ maxWidth: 600, margin: '0 auto', borderRadius: 12, boxShadow: '0 4px 24px rgba(0,0,0,0.08)' }}>
                <Title level={3} style={{ textAlign: 'center', marginBottom: 24 }}>
                    Leave Request
                </Title>
                <Form
                    form={form}
                    layout="vertical"
                    onFinish={handleSubmit}
                    initialValues={{
                        leaveDate: leaveDate,
                        status: 'REQUESTED',
                        refundType: 'CREDIT_SESSION',
                        refundStatus: 'ELIGIBLE',
                    }}
                >
                    <Form.Item label="Student ID">
                        <Input value={studentId} disabled />
                    </Form.Item>
                    <Form.Item label="Class Session ID">
                        <Input value={sessionId} disabled />
                    </Form.Item>
                    <Form.Item label="Leave Date" name="leaveDate" rules={[{ required: true }]}>
                        <DatePicker style={{ width: '100%' }} value={leaveDate} disabled />
                    </Form.Item>
                    <Form.Item label="Requested Date" name="requestedDate" rules={[{ required: true }]}>
                        <DatePicker style={{ width: '100%' }} />
                    </Form.Item>
                    <Form.Item label="Status" name="status" rules={[{ required: true }]}>
                        <Select>
                            {statusOptions.map((opt) => (
                                <Option key={opt} value={opt}>
                                    {opt}
                                </Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item label="Refund Type" name="refundType" rules={[{ required: true }]}>
                        <Select>
                            {refundTypes.map((opt) => (
                                <Option key={opt} value={opt}>
                                    {opt}
                                </Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item label="Refund Status" name="refundStatus" rules={[{ required: true }]}>
                        <Select>
                            {refundStatuses.map((opt) => (
                                <Option key={opt} value={opt}>
                                    {opt}
                                </Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item label="Reason" name="reason" rules={[{ required: true }]}>
                        <Input.TextArea rows={3} maxLength={200} placeholder="Enter reason" />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" loading={loading} style={{ width: '100%' }}>
                            Submit Leave Request
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default LeaveRequestPage;
