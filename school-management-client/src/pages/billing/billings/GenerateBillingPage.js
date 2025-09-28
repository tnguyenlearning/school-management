import { Button, DatePicker, Form, Input, Table, message, Card, Typography, Row, Col } from 'antd';
import { useState } from 'react';
import { generateBillingCycleForRange } from '~/common/services/apis/billingApis';
import FormItem from '~/components/FormItem';

const { Title } = Typography;

const GenerateBillingPage = () => {
    const [loading, setLoading] = useState(false);
    const [billingResults, setBillingResults] = useState([]);
    const [form] = Form.useForm();

    const handleGenerateBilling = async () => {
        try {
            const values = await form.validateFields();
            const { effDate, fromStudentCode, toStudentCode } = values;
            setLoading(true);
            const payload = {
                effDate: effDate.format('YYYY-MM-DD'),
                fromStudentCode: fromStudentCode ? fromStudentCode : '',
                toStudentCode: toStudentCode ? fromStudentCode : '',
            };
            const { response, errorMessage } = await generateBillingCycleForRange(payload);
            if (errorMessage) {
                message.error(errorMessage || 'Failed to generate billing cycle.');
            } else if (!response || response.length === 0) {
                message.info('No billing results found for the given criteria.');
                setBillingResults([]);
            } else {
                setBillingResults(response);
                message.success('Billing cycle generated successfully!');
            }
        } catch (error) {
            message.warning('Please fill out all fields.');
        } finally {
            setLoading(false);
        }
    };

    const columns = [
        { title: 'ID', dataIndex: 'id', key: 'id' },
        { title: 'Course Code', dataIndex: 'courseCode', key: 'courseCode' },
        { title: 'Student Code', dataIndex: 'studentCode', key: 'studentCode' },
        { title: 'Enrollment ID', dataIndex: 'enrollmentId', key: 'enrollmentId' },
        { title: 'Total Fee', dataIndex: 'totalFee', key: 'totalFee', render: (value) => `\$${value}` },
        { title: 'Total Paid', dataIndex: 'totalPaid', key: 'totalPaid', render: (value) => (value !== null ? `\$${value}` : '-') },
        { title: 'Total Discount', dataIndex: 'totalDiscount', key: 'totalDiscount', render: (value) => `\$${value}` },
        { title: 'Total Penalty', dataIndex: 'totalPenalty', key: 'totalPenalty', render: (value) => `\$${value}` },
        { title: 'Total Refund', dataIndex: 'totalRefund', key: 'totalRefund', render: (value) => (value !== null ? `\$${value}` : '-') },
        { title: 'Generated Date', dataIndex: 'generatedDate', key: 'generatedDate' },
        { title: 'Cycle Start Date', dataIndex: 'cycleStartDate', key: 'cycleStartDate' },
        { title: 'Cycle End Date', dataIndex: 'cycleEndDate', key: 'cycleEndDate' },
        { title: 'Next Cycle Start Date', dataIndex: 'nextCycleStartDate', key: 'nextCycleStartDate' },
        { title: 'Frequency', dataIndex: 'frequency', key: 'frequency' },
        { title: 'Total Learning Days', dataIndex: 'totalLearningDays', key: 'totalLearningDays' },
        { title: 'Cycle Number', dataIndex: 'cycleNum', key: 'cycleNum' },
        { title: 'Status', dataIndex: 'status', key: 'status' },
    ];

    return (
        <div style={{ padding: '20px' }}>
            <Title level={2}>Generate Billing</Title>
            <Card style={{ marginBottom: '20px' }}>
                <Form layout="vertical" form={form}>
                    <Row justify="space-between" gutter={16}>
                        <FormItem label="Effective Date" name="effDate" rules={[{ required: true }]} element={<DatePicker />} />
                        <FormItem label="From Student Code" name="fromStudentCode" element={<Input placeholder="Enter From Student Code" />} />
                        <FormItem label="To Student Code" name="toStudentCode" element={<Input placeholder="Enter To Student Code" />} />
                    </Row>
                    <Form.Item>
                        <Button type="primary" onClick={handleGenerateBilling} loading={loading} disabled={loading}>
                            Generate Billing
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
            <Title level={3}>Billing Results</Title>
            <Table
                dataSource={billingResults.map((item) => ({
                    ...item,
                    key: item.id,
                }))}
                columns={columns}
                loading={loading}
                pagination={{ pageSize: 10 }}
            />
        </div>
    );
};

export default GenerateBillingPage;
