import axios froms "axios";

const PrestaBancoBackendServer= import.meta.env.VITE_PRESTABANCO_BACKEND;
const PrestaBancoBackendPort= import.meta.env.VITE_PRESTABANCO_BACKEND_PORT;

export default axios.create({
    baseURL: `http://${PrestaBancoBackendServer}:${PrestaBancoBackendPort},
    headers: {
        "Content-type": "application/json"
    }
});
