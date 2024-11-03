import axios from 'axios';

export default axios.create({
    baseURL: `http://191.235.93.6:80/`,
    headers: {
        "Content-type": "application/json"
    }
});
