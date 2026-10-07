@echo off
setlocal
where docker >nul 2>nul
if %errorlevel% neq 0 (
  echo Docker Desktop is not installed or not in PATH.
  echo Use setup-mysql.sql with your local MySQL instead.
  pause
  exit /b 1
)
echo Starting MySQL for Hotel Management System...
docker compose up -d mysql
if %errorlevel% neq 0 (
  echo Could not start MySQL. Make sure Docker Desktop is running.
  pause
  exit /b 1
)
echo MySQL is ready on localhost:3306.
echo Database: hotel_management
 echo Username: hotel_app
 echo Password: hotel123
pause
