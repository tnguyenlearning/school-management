import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, message, Typography, Space } from 'antd';
import { generateSessionsForCourse } from '~/common/services/apis/courseApis';
import urls from '~/common/configs/urls';

const { Title, Text } = Typography;

const GenerateSession = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [course, setCourse] = useState(location.state?.course);
    const [monthsAhead, setMonthsAhead] = useState(0);

    useEffect(() => {
        if (!course) {
            message.error('Course details not found.');
        }
    }, [course]);

    const handleGenerate = async () => {
        const months = course.endDate ? 0 : monthsAhead;
        const { response, errorMessage } = await generateSessionsForCourse(course.id, months);
        if (response) {
            message.success('Sessions generated successfully!');
            navigate(`/sessions/${course.id}/manage`, { state: { course } });
        } else {
            message.error(errorMessage || 'Failed to generate sessions.');
        }
    };

    const handleNavigation = (path) => {
        navigate(path);
    };

    if (!course) {
        return <p>Loading...</p>;
    }

    return (
        <div>
            <Title level={2}>
                Auto Generate Session for Course {course.code} - {course.name}
            </Title>
            <Text>Generate from: {course.startDate}</Text>
            <Text>Generate to: {course.endDate || 'Indefinite'}</Text>
            {!course.endDate && <Input type="number" value={monthsAhead} onChange={(e) => setMonthsAhead(Number(e.target.value))} placeholder="Months Ahead" />}
            <Space style={{ marginTop: '16px' }}>
                <Button onClick={() => handleNavigation(urls.coursesMaintenance)}>Previous</Button>
                <Button type="primary" onClick={handleGenerate}>
                    Generate Sessions
                </Button>
                <Button onClick={() => handleNavigation(urls.coursesMaintenance)}>Cancel</Button>
            </Space>
        </div>
    );
};

export default GenerateSession;
