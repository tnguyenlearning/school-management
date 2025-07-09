import React from 'react';
import { Card, Button, Row, Col, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import urls from '~/common/configs/urls';

const BillingsMPage = () => {
    const navigate = useNavigate();

    const handleNavigation = (path) => {
        try {
            navigate(path);
        } catch (error) {
            message.error('Navigation failed. Please try again.');
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1>Billing Management</h1>
            <Row gutter={[16, 16]} style={{ marginTop: '20px' }}>
                {/* Generate Billing */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Generate Billing" bordered={false}>
                        <p>Manage billing cycles for students efficiently.</p>
                        <Button type="primary" onClick={() => handleNavigation('/billing-cycle')}>
                            Generate Billing
                        </Button>
                    </Card>
                </Col>

                {/* Generate Billing */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Collection" bordered={false}>
                        <p>Collection for students efficiently.</p>
                        <Button type="primary" onClick={() => handleNavigation('/collection')}>
                            Collection
                        </Button>
                    </Card>
                </Col>

                {/* Student Account */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Student Account" bordered={false}>
                        <p>Manage Student Account for students.</p>
                        <Button type="primary" onClick={() => handleNavigation(urls.studentAccountManagement)}>
                            Student Account
                        </Button>
                    </Card>
                </Col>

                {/* Receipt */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Receipt" bordered={false}>
                        <p>Manage Receipt for students.</p>
                        <Button type="primary" onClick={() => handleNavigation(urls.receiptManagement)}>
                            Receipt
                        </Button>
                    </Card>
                </Col>

                {/* Enquiry Billings Detail */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Enquiry Billings" bordered={false}>
                        <p>Enquire detailed billing information for students.</p>
                        <Button type="primary" onClick={() => handleNavigation(urls.enquiryBillings)}>
                            Enquiry Billings
                        </Button>
                    </Card>
                </Col>

                {/* Invoice */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Enquiry Invoice" bordered={false}>
                        <p>View and manage invoices for students.</p>
                        <Button type="primary" onClick={() => handleNavigation(urls.enquiryInvoices)}>
                            Enquiry Invoice
                        </Button>
                    </Card>
                </Col>

                {/* Receipt */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Receipt Management" bordered={false}>
                        <p>Access and manage receipts for payments made.</p>
                        <Button type="primary" onClick={() => handleNavigation('/receipt')}>
                            Receipt
                        </Button>
                    </Card>
                </Col>

                {/* Payment */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Payment Management" bordered={false}>
                        <p>Track and manage student payment records.</p>
                        <Button type="primary" onClick={() => handleNavigation('/payment')}>
                            Payment
                        </Button>
                    </Card>
                </Col>

                {/* Suggestion: Billing Reports */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Billing Reports" bordered={false}>
                        <p>Generate reports on Billing fees and payments.</p>
                        <Button type="primary" onClick={() => handleNavigation('/Billing-reports')}>
                            Reports
                        </Button>
                    </Card>
                </Col>

                {/* Suggestion: Fee Adjustment */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Fee Adjustment" bordered={false}>
                        <p>Adjust fees for specific students or courses.</p>
                        <Button type="primary" onClick={() => handleNavigation('/fee-adjustment')}>
                            Fee Adjustment
                        </Button>
                    </Card>
                </Col>

                {/* Frequency Options */}
                <Col xs={24} sm={12} md={8}>
                    <Card title="Frequency Options" bordered={false}>
                        <p>Manage billing frequency options.</p>
                        <Button type="primary" onClick={() => handleNavigation('/frequency-options')}>
                            Frequency Options
                        </Button>
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default BillingsMPage;
