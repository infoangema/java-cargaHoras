java -jar -Djasypt.encryptor.password="GerrDevs9003...."
sudo idea /etc/nginx/sites-available/default
java "-Dspring.profiles.active=prod" "-Djasypt.encryptor.password=GerrDevs9003...." -jar hours-version-.0.0.1-port-8090.jar


```
[Unit]
Description=Angema Backend Carga Horas
After=syslog.target

[Service]
User=admdevs
ExecStart=/usr/bin/java "-Dspring.profiles.active=prod" "-Djasypt.encryptor.password=GerrDevs9003...." -jar /home/admdevs/Escritorio/devs/java/java-cargaHoras/.prod/hours-backend.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```
