@echo off
setlocal EnableExtensions

set "APP_HOME=%~dp0"
set "WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar"
set "WRAPPER_URL=https://raw.githubusercontent.com/gradle/gradle/v9.6.1/gradle/wrapper/gradle-wrapper.jar"
set "REQUIRED_SHA256=497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7"

if defined JAVA_HOME (
  set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
  set "JAVA_EXE=java.exe"
)

if not exist "%WRAPPER_JAR%" goto downloadWrapper

for /f %%H in ('powershell.exe -NoProfile -Command "(Get-FileHash -LiteralPath ''%WRAPPER_JAR%'' -Algorithm SHA256).Hash.ToLowerInvariant()"') do set "ACTUAL_SHA256=%%H"
if /I "%ACTUAL_SHA256%"=="%REQUIRED_SHA256%" goto execute

:downloadWrapper
if not exist "%APP_HOME%gradle\wrapper" mkdir "%APP_HOME%gradle\wrapper"
set "TMP_JAR=%WRAPPER_JAR%.tmp"
del /q "%TMP_JAR%" >NUL 2>&1
powershell.exe -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri '%WRAPPER_URL%' -OutFile '%TMP_JAR%'"
if errorlevel 1 exit /b 1
for /f %%H in ('powershell.exe -NoProfile -Command "(Get-FileHash -LiteralPath ''%TMP_JAR%'' -Algorithm SHA256).Hash.ToLowerInvariant()"') do set "ACTUAL_SHA256=%%H"
if /I not "%ACTUAL_SHA256%"=="%REQUIRED_SHA256%" (
  del /q "%TMP_JAR%" >NUL 2>&1
  echo ERROR: Gradle Wrapper JAR checksum mismatch.
  exit /b 1
)
move /Y "%TMP_JAR%" "%WRAPPER_JAR%" >NUL

:execute
"%JAVA_EXE%" -Dfile.encoding=UTF-8 -Dorg.gradle.appname=%~n0 -jar "%WRAPPER_JAR%" %*
exit /b %ERRORLEVEL%