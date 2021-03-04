@echo off
call setenv.bat
mysqld --defaults-file=%MARIA_HOME%/config/my.cnf
pause ..