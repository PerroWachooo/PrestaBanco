import httpCommon from "../http-common";

const registerUser = data => {
    return httpCommon.post("api/v1/users/register", data);
};

const getAllUsers = () => {
    return httpCommon.get("api/v1/users/");
}

const getUserById = id => {
    return httpCommon.get(`api/v1/users/${id}`);
}

const updateUser = (data) => {
    return httpCommon.put(`api/v1/users/update`, data);
}

const deleteUser = id => {
    return httpCommon.delete(`api/v1/users/${id}`);
}

export default {registerUser, getAllUsers, getUserById, updateUser, deleteUser};
