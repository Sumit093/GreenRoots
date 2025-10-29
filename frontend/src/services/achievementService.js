import api from './api';

const achievementService = {
  createAchievement: (achievementData) => api.post('/achievements', achievementData),
  getAllAchievements: () => api.get('/achievements'),
  getAchievementById: (id) => api.get(`/achievements/${id}`),
  updateAchievement: (id, achievementData) => api.put(`/achievements/${id}`, achievementData),
  deleteAchievement: (id) => api.delete(`/achievements/${id}`),
  getAchievementsByStudent: (studentId) => api.get(`/achievements/student/${studentId}`),
};

export default achievementService;
