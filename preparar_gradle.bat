@echo off
setlocal
set "JAR=%~dp0gradle\wrapper\gradle-wrapper.jar"
if exist "%JAR%" (
  echo gradle-wrapper.jar ja existe.
  goto :done
)

echo Baixando gradle-wrapper.jar oficial do Gradle 8.9...
curl.exe -L "https://raw.githubusercontent.com/gradle/gradle/v8.9.0/gradle/wrapper/gradle-wrapper.jar" -o "%JAR%"
if errorlevel 1 (
  echo.
  echo Nao foi possivel baixar o arquivo automaticamente.
  echo Verifique sua conexao com a internet e tente novamente.
  pause
  exit /b 1
)

:done
echo Gradle Wrapper preparado.
echo Agora abra o projeto no Android Studio e faca o Gradle Sync.
pause
