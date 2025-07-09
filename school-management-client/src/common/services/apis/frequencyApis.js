import { apiCallNoPage } from '../apiService';

export const getFrequencyOptions = async () => await apiCallNoPage('/billing/v0/billing-frequency', 'GET');

export const getFrequencyOptionById = async (id) => {
    const { response, errorMessage } = await apiCallNoPage(`/billing/v0/billing-frequency/${id}`, 'GET');
    if (errorMessage) {
        return { response: null, errorMessage };
    }

    return { response: response, errorMessage };
};

export const createFrequencyOption = async (frequencyData) => apiCallNoPage('/billing/v0/billing-frequency', 'POST', frequencyData);

export const updateFrequencyOption = async (id, frequencyData) => apiCallNoPage(`/billing/v0/billing-frequency/${id}`, 'PUT', frequencyData);

export const deleteFrequencyOption = async (id) => apiCallNoPage(`/billing/v0/billing-frequency/${id}`, 'DELETE');
