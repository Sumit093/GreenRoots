# 🌿 Eco-Scholars: Student Plant Growth Tracking System

## Project Overview

Eco-Scholars is a web-based application designed to promote environmental awareness among school students by making each student responsible for nurturing a sapling throughout their school years. The platform allows schools to maintain digital records of every student's plant — encouraging sustainability, personal responsibility, and green consciousness from an early age.

## 🌍 Core Features

- **Student Plant Assignment**: Each student gets assigned a plant when they join school
- **Growth Tracking**: Digital records of plant growth with yearly photo updates
- **Transfer System**: Plants can be reassigned when students transfer schools
- **Gamification**: Points, badges, and leaderboards to encourage engagement
- **Multi-School Support**: Dashboard for multiple educational institutions
- **Role-Based Access**: Secure access control for students, school admins, and system admins

## 🏗️ Tech Stack

### Backend
- **Framework**: Spring Boot (Java)
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Security**: Spring Security with BCrypt
- **Image Storage**: Cloudinary/AWS S3
- **Caching**: Redis

### Frontend
- **Framework**: React.js
- **Styling**: Tailwind CSS
- **State Management**: React Context/Redux
- **HTTP Client**: Axios

### DevOps
- **Containerization**: Docker
- **Version Control**: Git + GitHub
- **Deployment**: Render/AWS EC2/Railway

## 📁 Project Structure

```
Eco-Scholars/
├── backend/                 # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/ecoscholars/
│   │       ├── EcoScholarsApplication.java
│   │       ├── config/      # Configuration classes
│   │       ├── controller/ # REST Controllers
│   │       ├── dto/        # Data Transfer Objects
│   │       ├── entity/    # JPA Entities
│   │       ├── repository/# Data Repositories
│   │       ├── service/   # Business Logic
│   │       └── security/  # Security Configuration
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── data.sql
│   └── pom.xml
├── frontend/               # React Frontend
│   ├── src/
│   │   ├── components/     # Reusable Components
│   │   ├── pages/         # Page Components
│   │   ├── services/      # API Services
│   │   ├── context/       # React Context
│   │   ├── utils/         # Utility Functions
│   │   └── App.js
│   ├── public/
│   └── package.json
├── docker-compose.yml     # Docker Configuration
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+
- Docker (optional)

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

### Database Setup
1. Create MySQL database: `ecoscholars`
2. Update `application.yml` with your database credentials
3. Run the application to auto-create tables

## 🔐 User Roles

- **Super Admin**: System-level operations, monitoring all schools
- **Admin**: Create/update/delete school & student records, view analytics
- **School Admin**: Manage students under their school, reassign plants
- **Student**: Add/update personal plant data, view timeline, upload photos

## 📊 Database Schema

### Core Tables
- `schools` - School information and admin details
- `students` - Student profiles and plant assignments
- `plants` - Plant details and growth information
- `growth_records` - Yearly growth tracking with photos
- `achievements` - Gamification badges and points
- `plant_transfers` - Transfer history when students leave

## 🌱 Key Features

### 1. Plant Assignment System
- Automatic plant assignment for new students
- Unique plant tracking with scientific names
- Ecological importance documentation

### 2. Growth Tracking
- Yearly photo uploads with metadata
- Height and growth measurements
- Timeline view with visual progress

### 3. Transfer Management
- Seamless plant reassignment for student transfers
- Complete transfer history tracking
- Continuity of care documentation

### 4. Gamification
- Green Points for regular updates
- Achievement badges for milestones
- School-wise leaderboards

### 5. Multi-School Dashboard
- Institution-level analytics
- Cross-school plant statistics
- Participation metrics

## 🔒 Security Features

- Password hashing with BCrypt
- JWT-based authentication
- Role-based endpoint protection
- Data encryption for sensitive information
- HTTPS enforcement

## 🚀 Deployment

### Docker Deployment
```bash
docker-compose up -d
```

### Manual Deployment
1. Build backend: `mvn clean package`
2. Build frontend: `npm run build`
3. Deploy to your preferred platform

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🌟 Future Enhancements

- AI-based plant health analysis
- IoT sensor integration
- Mobile app development
- Community forum
- NGO integration

---

**Made with 🌱 for a greener future**

