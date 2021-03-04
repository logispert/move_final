@echo off
call setenv.bat
mysqladmin -u root shutdown --port 3385
pause ..