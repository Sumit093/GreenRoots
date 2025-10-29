import api from './api';

const studentService = {
  registerStudent: (studentData) => api.post('/students', studentData),
  getAllStudents: () => api.get('/students'),
  getStudentById: (id) => api.get(`/students/${id}`),
  updateStudent: (id, studentData) => api.put(`/students/${id}`, studentData),
  deleteStudent: (id) => api.delete(`/students/${id}`),
  getStudentsBySchool: (schoolId) => api.get(`/students/school/${schoolId}`),
};

export default studentService;
