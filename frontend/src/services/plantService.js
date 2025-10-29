import api from './api';

const plantService = {
  registerPlant: (plantData) => api.post('/plants', plantData),
  getAllPlants: () => api.get('/plants'),
  getPlantById: (id) => api.get(`/plants/${id}`),
  updatePlant: (id, plantData) => api.put(`/plants/${id}`, plantData),
  deletePlant: (id) => api.delete(`/plants/${id}`),
  getPlantsBySchool: (schoolId) => api.get(`/plants/school/${schoolId}`),
  getPlantsByStudent: (studentId) => api.get(`/plants/student/${studentId}`),
};

export default plantService;
