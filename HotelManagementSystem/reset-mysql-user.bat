@echo off
setlocal
where mysql >nul 2>nul
if %errorlevel% neq 0 (
  echo MySQL command-line client was not found in PATH.
  echo Open MySQL Workbench and run setup-mysql.sql as root/admin instead.
  pause
  exit /b 1
)
echo This will reset the Hotel Management System database user password to: hotel123
mysql -u root -p < setup-mysql.sql
if %errorlevel% neq 0 (
  echo.
  echo MySQL setup failed. Check the root password and MySQL service.
  pause
  exit /b 1
)
echo.
echo Database/user setup completed successfully.
echo Database: hotel_management
 echo User: hotel_app
 echo Password: hotel123
pause
