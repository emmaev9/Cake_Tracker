import axios from "axios";
const API_URL_PERSON = "http://localhost:8084/person";

class PersonService {

  savePerson(person){
    console.log(person)
    return axios.post(`${API_URL_PERSON}/insert`,person);
  }

  deletePerson(id){
    return axios.delete(`${API_URL_PERSON}/${id}`);
  }

  getPerson(id){
    return axios.get(`${API_URL_PERSON}/${id}`);
  }

  getAllPeople(){
    return axios.get(`${API_URL_PERSON}/allPeople`);
  }
}

export default new PersonService();