import { apiCallNoPage } from '../apiService';

export const reserveAutoNum = (type) => apiCallNoPage(`/utils/v2/auto-numbers/reserve`, 'POST', type);
export const confirmAutoNum = (type, number) => apiCallNoPage(`/utils/v2/auto-numbers/confirm`, 'POST', { type, number });

export const reclaimAutoNum = (type, number) => apiCallNoPage(`/utils/v2/auto-numbers/reclaim`, 'POST', { type, number });
export const generateAutoNum = (type, prefix, count) => apiCallNoPage(`/utils/v2/auto-numbers/generate`, 'POST', { type, prefix, count });
