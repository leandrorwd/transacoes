set DERBY_INSTALL=C:\Apache\db-derby-10.12.1.1-bin
set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;.
cd %DERBY_INSTALL%\bin
setEmbeddedCP.bat
java org.apache.derby.tools.sysinfo