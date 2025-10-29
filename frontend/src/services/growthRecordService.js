import api from './api';

const growthRecordService = {
  createGrowthRecord: (growthRecordData) => {
    const formData = new FormData();
    for (const key in growthRecordData) {
      if (key === 'imageFile' && growthRecordData[key]) {
        formData.append(key, growthRecordData[key]);
      } else if (growthRecordData[key] !== undefined && growthRecordData[key] !== null) {
        formData.append(key, growthRecordData[key]);
      }
    }
    return api.post('/growth-records', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
  getAllGrowthRecords: () => api.get('/growth-records'),
  getGrowthRecordById: (id) => api.get(`/growth-records/${id}`),
  updateGrowthRecord: (id, growthRecordData) => {
    const formData = new FormData();
    for (const key in growthRecordData) {
      if (key === 'imageFile' && growthRecordData[key]) {
        formData.append(key, growthRecordData[key]);
      } else if (growthRecordData[key] !== undefined && growthRecordData[key] !== null) {
        formData.append(key, growthRecordData[key]);
      }
    }
    return api.put(`/growth-records/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
  deleteGrowthRecord: (id) => api.delete(`/growth-records/${id}`),
  getGrowthRecordsByPlant: (plantId) => api.get(`/growth-records/plant/${plantId}`),
  getGrowthRecordsByStudent: (studentId) => api.get(`/growth-records/student/${studentId}`),
  verifyGrowthRecord: (id, verifiedBy) => api.put(`/growth-records/${id}/verify?verifiedBy=${verifiedBy}`),
};

export default growthRecordService;
