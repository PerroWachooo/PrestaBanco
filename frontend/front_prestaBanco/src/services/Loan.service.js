import httpCommon from "../http-common";


const create = data => {
    return httpCommon.post("api/v1/loan/", data);
}

const getAllLoans = () => {
    return httpCommon.get("api/v1/loan/");
}

const update = (data) => {
    return httpCommon.put(`api/v1/loan/`, data);
}

const deleteLoan = id => {
    return httpCommon.delete(`api/v1/loan/${id}`);
}

export default {create, getAllLoans, update, deleteLoan};