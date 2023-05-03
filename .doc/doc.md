java -jar -Djasypt.encryptor.password="GerrDevs9003....|.."
mvn jasypt:decrypt-value -Djasypt.encryptor.password="GerrDevs9003....|.." -Djasypt.plugin.value="GL9Lqyj01G02tuEYpotOsaG/8g80Ca/OVh1lO0SWBUEYl4A9uewlQ9s1bmbgKBV4"

sudo idea /etc/nginx/sites-available/default

cd /home/admdevs/Escritorio/devs/java/java-cargaHoras/target
java "-Dspring.profiles.active=prod" "-Djasypt.encryptor.password=GerrDevs9003...." -jar hours-version-0.0.1-port-8090.jar
java "-Dspring.profiles.active=prod" "-Djasypt.encryptor.password=GerrDevs9003...." -jar hours-backend.jar

sudo nano /etc/systemd/system/hours-backend.service
ExecStart=/usr/bin/java -jar /home/admdevs/Escritorio/devs/java/java-cargaHoras/.prod/hours-backend.jar -Dspring.profiles.active=prod -Djasypt.encryptor.password=GerrDevs9003....
sudo systemctl daemon-reload
sudo systemctl enable hours-backend.service
sudo systemctl start hours-backend.service


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
