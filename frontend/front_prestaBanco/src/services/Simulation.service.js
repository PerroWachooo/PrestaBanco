import httpCommon from "../http-common";

const SimulateLoan = data => {
    return httpCommon.post("api/v1/simulation/aplication-simulation", data);
}

export default {SimulateLoan};