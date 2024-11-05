import httpCommon from "../http-common";

const getAllLoanAplications = () => {
    return httpCommon.get("api/v1/loanaplication/");
}

const getLoanAplicationByRut = rut => {
    return httpCommon.get(`api/v1/loanaplication/by-user/${rut}`);
}

const getLoanAplicationById = id => {
    return httpCommon.get(`api/v1/loanaplication/by-id/${id}`);
}

const create = (formData) => {
    return httpCommon.post("api/v1/loanaplication/", formData,{
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}

const update = (formData,id) => {
    return httpCommon.put(`api/v1/loanaplication/${id}`, formData,{
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}

const deleteLoanAplication = id => {
    return httpCommon.delete(`api/v1/loanaplication/${id}`);
}

const uploadFiles = (formData, id) => {
    return httpCommon.post(`api/v1/loanaplication/upload/${id}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
}

const calculateFee = data => {
    return httpCommon.post("api/v1/loanaplication/calculateFee", data);
}

const calculateTotalCost = id => {
    return httpCommon.get(`api/v1/loanaplication/calculateTotalCost/${id}`);
}

export default {calculateTotalCost, getAllLoanAplications, getLoanAplicationByRut, create, update, deleteLoanAplication, uploadFiles,calculateFee, getLoanAplicationById};