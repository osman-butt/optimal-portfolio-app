# Portfolio optimization app

This application implements the **Markowitz Portfolio Optimization** algorithm, which is a method for selecting the optimal portfolio allocation by maximizing expected return for a given level of risk. The project is built using a combination of Next.js for the frontend, Java for the optimization logic, Python (Flask) for retrieving real financial data, and Docker for containerization.

The system is composed of three main services:

- **Frontend** (Next.js)
- **Portfolio Optimization Service** (Java)
- **Stock Data Service** (Python with Flask)
These services work together in a Docker-based environment, where each service is isolated within its own container.

## Requirements
- **Docker**: To run the app in containers.

## Running the application
#### 1. Clone the repository
#### 2. Navigate to the clonned repository and run docker
```bash
cd optimal-portfolio-app
docker compose up -d
```
#### 3. The app is available on port 80
