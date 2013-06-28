@echo off

set dia=%date:~0,2%
set mes=%date:~3,2%
set ano=%date:~6,4%
set hh=%time:~0,2%
set mm=%time:~3,2%

set versao=%dia%_%mes%_%ano%_%hh%_%mm%
rem gerando backup
"C:\Program Files (x86)\MySQL\MySQL Server 5.5\bin\mysqldump" -u root -p123 bd_control_cheque > C:\backup_control_cheque\bkp_%versao%.sql

echo Backup gerado com sucesso!

