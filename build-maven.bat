@echo off
echo ========================================
echo Iniciando Build de Maven
echo ========================================

cd backend\demo

echo Ejecutando Maven Clean Install...
mvn clean install -B -DskipTests --no-transfer-progress -Dstyle.color=always

IF %ERRORLEVEL% NEQ 0 (
    echo ========================================
    echo Error en el build de Maven
    echo ========================================
    exit /b %ERRORLEVEL%
) ELSE (
    echo ========================================
    echo Build completado exitosamente
    echo ========================================
)
