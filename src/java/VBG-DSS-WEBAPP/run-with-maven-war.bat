@echo off
REM Build WAR e copia in Tomcat webapps. Spegnimento e riavvio Tomcat manuali.

set "PROJECT_DIR=%~dp0"
set "WAR=target\vbg-legacy-dss-webapp.war"
if not defined TOMCAT_HOME set "TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.1"
set "TOMCAT_WEBAPPS=%TOMCAT_HOME%\webapps"
set "APP_NAME=vbg-legacy-dss-webapp"

cd /d "%PROJECT_DIR%"
if errorlevel 1 (
    echo Errore: impossibile entrare nella cartella del progetto.
    pause
    exit /b 1
)

if "%SKIP_TESTS%"=="1" (
    echo [1/3] Build pulito e package - test saltati...
    call mvn clean package -DskipTests -q
) else (
    echo [1/3] Build pulito e package - con test...
    call mvn clean package -q
)

if errorlevel 1 (
    echo Maven build fallita.
    pause
    exit /b 1
)
if not exist "%WAR%" (
    echo WAR non trovato: %WAR%
    pause
    exit /b 1
)
echo    WAR generato: %WAR%
for %%F in ("%WAR%") do echo    Data/ora: %%~tF

echo [2/3] Copia WAR in webapps...
if not exist "%TOMCAT_WEBAPPS%" mkdir "%TOMCAT_WEBAPPS%"
copy /Y "%WAR%" "%TOMCAT_WEBAPPS%\%APP_NAME%.war"
if errorlevel 1 (
    echo Copia fallita. Esegui come Amministratore o imposta TOMCAT_HOME.
    pause
    exit /b 1
)

echo [3/3] Pulizia cartelle estratta e work...
if exist "%TOMCAT_WEBAPPS%\%APP_NAME%" (
    rmdir /s /q "%TOMCAT_WEBAPPS%\%APP_NAME%"
    echo    Cartella %APP_NAME% rimossa.
)
if exist "%TOMCAT_HOME%\work\Catalina\localhost\%APP_NAME%" (
    rmdir /s /q "%TOMCAT_HOME%\work\Catalina\localhost\%APP_NAME%"
    echo    Work rimosso.
)

echo.
echo Fatto. WAR copiato, cartelle pulite. Spegni e riavvia Tomcat manualmente, poi: http://localhost:8090/%APP_NAME%/
echo.
pause
