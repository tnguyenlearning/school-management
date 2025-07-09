import React from 'react';
import { Tabs } from 'antd';
import { useParams } from 'react-router-dom';
import PaymentTab from './PaymentTab';
import ReceiptTab from './ReceiptTab';

const { TabPane } = Tabs;

const BalanceDetailsPage = () => {
    const { studentAccountId } = useParams();

    return (
        <div>
            <h1>Balance Details</h1>
            <Tabs defaultActiveKey="1">
                <TabPane tab="Payments" key="1">
                    <PaymentTab studentAccountId={studentAccountId} />
                </TabPane>
                <TabPane tab="Receipts" key="2">
                    <ReceiptTab studentAccountId={studentAccountId} />
                </TabPane>
            </Tabs>
        </div>
    );
};

export default BalanceDetailsPage;
