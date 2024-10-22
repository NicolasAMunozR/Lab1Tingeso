import httpClient from "../http-common";

const create = (formData) => {
    return httpClient.post("/credits/", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
};

const evaluateCredit = id => {
    return httpClient.put(`/credits/evaluateCredit/${id}`);
}

const updateStatus = id => {
    return httpClient.put(`/credits/updateStatus/${id}`);
}

const updateTerms = (id, lienInsurance, administrtionFee) => {
    return httpClient.put(`/credits/updateTerms/${id}`, {params: {lienInsurance, administrtionFee}});
}

const rejectTerms = id => {
    return httpClient.put(`/credits/rejectTerms/${id}`);
}

const getcredits = () => {
    return httpClient.get(`/credits/all`);
}

export default { create, evaluateCredit, updateStatus, updateTerms, rejectTerms, getcredits };