@echo off
REM --- コンソールを UTF-8 に変更 ---
chcp 65001

REM --- Java の出力エンコーディングも UTF-8 に統一 ---
java -Dfile.encoding=UTF-8 -jar "%~dp0ProductManagement_Java_0056_B_Database_v2.jar"

pause
