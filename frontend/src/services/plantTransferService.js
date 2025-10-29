import api from './api';

const plantTransferService = {
  initiatePlantTransfer: (transferData) => api.post('/plant-transfers', transferData),
  getAllPlantTransfers: () => api.get('/plant-transfers'),
  getPlantTransferById: (id) => api.get(`/plant-transfers/${id}`),
  updatePlantTransfer: (id, transferData) => api.put(`/plant-transfers/${id}`, transferData),
  deletePlantTransfer: (id) => api.delete(`/plant-transfers/${id}`),
  getPlantTransfersByPlant: (plantId) => api.get(`/plant-transfers/plant/${plantId}`),
  getPlantTransfersByPreviousStudent: (studentId) => api.get(`/plant-transfers/previous-student/${studentId}`),
  getPlantTransfersByNewStudent: (studentId) => api.get(`/plant-transfers/new-student/${studentId}`),
};

export default plantTransferService;
