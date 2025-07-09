import React, { useState } from 'react';
import ReceiptSearch from './ReceiptSearch';
import ReceiptTable from './ReceiptTable';
import ReceiptActions from './ReceiptActions';
import { fetchReceipts } from '~/common/services/apis/receiptApi';

const ReceiptMPage = () => {
    const [receipts, setReceipts] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleSearch = async (filters) => {
        setLoading(true);
        const data = await fetchReceipts(filters);
        setReceipts(data);
        setLoading(false);
    };

    return (
        <div>
            <h1>Receipt Management</h1>
            <ReceiptActions />
            <ReceiptSearch onSearch={handleSearch} />
            <ReceiptTable data={receipts} loading={loading} />
        </div>
    );
};

export default ReceiptMPage;
