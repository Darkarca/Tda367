cd Server/Target
:start
java -jar Server-1-jar-with-dependencies.jar %1
set exitcode=%ERRORLEVEL%

if %exitcode% == "5" (goto :start)
pause
