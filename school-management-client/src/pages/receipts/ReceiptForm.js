import React, { useEffect, useState } from 'react';
import { Form, Input, Select, Button, DatePicker, Spin, message } from 'antd';
import { PAYMENT_METHODS } from '~/common/constants/app-enums';
import { fetchReceiptDetails, createReceipt } from '~/common/services/apis/receiptApi';
import { useParams, useNavigate } from 'react-router-dom';
import urls from '~/common/configs/urls';

const { Option } = Select;

const ReceiptForm = ({ match }) => {
    const { action, studentCode, studentAccountId } = useParams(); // Retrieve session ID and course ID from URL

    const [receipt, setReceipt] = useState({});
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate(); // Initialize navigation hook

    console.log('studentAccountId', studentAccountId);

    useEffect(() => {
        if (!studentCode || !studentAccountId) {
            message.error('Student Code or Student Account ID is missing.');
            return;
        }

        if (action === 'enquiry') {
            setLoading(true);
            fetchReceiptDetails(match.params.id_or_code).then((data) => {
                setReceipt(data);
                setLoading(false);
            });
        } else {
            setReceipt((prev) => ({ ...prev, studentCode })); // Auto-fill studentCode
        }
    }, [action, studentCode, studentAccountId]);

    const handleSubmit = async (values) => {
        const { response, errorMessage } = await createReceipt(studentAccountId, values);
        console.log('Receipt creation response:', response, 'Error message:', errorMessage);
        if (errorMessage) {
            message.error(errorMessage);
        } else {
            message.success('Receipt created successfully!');
            navigate(`${urls.baseBalanceDetails}/${studentAccountId}`); // Navigate to Balance Details page
        }
    };

    if (loading) return <Spin />;

    return (
        <Form initialValues={{ ...receipt, studentCode }} onFinish={handleSubmit} layout="vertical" disabled={action === 'enquiry'}>
            <Form.Item label="Student Code" name="studentCode">
                <Input disabled={true} />
            </Form.Item>
            <Form.Item label="Amount" name="amount" rules={[{ required: true }]}>
                <Input type="number" />
            </Form.Item>
            <Form.Item label="Receipt Date" name="receiptDate" rules={[{ required: true }]}>
                <DatePicker />
            </Form.Item>
            <Form.Item label="Payment Method" name="paymentMethod" rules={[{ required: true }]}>
                <Select>
                    {Object.keys(PAYMENT_METHODS).map((key) => (
                        <Option key={key} value={key}>
                            {PAYMENT_METHODS[key]}
                        </Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit" disabled={action === 'enquiry'}>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
};

export default ReceiptForm;
