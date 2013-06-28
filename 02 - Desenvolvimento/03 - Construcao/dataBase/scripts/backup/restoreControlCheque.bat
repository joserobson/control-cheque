@echo off

rem restaurando backup
"C:\Program Files (x86)\MySQL\MySQL Server 5.5\bin\mysql" -u root -p123 bd_control_cheque< C:\backup_control_cheque\bkp_%nomeDoArquivo%.sql

echo Backup gerado com sucesso!
pause
