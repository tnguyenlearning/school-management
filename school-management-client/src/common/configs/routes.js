import Home from '../../pages/Home';
import Register from '../../pages/Register';
import Login from '../../pages/Login';
import Logout from '../../pages/Logout';
import Profile from '../../pages/Profile';
import urls from './urls';
import SpecialLayout from '../../layouts/SpecialLayout';
import Special from '../../pages/Special';
import networkErr from '~/pages/NetworkErr';
import StudentForm from '~/pages/students/StudentForm';
import StudentsMPage from '~/pages/students/StudentsMPage';
import CoursesMPage from '~/pages/courses/CoursesMPage';
import CourseForm from '~/pages/courses/CourseForm';
import GenerateSession from '~/pages/courses/GenerateSession';
import SessionsManagement from '~/pages/sessions/SessionsManagement';
import AttendancesMPage from '~/pages/attendance/AttendancesMPage ';
import CourseStudentsMPage from '~/pages/courseStudents/CourseStudentsMPage';
import EligibleStudentsEnrollPage from '~/pages/enrollment/EligibleStudentsEnrollPage';
import BillingsMPage from '~/pages/billings/BillingsMPage';
import GenerateBillingPage from '~/pages/billings/GenerateBillingPage';
import AutoNumMPage from '~/pages/autonum/AutoNumMPage';
import GenerateAutoNumPage from '~/pages/autonum/GenerateAutoNumPage';
import ReclaimAutoNumPage from '~/pages/autonum/ReclaimAutoNumPage';
import EnrollStudentToCoursePage from '~/pages/enrollment/EnrollStudentToCoursePage';
import BillingsEnquiryPage from '~/pages/billings/BillingsEnquiryPage';
import FrequencyOptMPage from '~/pages/billings/FrequencyOptMPage';
import FrequencyOptForm from '~/pages/billings/FrequencyOptForm';
import ManageEnrollmentPage from '~/pages/enrollment/ManageEnrollmentPage';
import InvoicesEnquiryPage from '~/pages/billings/InvoicesEnquiryPage';
import StudentAccountForm from '~/pages/studentAccount/StudentAccountForm';

import StudentAccountMPage from '~/pages/studentAccount/StudentAccountMPage';
import BalanceDetailsPage from '~/pages/studentAccount/BalanceDetailsPage';
import ReceiptForm from '~/pages/receipts/ReceiptForm';
import CollectionPage from '~/pages/billings/CollectionPage';

const publicRoutes = [
    { path: urls.home, component: Home },
    { path: urls.register, component: Register },
    { path: urls.logIn, component: Login },
    { path: urls.logOut, component: Logout },
    { path: urls.profile, component: Profile },
    { path: urls.networkErr, component: networkErr },
    { path: urls.special, component: Special, layout: SpecialLayout },
    { path: urls.studentsManagement, component: StudentsMPage },
    { path: urls.studentForm, component: StudentForm },
    { path: urls.coursesMaintenance, component: CoursesMPage },
    { path: urls.courseForm, component: CourseForm },
    { path: urls.generateSession, component: GenerateSession },
    { path: urls.sessionsManagement, component: SessionsManagement },
    { path: urls.AttendancesMPage, component: AttendancesMPage },
    { path: urls.courseStudentManage, component: CourseStudentsMPage },
    { path: urls.studentEnroll, component: EligibleStudentsEnrollPage },
    { path: urls.enrollStudent, component: EnrollStudentToCoursePage },
    { path: urls.manageEnrollment, component: ManageEnrollmentPage },

    { path: urls.tuitionMaintenance, component: BillingsMPage },
    { path: urls.billingCycle, component: GenerateBillingPage },
    { path: urls.collection, component: CollectionPage },
    { path: urls.autoNumMaintenance, component: AutoNumMPage },
    { path: urls.generateAutoNum, component: GenerateAutoNumPage },
    { path: urls.reclaimAutoNum, component: ReclaimAutoNumPage },
    { path: urls.enquiryBillings, component: BillingsEnquiryPage },
    { path: urls.enquiryInvoices, component: InvoicesEnquiryPage },

    { path: urls.frequencyOptions, component: FrequencyOptMPage },
    { path: urls.FrequencyOptForm, component: FrequencyOptForm },

    { path: urls.studentAccountManagement, component: StudentAccountMPage },
    { path: urls.studentAccountForm, component: StudentAccountForm },

    // { path: urls.receiptManagement, component: ReceiptMPage },
    { path: urls.receiptForm, component: ReceiptForm },

    { path: urls.balanceDetails, component: BalanceDetailsPage },
];

const privateRoutes = [];

export { publicRoutes, privateRoutes };
