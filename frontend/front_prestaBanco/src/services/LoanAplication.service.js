import httpCommon from "../http-common";


const getAllLoanAplications = () => {
    return httpCommon.get("api/v1/loanaplication/");
}

const getLoanAplicationByUser = data => {
    return hhtpCommon.get(`api/v1/loanaplication/by-user`, data);
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

const update = (formData    ) => {
    return httpCommon.post("api/v1/loanaplication/", formData,{
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


export default {getAllLoanAplications, getLoanAplicationByUser, create, update, deleteLoanAplication, uploadFiles,calculateFee, getLoanAplicationById};