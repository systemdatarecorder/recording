

@echo off
setlocal
for /f "tokens=4-5 delims=. " %%i in ('ver') do set VERSION=%%i.%%j
if "%version%" == "5.2" goto NOUAC
if "%version%" == "5.1" goto NOUAC
endlocal

:UAC
REM  --> Check for permissions
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"

REM --> If error flag set, we do not have admin.
if '%errorlevel%' NEQ '0' (
    echo Requesting administrative privileges...
    goto UACPrompt
) else ( goto gotAdmin )

:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
    echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"

    "%temp%\getadmin.vbs"
    exit /B

:gotAdmin
    if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
    pushd "%CD%"
    CD /D "%~dp0"
: End check for permissions

:NOUAC

"%CD%\..\redist\vcredist_x86.exe" /passive /norestart
goto serviceInstall


:serviceInstall
sc create "SDR_Cpurec" binPath= "%CD%\..\bin\SDR_cpurec.exe"  start= auto displayname= "System data recorder for cpu data."
sc description "SDR_Cpurec" "System data recorder for cpu data."
sc start "SDR_Cpurec"

timeout /T 5