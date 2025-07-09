package com.school.education.clients.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.school.education.clients.dtos.FrequencyOptionDTO;

public class FrequencyOptionResponseDTO {
    @JsonProperty("_embedded")
    private Embedded embedded;

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public static class Embedded {
        @JsonProperty("billingFrequencyOptions")
        private List<FrequencyOptionDTO> billingFrequencyOptions;

        public List<FrequencyOptionDTO> getBillingFrequencyOptions() {
            return billingFrequencyOptions;
        }

        public void setBillingFrequencyOptions(List<FrequencyOptionDTO> billingFrequencyOptions) {
            this.billingFrequencyOptions = billingFrequencyOptions;
        }
    }
}
