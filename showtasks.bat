call runcrud
if "%ERRORLEVEL%" == "0" goto openApp
echo.
echo the file could not be open - breaking work
goto fail

:openApp
start http://localhost:8080/crud/v1/task/getTasks

goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.

